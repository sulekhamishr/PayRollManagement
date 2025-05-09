package com.java.dao;

import java.sql.SQLException;
import java.util.List;

import com.java.model.Employee;

public interface IEmployeeServices {

    Employee getEmployeeById(int employeeId) throws SQLException, ClassNotFoundException;

    List<Employee> getAllEmployees() throws SQLException, ClassNotFoundException;

    void addEmployee(Employee employee) throws SQLException, ClassNotFoundException;

    void updateEmployee(Employee employee) throws SQLException, ClassNotFoundException;

    void removeEmployee(int employeeId) throws SQLException, ClassNotFoundException;
}
