package com.java.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.java.model.Tax;
import com.java.prms.ConnectionHelper;

public class TaxServiceImp implements ITaxServices {
	
	private final Connection conn ;

    public TaxServiceImp() throws ClassNotFoundException, SQLException {
        this.conn = ConnectionHelper.getConnection();
    }
    
    public boolean addTax(Tax tax) {
        String query = "INSERT INTO tax (TaxID, EmployeeID, TaxYear, TaxAmount) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, tax.getTaxID());
            ps.setInt(2, tax.getEmployeeID());
            ps.setInt(3, tax.getTaxYear());
            ps.setDouble(4, tax.getTaxAmount());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    

	@Override
	public double CalculateTax(int employeeId, int taxYear) {
		// TODO Auto-generated method stub
		String query = "SELECT SUM(BasicSalary - Deductions) AS TaxableIncome FROM payroll WHERE EmployeeID = ? AND YEAR(PayPeriodStartDate) = ?";
        double taxableIncome = 0.0;

        try (PreparedStatement ps = conn.prepareStatement(query))
        {
            ps.setInt(1, employeeId);
            ps.setInt(2, taxYear);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                taxableIncome = rs.getDouble("TaxableIncome");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return computeTax(taxableIncome);
    }
	
	private double computeTax(double income) {
        if (income <= 50000) return income * 0.10;
        else if (income <= 100000) return income * 0.20;
        else return income * 0.30;
    }


	@Override
	public Tax GetTaxById(int taxId) {
	    String query = "SELECT * FROM tax WHERE TaxID = ?";
	    try (PreparedStatement ps = conn.prepareStatement(query)) {
	        ps.setInt(1, taxId);
	        ResultSet rs = ps.executeQuery();
	        
	        if (rs.next()) {  
	            Tax tax = new Tax();
	            tax.setTaxID(rs.getInt("TaxID"));
	            tax.setEmployeeID(rs.getInt("EmployeeID"));
	            tax.setTaxYear(rs.getInt("TaxYear"));
	            tax.setTaxAmount(rs.getDouble("TaxAmount"));
	            
	            return tax;  // Return the tax object after populating it with the data from the result set
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;  // Return null if no record is found
	}

	

	    // Other existing methods...

	    @Override
	    public void removeTaxesForEmployee(int employeeId) {
	        // Implement logic to remove tax records associated with the employee
	        System.out.println("Removing tax records for Employee ID: " + employeeId);
	        // Here, you would call a DAO method or database operation to remove tax records
	    }
	


	@Override
	public List<Tax> GetTaxesForEmployee(int employeeId) throws ClassNotFoundException {
	    double totalTax = 0;
	    List<Tax> taxes = new ArrayList<>();
	    // Adjust the query to fetch all necessary columns
	    String query = "SELECT TaxID, EmployeeID, TaxYear, TaxAmount FROM tax WHERE EmployeeID = ?";
	    try (PreparedStatement ps = conn.prepareStatement(query)) {
	        ps.setInt(1, employeeId);
	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {
	            Tax tax = new Tax();
	            tax.setTaxID(rs.getInt("TaxID"));
	            tax.setEmployeeID(rs.getInt("EmployeeID"));
	            tax.setTaxYear(rs.getInt("TaxYear"));
	            tax.setTaxAmount(rs.getDouble("TaxAmount"));

	            taxes.add(tax);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return taxes;
	}


	@Override
	public List<Tax> GetTaxesForYear(int taxYear) {
		// TODO Auto-generated method stub
	
		    List<Tax> taxList = new ArrayList<>();
		    String query = "SELECT * FROM tax WHERE TaxYear = ?";
		    try (PreparedStatement ps = conn.prepareStatement(query)) {
		        ps.setInt(1, taxYear);
		        ResultSet rs = ps.executeQuery();

		        while (rs.next()) {
		            Tax tax = new Tax();
		            tax.setTaxID(rs.getInt("TaxID"));
		            tax.setEmployeeID(rs.getInt("EmployeeID"));
		            tax.setTaxYear(rs.getInt("TaxYear"));
		            tax.setTaxableIncome(rs.getDouble("TaxableIncome"));
		            tax.setTaxAmount(rs.getDouble("TaxAmount"));
		            taxList.add(tax);
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    
		    // Check if no records found, return empty list or handle accordingly
		    return taxList.isEmpty() ? null : taxList;
		}
}