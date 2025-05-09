package com.java.dao;

import java.time.LocalDate;

import java.util.List;

import com.java.model.Employee;
import com.java.model.FinancialRecord;

public interface IFinancialRecoredServices {

	
    void AddFinancialRecord(int employeeId,String description,double amount,String recordType);
    FinancialRecord GetFinancialRecordById(int recordId);
    List<FinancialRecord> GetFinancialRecordsForEmployee(int employeeId) ;
	List<FinancialRecord> GetFinancialRecordsForDate(LocalDate recordDate);
	void removeFinancialRecordsForEmployee(int employeeId);
}
