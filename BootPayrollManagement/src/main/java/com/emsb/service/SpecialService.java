package com.emsb.service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emsb.entity.Employees;
import com.emsb.entity.Payroll;
import com.emsb.exception.EmployeesException;
import com.emsb.model.FinancialSummaryResponse;
import com.emsb.repository.EmployeesRepository;

@Service
public class SpecialService {
	
	@Autowired
	private EmployeesRepository employeesRepo;

	public Employees findEmployeeById(int employeeId) {
		
		Optional<Employees> employee = employeesRepo.findById(employeeId);

	    if (employee.isPresent()) {
	        return employee.get();
	    } else {
	        
	        throw new EmployeesException("Employee id not found for the EmployeeId: " + employeeId);
	    }
	}

	public double formatToTwoDecimalPoints(double value) {
		DecimalFormat df = new DecimalFormat("#.##");
		return Double.parseDouble(df.format(value));
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
				throw new EmployeesException("Designation must be 'Software Developer' for age group 21-26!");

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
				throw new EmployeesException(
						"Designation must be 'Senior Developer', 'Tech Lead' and 'Architect' for age group 26-35!");
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
				throw new EmployeesException(
						"Designation must be 'Manager', 'Senior Manager' and 'Delivery Head' for age group 35-60!");
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

		monthTax = yearTax / 12;

		return monthTax;
	}

	public int getMonthNumber(String month) {
		DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MMMM");
		try {
			LocalDate date = LocalDate.parse("2021-" + month + "-01", DateTimeFormatter.ofPattern("yyyy-MMMM-dd"));
			return date.getMonthValue();
		} catch (DateTimeParseException e) {
			throw new EmployeesException("Not a valid month format. Please use full month name (e.g., January).");
		}
	}

	public LocalDate parseMonthYearToLocalDate(String monthYear, boolean isStart) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-yyyy");
			LocalDate date = LocalDate.parse("01-" + monthYear, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
			return isStart ? date.withDayOfMonth(1) : date.withDayOfMonth(date.lengthOfMonth());
		} catch (DateTimeParseException e) {
			throw new EmployeesException("Not a valid date format. Please use MM-YYYY.");
		}
	}

	public FinancialSummaryResponse calculateTotals(List<Payroll> payrolls) {

		double totalGrossSalary = payrolls.stream().mapToDouble(payroll -> payroll.getGrossSalary()).sum();
		double totalTaxAmount = payrolls.stream().mapToDouble(payroll -> payroll.getTaxAmount()).sum();
		double totalNetSalary = payrolls.stream().mapToDouble(payroll -> payroll.getNetSalary()).sum();

		totalGrossSalary = formatToTwoDecimalPoints(totalGrossSalary);
		totalTaxAmount = formatToTwoDecimalPoints(totalTaxAmount);
		totalNetSalary = formatToTwoDecimalPoints(totalNetSalary);

		return new FinancialSummaryResponse(payrolls, totalGrossSalary, totalTaxAmount, totalNetSalary);
	}

}
