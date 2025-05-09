package com.java.dao;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

class Employee {
    private double basicSalary;
    private double bonus;
    private double allowances;

    public Employee(double basicSalary, double bonus, double allowances) {
        this.basicSalary = basicSalary;
        this.bonus = bonus;
        this.allowances = allowances;
    }

    public double getBasicSalary() { return basicSalary; }
    public double getBonus() { return bonus; }
    public double getAllowances() { return allowances; }
}

class Deductions {
    private double tax;
    private double insurance;

    public Deductions(double tax, double insurance) {
        this.tax = tax;
        this.insurance = insurance;
    }

    public double getTax() { return tax; }
    public double getInsurance() { return insurance; }
}

class PayrollService {
    public double calculateGrossSalary(Employee employee) {
        if (employee.getBasicSalary() < 0) {
            throw new IllegalArgumentException("Invalid salary data.");
        }
        return employee.getBasicSalary() + employee.getBonus() + employee.getAllowances();
    }

    public double calculateNetSalary(double grossSalary, Deductions deductions) {
        return grossSalary - (deductions.getTax() + deductions.getInsurance());
    }

    public double calculateTax(Employee employee) {
        double gross = calculateGrossSalary(employee);
        if (gross > 200000) return gross * 0.25;
        else if (gross > 100000) return gross * 0.20;
        else return gross * 0.10;
    }

    public double[] processPayroll(Employee[] employees) {
        double[] netSalaries = new double[employees.length];
        for (int i = 0; i < employees.length; i++) {
            double gross = calculateGrossSalary(employees[i]);
            double tax = calculateTax(employees[i]);
            double insurance = gross * 0.05;
            netSalaries[i] = calculateNetSalary(gross, new Deductions(tax, insurance));
        }
        return netSalaries;
    }
}

public class AppTest {

    private PayrollService payrollService;

    @Before
    public void setup() {
        payrollService = new PayrollService();
    }

    @Test
    public void testCalculateGrossSalary() {
        Employee emp = new Employee(50000, 5000, 3000);
        double gross = payrollService.calculateGrossSalary(emp);
        assertEquals(58000, gross, 0.01);
    }

    @Test
    public void testCalculateNetSalary() {
        double gross = 58000;
        Deductions ded = new Deductions(8000, 2000);
        double net = payrollService.calculateNetSalary(gross, ded);
        assertEquals(48000, net, 0.01);
    }

    @Test
    public void testHighIncomeTax() {
        Employee emp = new Employee(200000, 30000, 20000);
        double tax = payrollService.calculateTax(emp);
        assertTrue(tax > 40000);
    }

    @Test
    public void testPayrollProcessing() {
        Employee[] emps = {
            new Employee(40000, 5000, 2000),
            new Employee(60000, 7000, 3000)
        };
        double[] results = payrollService.processPayroll(emps);
        assertEquals(2, results.length);
        for (double net : results) assertTrue(net > 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidEmployee() {
        Employee emp = new Employee(-10000, 0, 0);
        payrollService.calculateGrossSalary(emp);
    }
}
