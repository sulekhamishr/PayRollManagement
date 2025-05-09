package com.java.model;

import java.sql.Date;

import java.time.LocalDate;
import java.time.Period;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Employee {

	private int  employeeId;
	private String firstName;
	private String  lastName;
    private LocalDate dateOfBirth;
	private String  gender;
	private String  email;
	private Double BasicSalary;
	
	private long phoneNumber ;
	private String  adpayrolldress ;
	private String   position ;
	private LocalDate  joiningDate ;
	private LocalDate   terminationDate ;
	
	
    public int calculateAge() {
        LocalDate today = LocalDate.now();// current date
        Period period = Period.between(this.dateOfBirth, today); //period between date of birth and current date
        return period.getYears(); //get years covered
    }







	}








