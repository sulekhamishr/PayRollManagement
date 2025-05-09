package com.java.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.java.model.FinancialRecord;
import com.java.prms.ConnectionHelper;

public class FinancialRecoredServices implements IFinancialRecoredServices {

	@Override
	public void AddFinancialRecord(int employeeId, String description, double amount, String recordType) {
		// TODO Auto-generated method stub
		String query = "INSERT INTO financialrecord "
        		+ "(EmployeeID, RecordDate, Description, Amount, RecordType) "
        		+ "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

                         ps.setInt(1, employeeId);
                         ps.setDate(2, Date.valueOf(LocalDate.now()));
                         ps.setString(3, description);
                         ps.setDouble(4, amount);
                         
                         ps.setString(5, recordType);

            ps.executeUpdate();
            System.out.println("Financial record added successfully");

        } catch (Exception e)
        {
            e.printStackTrace();
        }
	}
	
    @Override
    public void removeFinancialRecordsForEmployee(int employeeId) {
        // Implement logic to remove financial records associated with the employee
        System.out.println("Removing financial records for Employee ID: " + employeeId);
        // Here, you would call a DAO method or database operation to remove financial records
    }


	@Override
	public FinancialRecord GetFinancialRecordById(int recordId) {
		// TODO Auto-generated method stub
		String query = "SELECT * FROM financialrecord WHERE RecordID = ?";

        try (Connection conn = ConnectionHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, recordId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new FinancialRecord(
                    rs.getInt("RecordID"),
                    rs.getInt("EmployeeID"),
                    rs.getDate("RecordDate").toLocalDate(),
                    rs.getString("Description"),
                    rs.getDouble("Amount"),
                    rs.getString("RecordType")
                );
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
	}

	@Override
	public List<FinancialRecord> GetFinancialRecordsForEmployee(int employeeId) {
		// TODO Auto-generated method stub
		
		 String query = "SELECT * FROM financialrecord WHERE EmployeeID = ?";
	        List<FinancialRecord> records = new ArrayList<>();

	        try (Connection conn = ConnectionHelper.getConnection();
	             PreparedStatement ps = conn.prepareStatement(query)) {

	            ps.setInt(1, employeeId);
	            ResultSet rs = ps.executeQuery();

	            while (rs.next()) {
	                records.add(new FinancialRecord(
	                    rs.getInt("RecordID"),
	                    rs.getInt("EmployeeID"),
	                    rs.getDate("RecordDate").toLocalDate(),
	                    rs.getString("Description"),
	                    rs.getDouble("Amount"),
	                    rs.getString("RecordType")
	                ));
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return records;
	}

	@Override
	public List<FinancialRecord> GetFinancialRecordsForDate(LocalDate recordDate) {
		// TODO Auto-generated method stub
		
		 String query = "SELECT * FROM financialrecord WHERE RecordDate = ?";
	        List<FinancialRecord> records = new ArrayList<>();

	        try (Connection conn = ConnectionHelper.getConnection();
	             PreparedStatement ps = conn.prepareStatement(query)) {

	            ps.setDate(1, Date.valueOf(recordDate));
	            ResultSet rs = ps.executeQuery();

	               while (rs.next()) {
	                records.add(new FinancialRecord(
	                    rs.getInt("RecordID"),
	                    rs.getInt("EmployeeID"),
	                    rs.getDate("RecordDate").toLocalDate(),
	                    rs.getString("Description"),
	                    rs.getDouble("Amount"),
	                    rs.getString("RecordType")
	                ));
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return records;
	    }
}





