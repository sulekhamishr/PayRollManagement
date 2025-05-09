package com.java.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.java.model.Payroll;
import com.java.prms.ConnectionHelper;

public class PayRollServicesImp implements IPayRollServices {

    private static Connection connection;
    private static PreparedStatement ps;
    
    
    
    @Override
    public void generatePayroll(int employeeId, String payPeriodStartDate, String payPeriodEndDate) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = ConnectionHelper.getConnection(); 

            // Step 1: Fetch basic salary
            String employeeQuery = "SELECT BasicSalary FROM Employee WHERE EmployeeID = ?";
            ps = connection.prepareStatement(employeeQuery);
            ps.setInt(1, employeeId);
            rs = ps.executeQuery();

            double basicSalary = 0.0;
            if (rs.next()) {
                basicSalary = rs.getDouble("BasicSalary");
            } else {
                System.out.println("Employee not found.");
                return;
            }

            rs.close();  //  Close ResultSet before reusing PreparedStatement
            ps.close();

            // Step 2: Calculate overtime pay (hardcoded here as 5 hours × ₹300)
            double overtimePay = 5 * 300;

            // Step 3: Compute deductions (10% of basic salary)
            double deductions = basicSalary * 0.10;

            // Step 4: Calculate net salary
            double netSalary = basicSalary + overtimePay - deductions;

            // Step 5: Insert payroll record
            String insertQuery = "INSERT INTO Payroll "
                    + "(EmployeeID, PayPeriodStartDate, PayPeriodEndDate, BasicSalary, OvertimePay, Deductions, NetSalary) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?)";

            ps = connection.prepareStatement(insertQuery);
            ps.setInt(1, employeeId);
            ps.setDate(2, Date.valueOf(payPeriodStartDate));
            ps.setDate(3, Date.valueOf(payPeriodEndDate));
            ps.setDouble(4, basicSalary);
            ps.setDouble(5, overtimePay);
            ps.setDouble(6, deductions);
            ps.setDouble(7, netSalary);

            int rowsInserted = ps.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println(" Payroll generated successfully.");
            } else {
                System.out.println("Failed to insert payroll record.");
            }

        } catch (Exception e) {
            System.out.println("Error during payroll generation: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
    }

   
    @Override
    public Payroll getPayrollById(int payrollId) {
		// TODO Auto-generated method stub

        String query = "SELECT * FROM payrollmanagementsystem.Payroll WHERE PayrollId = ?";
        Payroll payroll = null;

        try {
            connection = ConnectionHelper.getConnection();
            ps = connection.prepareStatement(query);
            ps.setInt(1, payrollId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                payroll = new Payroll();
                payroll.setPayrollID(rs.getInt("PayrollId"));
                payroll.setEmployeeID(rs.getInt("EmployeeId"));
                payroll.setPayPeriodStartDate(rs.getDate("PayPeriodStartDate").toString());
                payroll.setPayPeriodEndDate(rs.getDate("PayPeriodEndDate").toString());
                payroll.setBasicSalary(rs.getDouble("BasicSalary"));
                payroll.setOvertimePay(rs.getDouble("OvertimePay"));
                payroll.setDeductions(rs.getDouble("Deductions"));
                payroll.setNetSalary(rs.getDouble("NetSalary"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return payroll;
    }

    @Override
    public List<Payroll> getPayrollsForEmployee(int employeeId) {
		// TODO Auto-generated method stub

        List<Payroll> payrolls = new ArrayList<>();
        String query = "SELECT * FROM payrollmanagementsystem.Payroll WHERE EmployeeId = ? ORDER BY PayPeriodStartDate DESC";

        try {
            connection = ConnectionHelper.getConnection();
            ps = connection.prepareStatement(query);
            ps.setInt(1, employeeId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Payroll payroll = new Payroll();
                payroll.setPayrollID(rs.getInt("PayrollId"));
                payroll.setEmployeeID(rs.getInt("EmployeeId"));
                payroll.setPayPeriodStartDate(rs.getDate("PayPeriodStartDate").toString());
                payroll.setPayPeriodEndDate(rs.getDate("PayPeriodEndDate").toString());
                payroll.setBasicSalary(rs.getDouble("BasicSalary"));
                payroll.setOvertimePay(rs.getDouble("OvertimePay"));
                payroll.setDeductions(rs.getDouble("Deductions"));
                payroll.setNetSalary(rs.getDouble("NetSalary"));
                payrolls.add(payroll);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return payrolls;
    }

   

    @Override
    public void removePayrollsForEmployee(int employeeId) {
        // Implement logic to remove payroll records associated with the employee
        // Example: Deleting from the database or clearing the list
        System.out.println("Removing payroll records for Employee ID: " + employeeId);
        // Here, you would call a DAO method or database operation to remove payroll records
    }


	@Override
	public List<Payroll> getPayrollsForPeriod(String startDate, String endDate) {
		// TODO Auto-generated method stub

        List<Payroll> payrolls = new ArrayList<>();
        String query = "SELECT * FROM payrollmanagementsystem.Payroll WHERE PayPeriodStartDate >= ? AND PayPeriodEndDate <= ? ORDER BY PayPeriodStartDate DESC";

        try {
            connection = ConnectionHelper.getConnection();
            ps = connection.prepareStatement(query);
            ps.setDate(1, Date.valueOf(startDate));
            ps.setDate(2, Date.valueOf(endDate));
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Payroll payroll = new Payroll();
                payroll.setPayrollID(rs.getInt("PayrollId"));
                payroll.setEmployeeID(rs.getInt("EmployeeId"));
                payroll.setPayPeriodStartDate(rs.getDate("PayPeriodStartDate").toString());
                payroll.setPayPeriodEndDate(rs.getDate("PayPeriodEndDate").toString());
                payroll.setBasicSalary(rs.getDouble("BasicSalary"));
                payroll.setOvertimePay(rs.getDouble("OvertimePay"));
                payroll.setDeductions(rs.getDouble("Deductions"));
                payroll.setNetSalary(rs.getDouble("NetSalary"));
                payrolls.add(payroll);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return payrolls;
	}



}