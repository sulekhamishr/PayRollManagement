package com.java.bal;

import java.sql.SQLException;
import java.time.Year;
import java.util.List;

import com.java.dao.ITaxServices;
import com.java.dao.TaxServiceImp;
import com.java.exception.TaxCalculationException;
import com.java.model.Tax;

public class TaxServicesValidation {

    private ITaxServices taxServices;
    private StringBuilder sb;

    public TaxServicesValidation() {
        try {
            this.taxServices = new TaxServiceImp();
        } catch (ClassNotFoundException | SQLException e) {

        	e.printStackTrace();
        }
        this.sb = new StringBuilder();
    }

    public boolean addTax(Tax tax) throws TaxCalculationException {
        sb.setLength(0); 

        if (tax.getTaxID() <= 0) {
            sb.append("Tax ID must be greater than zero. ");
        }

        if (tax.getEmployeeID() <= 0) {
            sb.append("Employee ID must be greater than zero. ");
        }

        int currentYear = Year.now().getValue();
        if (tax.getTaxYear() < 2000 || tax.getTaxYear() > currentYear) {
            sb.append("Tax year must be between 2000 and ").append(currentYear).append(". ");
        }

        if (tax.getTaxAmount() < 0) {
            sb.append("Tax amount must not be negative. ");
        }

        if (sb.length() > 0) {
            throw new TaxCalculationException("Validation failed: " + sb.toString());
        }

        return taxServices.addTax(tax);
    }

    public double calculateTax(int employeeId, int taxYear) throws TaxCalculationException {
        sb.setLength(0); 

        if (employeeId <= 0) {
            sb.append("Employee ID must be greater than zero. ");
        }

        int currentYear = Year.now().getValue();
        if (taxYear < 2000 || taxYear > currentYear) {
            sb.append("Tax year must be between 2000 and ").append(currentYear).append(". ");
        }

        if (sb.length() > 0) {
            throw new TaxCalculationException("Validation failed: " + sb.toString());
        }

        return taxServices.CalculateTax(employeeId, taxYear);
    }

    public Tax getTaxById(int taxId) throws TaxCalculationException {
        if (taxId <= 0) {
            throw new TaxCalculationException("Tax ID must be a positive number.");
        }
        return taxServices.GetTaxById(taxId);
    }

    public List<Tax> getTaxesForEmployee(int employeeId) throws TaxCalculationException, ClassNotFoundException {
        if (employeeId <= 0) {
            throw new TaxCalculationException("Invalid employee ID.");
        }
        return taxServices.GetTaxesForEmployee(employeeId);
    }

    public List<Tax> getTaxesForYear(int taxYear) throws TaxCalculationException {
        int currentYear = Year.now().getValue();
        if (taxYear < 2000 || taxYear > currentYear) {
            throw new TaxCalculationException("Tax year must be between 2000 and " + currentYear + ".");
        }
        return taxServices.GetTaxesForYear(taxYear);
    }
}
