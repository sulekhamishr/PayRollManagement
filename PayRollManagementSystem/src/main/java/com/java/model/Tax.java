package com.java.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
	public class Tax {
	    private   int taxID;
	    private   int  employeeID;
	    private  int  taxYear;
	    private  double  taxableIncome;
	    private  double  taxAmount;
	}


