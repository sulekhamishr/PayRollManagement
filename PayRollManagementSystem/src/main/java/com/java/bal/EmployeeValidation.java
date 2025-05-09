package com.java.bal;

import java.sql.SQLException;
import java.util.List;

import com.java.dao.EmployeeServicesImp;
import com.java.dao.IEmployeeServices;
import com.java.model.Employee;

public class EmployeeValidation {

    static IEmployeeServices employDao;
    static StringBuilder sb;

    static {
        employDao = new EmployeeServicesImp();
        sb = new StringBuilder();
    }

    public Employee getEmployeeById(int employeeId) throws SQLException, ClassNotFoundException {
        if (employeeId <= 0) {
            throw new IllegalArgumentException("Invalid employee ID. Must be greater than 0.");
        }
        return employDao.getEmployeeById(employeeId);
    }

    public List<Employee> getAllEmployees() throws ClassNotFoundException, SQLException {
        return employDao.getAllEmployees();
    }

    public void addEmployee(Employee employee) throws ClassNotFoundException, SQLException {
        validateEmployee(employee, false);
        employDao.addEmployee(employee);
    }

    public void updateEmployee(Employee employee) throws ClassNotFoundException, SQLException {
        validateEmployee(employee, true);
        employDao.updateEmployee(employee);
    }

    public void removeEmployee(int employeeId) throws ClassNotFoundException, SQLException {
        if (employeeId <= 0) {
            throw new IllegalArgumentException("Invalid employee ID. Must be greater than 0.");
        }
        employDao.removeEmployee(employeeId);
    }

    private void validateEmployee(Employee employee, boolean isUpdate) {
        sb.setLength(0); 

        if (employee == null) {
            throw new IllegalArgumentException("Employee object cannot be null.");
        }

        if (isUpdate && employee.getEmployeeId() <= 0) {
            sb.append("Employee ID is required for update and must be greater than 0.\n");
        }

        if (isNullOrEmpty(employee.getFirstName())) {
            sb.append("First name cannot be empty.\n");
        }

        if (isNullOrEmpty(employee.getLastName())) {
            sb.append("Last name cannot be empty.\n");
        }

        if (isNullOrEmpty(employee.getEmail()) || !employee.getEmail().contains("@")) {
            sb.append("Valid email is required.\n");
        }

        if (employee.getPhoneNumber() <= 0) {
            sb.append("Phone number must be a positive number.\n");
        }

        if (employee.getDateOfBirth() == null) {
            sb.append("Date of birth is required.\n");
        }

        if (employee.getJoiningDate() == null) {
            sb.append("Joining date is required.\n");
        }

        if (isNullOrEmpty(employee.getGender())) {
            sb.append("Gender is required.\n");
        }

        if (isNullOrEmpty(employee.getAdpayrolldress())) {
            sb.append("Address is required.\n");
        }

        if (isNullOrEmpty(employee.getPosition())) {
            sb.append("Position is required.\n");
        }

        if (employee.getBasicSalary() == null || employee.getBasicSalary() <= 0) {
            sb.append("Basic salary must be a positive number.\n");
        }

        if (sb.length() > 0) {
            throw new IllegalArgumentException("Validation failed:\n" + sb.toString());
        }
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
}
