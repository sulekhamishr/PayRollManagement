package com.java.bal;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import com.java.dao.IPayRollServices;
import com.java.dao.PayRollServicesImp;
import com.java.exception.PayRollException;
import com.java.model.Payroll;

public class PayRollValidation {

    private final IPayRollServices payRollServices;
    private final StringBuilder sb;

    public PayRollValidation(IPayRollServices payRollServices) {
        this.payRollServices = payRollServices;
        this.sb = new StringBuilder();
    }

    public PayRollValidation() {
        this(new PayRollServicesImp());
    }

    public void generatePayroll(int employeeID, String payPeriodStartDate, String payPeriodEndDate) throws PayRollException {
        sb.setLength(0); 

        if (employeeID <= 0) {
            sb.append("Invalid Employee ID. ");
        }

        LocalDate startDate = null;
        LocalDate endDate = null;

        try {
            startDate = LocalDate.parse(payPeriodStartDate);
        } catch (DateTimeParseException e) {
            sb.append("Invalid start date format (expected yyyy-MM-dd). ");
        }

        try {
            endDate = LocalDate.parse(payPeriodEndDate);
        } catch (DateTimeParseException e) {
            sb.append("Invalid end date format (expected yyyy-MM-dd). ");
        }

        if (startDate != null && endDate != null) {
            if (startDate.isAfter(endDate)) {
                sb.append("Start date cannot be after end date. ");
            }

            if (endDate.isAfter(LocalDate.now())) {
                sb.append("End date cannot be in the future. ");
            }

            long days = java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate) + 1;
            if (days != 15 && days != 30) {
                sb.append("Payroll period must be 15 or 30 days. ");
            }
        }

        if (sb.length() > 0) {
            throw new PayRollException("Validation Error(s): " + sb.toString());
        }

        payRollServices.generatePayroll(employeeID, payPeriodStartDate, payPeriodEndDate);
    }

    public Payroll getPayrollById(int payrollID) {
        return payRollServices.getPayrollById(payrollID);
    }

    public List<Payroll> getPayrollsForEmployee(int employeeId) {
        return payRollServices.getPayrollsForEmployee(employeeId);
    }

    public List<Payroll> getPayrollsForPeriod(String payPeriodStartDate, String payPeriodEndDate) {
        return payRollServices.getPayrollsForPeriod(payPeriodStartDate, payPeriodEndDate);
    }
}
