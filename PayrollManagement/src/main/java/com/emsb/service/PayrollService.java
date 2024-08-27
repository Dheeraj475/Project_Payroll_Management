package com.emsb.service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emsb.entity.Employees;
import com.emsb.entity.Payroll;
import com.emsb.exception.EmployeesException;
import com.emsb.repository.EmployeesRepository;
import com.emsb.repository.PayrollRepository;

@Service
public class PayrollService {

	@Autowired
	private EmployeesRepository employeeRepo;

	@Autowired
	private PayrollRepository payrollRepo;

	
	public Payroll processingPayroll(int employeeId, String date, String month, int year) {
		Employees employee = employeeRepo.findById(employeeId)
				.orElseThrow(() -> new EmployeesException("Employee Id not found!"));

		double grossSalary = employee.getSalary();
		double taxes = calculateTax(grossSalary);
		double netSalary = grossSalary - taxes;

		// Format two decimal points
		grossSalary = formatToTwoDecimalPoints(grossSalary);
		taxes = formatToTwoDecimalPoints(taxes);
		netSalary = formatToTwoDecimalPoints(netSalary);

		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate payDate;
		try {
			payDate = LocalDate.parse(date, dateFormatter);
		} catch (DateTimeParseException e) {
			throw new EmployeesException("Invalid date format. Please use yyyy-MM-dd.");
		}

		Payroll payroll = new Payroll();
		payroll.setGrossSalary(grossSalary);
		payroll.setTaxAmount(taxes);
		payroll.setNetSalary(netSalary);
		payroll.setPayMonth(month);
		payroll.setPayYear(year);
		payroll.setPayDate(payDate);
		payroll.setEmployeeId(employee);

		return payrollRepo.save(payroll);
	}

	public double calculateSalary(Employees employee) {
		int age = employee.getAge();
		String designation = employee.getDesignation();
		int rating = employee.getRating();

		double maxSalary = 0;
		double salary = 0;

		// Determine base salary based on age and designation
		if (age >= 21 && age <= 26) {
			switch (designation) {
			case "Software Developer":
				maxSalary = 40000;
				break;
			default:
				throw new EmployeesException("Invalid designation for age group 21-26!");

			}

		} else if (age > 26 && age <= 35) {
			switch (designation) {
			case "Senior Developer":
				maxSalary = 80000;
				break;
			case "Tech Lead":
				maxSalary = 90000;
				break;
			case "Architect":
				maxSalary = 100000;
				break;
			default:
				throw new EmployeesException("Invalid designation for age group 26-35!");
			}
		} else if (age > 35 && age <= 60) {
			switch (designation) {
			case "Manager":
				maxSalary = 150000;
				break;
			case "Senior Manager":
				maxSalary = 200000;
				break;
			case "Delivery Head":
				maxSalary = 300000;
				break;
			default:
				throw new EmployeesException("Invalid designation for age group 35-60!");
			}
		} else {
			throw new EmployeesException("Age must be between 21-60!");
		}

		// Apply rating adjustments
		switch (rating) {
		case 1:
			salary = maxSalary;
			break;
		case 2:
			salary = maxSalary * Math.pow(0.95, 1);
			break;
		case 3:
			salary = maxSalary * Math.pow(0.95, 2);
			break;
		case 4:
			salary = maxSalary * Math.pow(0.95, 3);
			break;
		case 5:
			salary = maxSalary * Math.pow(0.95, 4);
			break;
		default:
			throw new EmployeesException("Rating must be between 1-5!");
		}

		return salary;
	}
	
	public double calculateTax(double salary) {

		salary = salary * 12;

		double yearTax = 0.0;
		double monthTax = 0.0;

		/* 1. No tax for income up to 500,000 */
		if (salary <= 500000) {
			yearTax = 0;
		}
		/* 2. 10% tax for income between 500,001 and 700,000 */
		else if (salary <= 700000) {
			yearTax = (salary - 500000) * 0.10;
		}
		/* 3. 20% tax for income between 700,001 and 1,000,000 */
		else if (salary <= 1000000) {
			yearTax = (200000 * 0.10) + ((salary - 700000) * 0.20);
		}
		/* 4. 30% tax for income above 1,000,000 */
		else {
			yearTax = (200000 * 0.10) + (300000 * 0.20) + ((salary - 1000000) * 0.30);
		}
		
		monthTax = yearTax/12;

		return monthTax;
	}

	public List<Payroll> findAllPayrolls() {
		return payrollRepo.findAll();
	}
	
	public String generatePayStub(int employeeId, String month, int year) {
	    Employees employee = employeeRepo.findById(employeeId)
	            .orElseThrow(() -> new EmployeesException("Employee Id not found!"));

	    List<Payroll> payrolls = payrollRepo.findByEmployeeIdAndPayMonthAndPayYear(employeeId, month, year);
	    if (payrolls.isEmpty()) {
	        throw new EmployeesException("No payroll records found for the specified month and year!");
	    }

	    Payroll payroll = payrolls.get(0); // Assuming one record per month per employee

	    // Create a formatted pay stub string
	    StringBuilder payStub = new StringBuilder();
	    payStub.append("Employee Name: ").append(employee.getName()).append("\n");
	    payStub.append("Designation: ").append(employee.getDesignation()).append("\n");
	    payStub.append("Pay Date: ").append(payroll.getPayDate()).append("\n");
	    payStub.append("Month: ").append(month).append(", Year: ").append(year).append("\n");
	    payStub.append("Gross Salary: ₹").append(payroll.getGrossSalary()).append("\n");
	    payStub.append("Taxes: ₹").append(payroll.getTaxAmount()).append("\n");
	    payStub.append("Net Salary: ₹").append(payroll.getNetSalary()).append("\n");

	    return payStub.toString();
	}

	public List<Payroll> findPayrollByEmployeeId(int employeeId) throws EmployeesException {
		List<Payroll> payrolls = payrollRepo.findByEmployeeId_EmployeeId(employeeId);
		if (payrolls == null || payrolls.isEmpty()) {
			throw new EmployeesException(employeeId + " : Payroll record not found!");
		}
		return payrolls;
	}

	public double formatToTwoDecimalPoints(double value) {
		DecimalFormat df = new DecimalFormat("#.##");
		return Double.parseDouble(df.format(value));
	}

}
