package com.java.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
	

	public class Payroll {
	    private int payrollID;
	    private int employeeID;
	    private String payPeriodStartDate;
	    private String payPeriodEndDate;
	    
	    private double basicSalary;
	    private double overtimePay;
	    private double deductions;
	    private double netSalary;
	
	
	
		
	
	}

