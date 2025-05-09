package com.java.bal;

import java.time.LocalDate;
import java.util.List;

import com.java.dao.FinancialRecoredServices;
import com.java.dao.IFinancialRecoredServices;
import com.java.exception.FinancialRecordException;
import com.java.model.FinancialRecord;

public class FinancialRecoredValidation {

	private static IFinancialRecoredServices financialRecordServices;
    private static StringBuilder sb;

    static {
        financialRecordServices = new FinancialRecoredServices();
        sb = new StringBuilder();
    }

    public static void addFinancialRecord(int employeeId, String description, double amount, String recordType) throws FinancialRecordException {
                  sb.setLength(0);

           if (employeeId <= 0) {
        	   
            sb.append("Invalid Employee ID. ");
            
               }
           if (description == null || description.trim().isEmpty())
           {
            sb.append("Description cannot be empty. ");
        }
           if (amount <= 0) { 
            sb.append("Amount must be greater than zero. ");
            }
           if (recordType == null || (!recordType.equalsIgnoreCase("credit") && !recordType.equalsIgnoreCase("debit"))) {
            sb.append("Record type must be 'credit' or 'debit'. ");
        }

        if (sb.length() > 0) 
        {
            throw new FinancialRecordException("Validation Error(s): " + sb.toString());
        }

        financialRecordServices.AddFinancialRecord(employeeId, description, amount, recordType.toLowerCase());
    }
    public static FinancialRecord getFinancialRecordById(int recordId) throws FinancialRecordException {
    	
        if (recordId <= 0) {
            throw new FinancialRecordException("Invalid Record ID.");
        }

        FinancialRecord record = financialRecordServices.GetFinancialRecordById(recordId);
        
        
        if (record == null) {
            throw new FinancialRecordException("Financial record not found.");
        }
    return record;
    }
    
    public static List<FinancialRecord> GetFinancialRecordsForEmployee(int employeeId) throws FinancialRecordException {
		
    	
    	 if (employeeId <= 0) {
             throw new FinancialRecordException("Invalid Employee ID.");
         }

         List<FinancialRecord> records = financialRecordServices.GetFinancialRecordsForEmployee(employeeId);
         
         if (records == null || records.isEmpty()) 
         {
             throw new FinancialRecordException("No financial records found for this employee.");
         }

         return records;
     }
    	
  
    
    public static List<FinancialRecord> GetFinancialRecordsForDate(LocalDate recordDate) throws FinancialRecordException{

    	
    	 if (recordDate == null)
    	 {
             throw new FinancialRecordException("Record date cannot be null.");
         }

         List<FinancialRecord> records = financialRecordServices.GetFinancialRecordsForDate(recordDate);
         if (records == null || records.isEmpty())
         {
             throw new FinancialRecordException("No financial records found for the given date.");
         }

         return records;


    }
}


