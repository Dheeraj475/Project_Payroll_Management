package com.emsb.service;

import java.time.LocalDate;

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
	
	@Autowired
	private TaxService taxService;

	public Payroll processPayroll(int employeeId, String month, int year) {
		Employees employee = employeeRepo.findById(employeeId)
				.orElseThrow(() -> new EmployeesException("Employee Id not found!"));

		double grossSalary = employee.getSalary();
		double tax = taxService.calculateTax(grossSalary);
		double netSalary = grossSalary - tax;

		Payroll payroll = new Payroll();
		payroll.setGrossSalary(grossSalary);
		payroll.setTaxAmount(tax);
		payroll.setNetSalary(netSalary);
		payroll.setMonth(month);
		payroll.setYear(year);
		payroll.setPayDate(LocalDate.now());
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



}
