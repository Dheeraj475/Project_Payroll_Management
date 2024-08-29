package com.emsb.service;

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
	
	@Autowired
	private SpecialService specialService;
	
	
	public List<Payroll> getAllPayrolls() {
		return payrollRepo.findAll();
	}

	
	public Payroll addingPayroll(int employeeId, String date, String month, int year) {
		Employees employee = specialService.findEmployeeById(employeeId);

	    double grossSalary = employee.getSalary();
	    double taxes = specialService.calculateTax(grossSalary);
	    double netSalary = grossSalary - taxes;

	    // Format two decimal points
	    grossSalary = specialService.formatToTwoDecimalPoints(grossSalary);
	    taxes = specialService.formatToTwoDecimalPoints(taxes);
	    netSalary = specialService.formatToTwoDecimalPoints(netSalary);

	    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    LocalDate payDate;
	    try {
	        payDate = LocalDate.parse(date, dateFormatter);
	    } catch (DateTimeParseException e) {
	        throw new EmployeesException("Not a valid date format. Please use yyyy-MM-dd.");
	    }

	    // Validate if payDate matches the given month and year
	    if (payDate.getMonthValue() != specialService.getMonthNumber(month)) {
	        throw new EmployeesException("Pay date month does not match the given month.");
	    }
	    if (payDate.getYear() != year) {
	        throw new EmployeesException("Pay date year does not match the given year.");
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

	
	
	public List<Payroll> getPayrollByEmployeeId(int employeeId) throws EmployeesException {
		Employees employee = specialService.findEmployeeById(employeeId);
		
		List<Payroll> payrolls = payrollRepo.findByEmployeeId_EmployeeId(employeeId);
		if (payrolls == null || payrolls.isEmpty()) {
			throw new EmployeesException("Employee payroll record not found for the EmployeeId : "+employeeId);
		}
		return payrolls;
	}
	
	public List<Payroll> getEmployeePayrollByMonthAndYear(int employeeId, String month, int year) {
		Employees employee = specialService.findEmployeeById(employeeId);

        List<Payroll> payrolls = payrollRepo.findByEmployeeIdAndPayMonthAndPayYear(employeeId, month, year);
        if (payrolls == null || payrolls.isEmpty()) {
            throw new EmployeesException("Employee payroll record not found for the EmployeeId : "+employeeId+", Month : "+month + " and Year : "+year);
        }

        return payrolls;
    }
	
	
	public List<Payroll> getEmployeePayrollByYear(int employeeId, int year) {
		Employees employee = specialService.findEmployeeById(employeeId);

        List<Payroll> payrolls = payrollRepo.findByEmployeeIdAndPayYear(employeeId, year);
        if (payrolls == null || payrolls.isEmpty()) {
            throw new EmployeesException("Employee payroll record not found for the EmployeeId : "+employeeId+" and Year : "+year);
        }

        return payrolls;
    }


	
	  public List<Payroll> getPayrollsByMonthYearRange(int employeeId, String startMonthYear, String endMonthYear) {
		    Employees employee = specialService.findEmployeeById(employeeId);

	        LocalDate startDate = specialService.parseMonthYearToLocalDate(startMonthYear, true);
	        LocalDate endDate = specialService.parseMonthYearToLocalDate(endMonthYear, false);

	        List<Payroll> payrolls = payrollRepo.findByEmployeeIdAndPayDateBetween(employeeId, startDate, endDate);
	        if (payrolls == null || payrolls.isEmpty()) {
	            throw new EmployeesException("No payroll records found for EmployeeId: " + employeeId + " between " + startMonthYear + " and " + endMonthYear);
	        }

	        return payrolls;
	    }

	  


	   
	




}
