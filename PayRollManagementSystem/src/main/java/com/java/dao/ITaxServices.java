package com.java.dao;

import java.util.List;

import com.java.model.Tax;

public interface ITaxServices {
	


    public boolean addTax(Tax tax) ;

	double CalculateTax(int employeeId, int taxYear);

	Tax GetTaxById(int taxId);

	List<Tax> GetTaxesForEmployee(int employeeId) throws ClassNotFoundException;

	List<Tax> GetTaxesForYear(int taxYear);

	void removeTaxesForEmployee(int employeeId);


}
