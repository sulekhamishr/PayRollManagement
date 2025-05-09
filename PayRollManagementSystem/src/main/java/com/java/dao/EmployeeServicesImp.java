package com.java.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.java.model.Employee;
import com.java.prms.ConnectionHelper;

public class EmployeeServicesImp implements IEmployeeServices {

    Connection connection;
    PreparedStatement ps;

    @Override
    public Employee getEmployeeById(int employeeId) throws SQLException, ClassNotFoundException {
    	
		// TODO Auto-generated method stub

        connection = ConnectionHelper.getConnection();
        String query = "SELECT * FROM employee WHERE EmployeeId = ?";
        ps = connection.prepareStatement(query);
        ps.setInt(1, employeeId);
        ResultSet rs = ps.executeQuery();

        Employee emp = null;
        if (rs.next()) {
            emp = new Employee();
            emp.setEmployeeId(rs.getInt("EmployeeId"));
            emp.setFirstName(rs.getString("FirstName"));
            emp.setLastName(rs.getString("LastName"));
            emp.setDateOfBirth(rs.getDate("DateOfBirth").toLocalDate());
            emp.setGender(rs.getString("Gender"));
            emp.setEmail(rs.getString("Email"));
            emp.setPhoneNumber(rs.getLong("PhoneNumber"));
            emp.setAdpayrolldress(rs.getString("Adpayrolldress"));
            emp.setPosition(rs.getString("Position"));
            emp.setBasicSalary(rs.getDouble("BasicSalary"));
            emp.setJoiningDate(rs.getDate("JoiningDate").toLocalDate());
            Date termination = rs.getDate("TerminationDate");
            if (termination != null) {
                emp.setTerminationDate(termination.toLocalDate());
            }
        }
        rs.close();
        ps.close();
        connection.close();
        return emp;
    }

    @Override
    public List<Employee> getAllEmployees() throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub

        connection = ConnectionHelper.getConnection();
        String query = "SELECT * FROM employee";
        ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        List<Employee> list = new ArrayList<>();
        while (rs.next()) {
            Employee emp = new Employee();
            emp.setEmployeeId(rs.getInt("EmployeeId"));
            emp.setFirstName(rs.getString("FirstName"));
            emp.setLastName(rs.getString("LastName"));
            emp.setDateOfBirth(rs.getDate("DateOfBirth").toLocalDate());
            emp.setGender(rs.getString("Gender"));
            emp.setEmail(rs.getString("Email"));
            emp.setPhoneNumber(rs.getLong("PhoneNumber"));
            emp.setAdpayrolldress(rs.getString("Adpayrolldress")); 
            emp.setPosition(rs.getString("Position"));
            emp.setBasicSalary(rs.getDouble("BasicSalary"));
            emp.setJoiningDate(rs.getDate("JoiningDate").toLocalDate());
            Date termination = rs.getDate("TerminationDate");
            if (termination != null) {
                emp.setTerminationDate(termination.toLocalDate());
            }
            list.add(emp);
        }
        rs.close();
        ps.close();
        connection.close();
        return list;
    }

    @Override
    public void addEmployee(Employee emp) throws SQLException, ClassNotFoundException {
        connection = ConnectionHelper.getConnection();

        String query = "INSERT INTO employee "
                     + "(FirstName, LastName, DateOfBirth, Gender, Email, PhoneNumber,Adpayrolldress, Position, BasicSalary, JoiningDate, TerminationDate) "
                     + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        ps = connection.prepareStatement(query);
        ps.setString(1, emp.getFirstName());
        ps.setString(2, emp.getLastName());
        ps.setDate(3, Date.valueOf(emp.getDateOfBirth()));
        ps.setString(4, emp.getGender());
        ps.setString(5, emp.getEmail());
        ps.setLong(6, emp.getPhoneNumber());  
        ps.setString(7, emp.getAdpayrolldress());      
        ps.setString(8, emp.getPosition());
        ps.setDouble(9, emp.getBasicSalary());
        ps.setDate(10, Date.valueOf(emp.getJoiningDate()));

        if (emp.getTerminationDate() != null) {
            ps.setDate(11, Date.valueOf(emp.getTerminationDate()));
        } else {
            ps.setNull(11, Types.DATE);
        }

        ps.executeUpdate();
        ps.close();
        connection.close();
    }

    @Override
    public void updateEmployee(Employee emp) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub

        connection = ConnectionHelper.getConnection();
        String query = "UPDATE employee SET FirstName=?, LastName=?, Email=?, PhoneNumber=?, Gender=?, "
                + "DateOfBirth=?, Adpayrolldress=?, Position=?, BasicSalary=?, JoiningDate=?, TerminationDate=? "
                + "WHERE EmployeeId=?";
        ps = connection.prepareStatement(query);
        ps.setString(1, emp.getFirstName());
        ps.setString(2, emp.getLastName());
        ps.setString(3, emp.getEmail());
        ps.setLong(4, emp.getPhoneNumber());
        ps.setString(5, emp.getGender());
        ps.setDate(6, Date.valueOf(emp.getDateOfBirth()));
        ps.setString(7, emp.getAdpayrolldress()); // ✅ fixed
        ps.setString(8, emp.getPosition());
        ps.setDouble(9, emp.getBasicSalary());
        ps.setDate(10, Date.valueOf(emp.getJoiningDate()));
        if (emp.getTerminationDate() != null) {
            ps.setDate(11, Date.valueOf(emp.getTerminationDate()));
        } else {
            ps.setNull(11, Types.DATE);
        }
        ps.setInt(12, emp.getEmployeeId());

        ps.executeUpdate();
        ps.close();
        connection.close();
    }

    @Override
    public void removeEmployee(int employeeId) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub

        
    	
     //employeeid is foreign key what's why we have to delete it from all the table then only it will get deleted
    	    connection = ConnectionHelper.getConnection();

    	    String deletePayroll = "DELETE FROM payroll WHERE EmployeeID=?";
    	    ps = connection.prepareStatement(deletePayroll);
    	    ps.setInt(1, employeeId);
    	    ps.executeUpdate();
    	    ps.close();

    	    String deleteFinancial = "DELETE FROM financialrecord WHERE EmployeeID=?";
    	    ps = connection.prepareStatement(deleteFinancial);
    	    ps.setInt(1, employeeId);
    	    ps.executeUpdate();
    	    ps.close();

    	    String deleteEmployee = "DELETE FROM employee WHERE EmployeeId=?";
    	    ps = connection.prepareStatement(deleteEmployee);
    	    ps.setInt(1, employeeId);
    	    ps.executeUpdate();
    	    ps.close();

    	    connection.close();
    	}
    
}
