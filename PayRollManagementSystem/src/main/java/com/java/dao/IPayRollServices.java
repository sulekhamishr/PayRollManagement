package com.java.dao;

import java.time.LocalDate;
import java.util.List;
import com.java.model.Payroll;

public interface IPayRollServices {

    void generatePayroll(int employeeID, String payPeriodStartDate, String payPeriodEndDate);
    
    Payroll getPayrollById(int payrollID);
    
    List<Payroll> getPayrollsForEmployee(int employeeId);
    
    List<Payroll> getPayrollsForPeriod(String payPeriodStartDate, String payPeriodEndDate);

	void removePayrollsForEmployee(int employeeId);
}
