package com.java.main;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.java.dao.IPayRollServices;
import com.java.dao.PayRollServicesImp;

import com.java.bal.TaxServicesValidation;
import com.java.dao.*;
import com.java.model.*;

public class MainProgram {

    static Scanner sc = new Scanner(System.in);

    public static void GetAllEmployees() {
        try {
            IEmployeeServices service = new EmployeeServicesImp();
            List<Employee> employees = service.getAllEmployees();
            if (employees.isEmpty()) {
                System.out.println("No employees found.");
            } else {
                employees.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("Error retrieving employees: " + e.getMessage());
        }
    }

    public static void AddEmployee() {
        try {
            Employee emp = new Employee();

            sc.nextLine();  // Clear scanner buffer

            System.out.print("Enter First Name: ");
            emp.setFirstName(sc.nextLine());

            System.out.print("Enter Last Name: ");
            emp.setLastName(sc.nextLine());

            System.out.print("Enter Date of Birth (yyyy-mm-dd): ");
            emp.setDateOfBirth(LocalDate.parse(sc.nextLine().trim()));

            System.out.print("Enter Gender: ");
            emp.setGender(sc.nextLine());

            System.out.print("Enter Email: ");
            emp.setEmail(sc.nextLine());

            System.out.print("Enter Phone Number: ");
            emp.setPhoneNumber(sc.nextLong());  

            sc.nextLine();
            System.out.print("Enter Address: ");
            emp.setAdpayrolldress(sc.nextLine());  
            

            System.out.print("Enter Position: ");
            emp.setPosition(sc.nextLine());

            System.out.print("Enter Basic Salary: ");
            emp.setBasicSalary(sc.nextDouble());
            sc.nextLine();  

            System.out.print("Enter Joining Date (yyyy-mm-dd): ");
            emp.setJoiningDate(LocalDate.parse(sc.nextLine().trim()));

            IEmployeeServices service = new EmployeeServicesImp();
            service.addEmployee(emp);

            System.out.println("Employee added successfully.");
        } catch (Exception e) {
            System.out.println("Error adding employee: " + e.getMessage());
        }
    }


    public static void UpdateEmployee() {
        try {
            System.out.print("Enter Employee ID to update: ");
            int id = sc.nextInt();
            sc.nextLine();  

            IEmployeeServices service = new EmployeeServicesImp();
            Employee emp = service.getEmployeeById(id);

            if (emp == null) {
                System.out.println("Employee not found.");
                return;
            }

            System.out.print("New First Name (" + emp.getFirstName() + "): ");
            String fname = sc.nextLine();
            if (!fname.isEmpty()) emp.setFirstName(fname);

            System.out.print("New Last Name (" + emp.getLastName() + "): ");
            String lname = sc.nextLine();
            if (!lname.isEmpty()) emp.setLastName(lname);

            System.out.print("New Email (" + emp.getEmail() + "): ");
            String email = sc.nextLine();
            if (!email.isEmpty()) emp.setEmail(email);

            System.out.print("New Phone Number (" + emp.getPhoneNumber() + "): ");
            String phone = sc.nextLine();
            if (!phone.isEmpty()) emp.setPhoneNumber(Long.parseLong(phone));

            service.updateEmployee(emp);
            System.out.println("Employee updated.");
        } catch (Exception e) {
            System.out.println("Error updating employee: " + e.getMessage());
        }
    }

    public static void RemoveEmployee() {
        try {
            System.out.print("Enter Employee ID to delete: ");
            int id = sc.nextInt();

            ITaxServices taxService = new TaxServiceImp();
            IEmployeeServices employeeService = new EmployeeServicesImp();
            IPayRollServices payrollService = new PayRollServicesImp();
            IFinancialRecoredServices financialRecordService = new FinancialRecoredServices();

            // Remove related records first
            taxService.removeTaxesForEmployee(id);  // Remove tax records for employee
            payrollService.removePayrollsForEmployee(id);  // Remove payroll records for employee
            financialRecordService.removeFinancialRecordsForEmployee(id);  // Remove financial records for employee

            // Now remove the employee
            employeeService.removeEmployee(id);  // Remove employee record

            System.out.println("Employee and associated records deleted.");
        } catch (Exception e) {
            System.out.println("Error deleting employee: " + e.getMessage());
        }
    }




    public static void GeneratePayroll() {
        try {
            System.out.print("Enter Employee ID: ");
            int empId = sc.nextInt();
            sc.nextLine();  

            System.out.print("Enter Start Date (yyyy-mm-dd): ");
            String start = sc.nextLine();

            System.out.print("Enter End Date (yyyy-mm-dd): ");
            String end = sc.nextLine();
           
            
//            System.out.print("Enter overtime ");
//            double overtimePay = sc.nextDouble();

            IPayRollServices service = new PayRollServicesImp();
            service.generatePayroll(empId, start, end);
            System.out.println("Payroll generated successfully.");
        } catch (Exception e) {
            System.out.println("Error generating payroll: " + e.getMessage());
        }
    }

    public static void GetPayrollById() {
        try {
            System.out.print("Enter Payroll ID: ");
            int payrollId = sc.nextInt();

            IPayRollServices service = new PayRollServicesImp();
            Payroll payroll = service.getPayrollById(payrollId);

            if (payroll != null) {
                System.out.println(payroll);
            } else {
                System.out.println("No payroll found with ID: " + payrollId);
            }
        } catch (Exception e) {
            System.out.println("Error retrieving payroll: " + e.getMessage());
        }
    }

    public static void GetPayrollsForEmployee() {
        try {
            System.out.print("Enter Employee ID: ");
            int empId = sc.nextInt();

           
            IPayRollServices service = new PayRollServicesImp();
            List<Payroll> payrolls = service.getPayrollsForEmployee(empId);

            if (payrolls.isEmpty()) {
                System.out.println("No payroll records found.");
            } else {
                payrolls.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("retrieving payrolls: " + e.getMessage());
        }
    }

    public static <IPayrollServices> void GetPayrollsForPeriod() {
    	try {
    		sc.nextLine();
            System.out.print("Enter Start Date (yyyy-mm-dd): ");
            String startDateString = sc.nextLine().trim();
            if (startDateString.isEmpty()) {
                System.out.println("Start Date cannot be empty.");
                return;
            }

            System.out.print("Enter End Date (yyyy-mm-dd): ");
            String endDateString = sc.nextLine().trim();
            if (endDateString.isEmpty()) {
                System.out.println("End Date cannot be empty.");
                return;
            }

            LocalDate startDate = LocalDate.parse(startDateString);
            LocalDate endDate = LocalDate.parse(endDateString);

            IPayRollServices payRollServices = new PayRollServicesImp();
            List<Payroll> payrolls = payRollServices.getPayrollsForPeriod(startDateString, endDateString);

            if (payrolls.isEmpty()) {
                System.out.println("No payroll records found for the specified period.");
            } else {
                System.out.println("Payroll records for the period (" + startDate + " to " + endDate + "):");
                for (Payroll payroll : payrolls) {
                    System.out.println(payroll);
                }
            }
        } catch (Exception e) {
            System.out.println("Error retrieving payrolls for the period: " + e.getMessage());
        }
    }

    public static void CalculateTax() {
        try {
            System.out.print("Enter Employee ID: ");
            int empId = sc.nextInt();

            System.out.print("Enter Tax Year: ");
            int year = sc.nextInt();

            ITaxServices service = new TaxServiceImp();
            double tax = service.CalculateTax(empId, year);

            System.out.println("Estimated Tax: " + tax);
        } catch (Exception e) {
            System.out.println("Error calculating tax: " + e.getMessage());
        }
    }

    public static void GetTaxById() {
        try {
            System.out.print("Enter Tax ID: ");
            int taxId = sc.nextInt();

            ITaxServices service = new TaxServiceImp();
            Tax tax = service.GetTaxById(taxId);

            if (tax != null) {
                System.out.println(tax);
            } else {
                System.out.println("No tax found with ID: " + taxId);
            }
        } catch (Exception e) {
            System.out.println("Error retrieving tax: " + e.getMessage());
        }
    }

    public static void GetTaxesForEmployee() {
        try {
            System.out.print("Enter Employee ID: ");
            int empId = sc.nextInt();

            ITaxServices service = new TaxServiceImp();
            List<Tax> taxes = service.GetTaxesForEmployee(empId);

            if (taxes.isEmpty()) {
                System.out.println("No tax records found for this employee.");
            } else {
                taxes.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("Error retrieving taxes for employee: " + e.getMessage());
        }
    }

    public static void GetTaxesForYear() {
        try {
            System.out.print("Enter Tax Year: ");
            int year = sc.nextInt();

            ITaxServices service = new TaxServiceImp();
            List<Tax> taxes = service.GetTaxesForYear(year);

            if (taxes.isEmpty()) {
                System.out.println("No tax records found for this year.");
            } else {
                taxes.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("Error retrieving taxes for the year: " + e.getMessage());
        }
    }

    public static void AddFinancialRecord() {
        try {
            System.out.print("Enter Employee ID: ");
            int empId = sc.nextInt();
            sc.nextLine();  

            System.out.print("Enter Description: ");
            String desc = sc.nextLine();

            System.out.print("Enter Amount: ");
            double amt = sc.nextDouble();
            sc.nextLine(); 

            System.out.print("Enter Record Type (Credit/Debit): ");
            String type = sc.nextLine();

            IFinancialRecoredServices service = new FinancialRecoredServices();
            service.AddFinancialRecord(empId, desc, amt, type);

            System.out.println("Financial record added.");
        } catch (Exception e) {
            System.out.println("Error adding financial record: " + e.getMessage());
        }
    }

    public static void GetFinancialRecordById() {
        try {
            System.out.print("Enter Financial Record ID: ");
            int recordId = sc.nextInt();

            IFinancialRecoredServices service = new FinancialRecoredServices();
            FinancialRecord record = service.GetFinancialRecordById(recordId);

            if (record != null) {
                System.out.println(record);
            } else {
                System.out.println("No financial record found with ID: " + recordId);
            }
        } catch (Exception e) {
            System.out.println("Error retrieving financial record: " + e.getMessage());
        }
    }

    public static void GetFinancialRecordsForEmployee() {
        try {
            System.out.print("Enter Employee ID: ");
            int empId = sc.nextInt();

            IFinancialRecoredServices service = new FinancialRecoredServices();
            List<FinancialRecord> records = service.GetFinancialRecordsForEmployee(empId);

            if (records.isEmpty()) {
                System.out.println("No financial records found.");
            } else {
                records.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("Error retrieving financial records: " + e.getMessage());
        }
    }

    public static void GetFinancialRecordsForDate() {
        try {
        	
        	sc.nextLine();
            System.out.print("Enter the date (yyyy-mm-dd): ");
            LocalDate date = LocalDate.parse(sc.nextLine());

         
            IFinancialRecoredServices service = new FinancialRecoredServices();
            List<FinancialRecord> records = service.GetFinancialRecordsForDate(date);

            if (records.isEmpty()) {
                System.out.println("No records found for the date.");
            } else {
                records.forEach(System.out::println);
            }
        } catch (Exception e) {
            
            
            
            System.out.println("Error retrieving financial records for the date: " + e.getMessage());
        }
    }

    public static void DisplayMainMenu() {
        System.out.println("\n=== Main Menu ===");
        System.out.println("1. Get All Employees");
        System.out.println("2. Add Employee");
        System.out.println("3. Update Employee");
        System.out.println("4. Remove Employee");
        System.out.println("5. Generate Payroll");
        System.out.println("6. Get Payroll By ID");
        System.out.println("7. Get Payrolls For Employee");
        System.out.println("8. Get Payrolls For Period");
        System.out.println("9. Calculate Tax");
        System.out.println("10. Get Tax By ID");
        System.out.println("11. Get Taxes For Employee");
        System.out.println("12. Get Taxes For Year");
        System.out.println("13. Add Financial Record");
        System.out.println("14. Get Financial Record By ID");
        System.out.println("15. Get Financial Records For Employee");
        System.out.println("16. Get Financial Records For Date");
        
        System.out.println("17. Exit");
        System.out.print("Choose an option: ");
    }

    public static void main(String[] args) {
        boolean running = true;
        
        while (running) {
            DisplayMainMenu();
            int choice = -1;
            
            try {
                choice = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                sc.nextLine();  
                continue;
            }
            
            switch (choice) {
                case 1: 
                    GetAllEmployees(); break;
                case 2: 
                    AddEmployee(); break;
                case 3: 
                    UpdateEmployee(); break;
                case 4: 
                    RemoveEmployee(); break;
                case 5: 
                    GeneratePayroll(); break;
                case 6: 
                    GetPayrollById(); break;
                case 7: 
                    GetPayrollsForEmployee(); break;
                case 8: 
                    GetPayrollsForPeriod(); break;
                case 9: 
                    CalculateTax(); break;
                case 10: 
                    GetTaxById(); break;
                case 11: 
                    GetTaxesForEmployee(); break;
                case 12: 
                    GetTaxesForYear(); break;
                case 13: 
                    AddFinancialRecord(); break;
                case 14: 
                    GetFinancialRecordById(); break;
                case 15: 
                    GetFinancialRecordsForEmployee(); break;
                case 16: 
                    GetFinancialRecordsForDate(); break;
                case 17: 
                    running = false; 
                    System.out.println("Exiting..."); 
                    break;
                default: 
                    System.out.println("Invalid choice. Please choose a valid option.");
            }
        }
    }
}

