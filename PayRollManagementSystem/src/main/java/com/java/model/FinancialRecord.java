package com.java.model;


import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

	public class FinancialRecord {
	    private int recordID;
	    private int employeeID;
	    private LocalDate recordDate;
	    
	    private String description;
	    private double amount;
	    private String recordType;
	}

