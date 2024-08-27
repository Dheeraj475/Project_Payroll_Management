package com.emsb;

import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.emsb.entity.Employees;
import com.emsb.entity.Payroll;
import com.emsb.repository.EmployeesRepository;
import com.emsb.repository.PayrollRepository;
import com.emsb.service.PayrollService;

@SpringBootTest(classes = PayrollManagementApplication.class)
public class PayrollServiceTest {

	@Mock
	private EmployeesRepository employeeRepo;

	@Mock
	private PayrollRepository payrollRepo;

	@InjectMocks
	private PayrollService payrollService;

	@Test
	public void testProcessingPayroll() {
		// Create an Employees object
		Employees emp = new Employees();
		emp.setEmployeeId(1);
		emp.setName("Ravi Badodi");
		emp.setAge(23);
		emp.setGender("Male");
		emp.setDesignation("Software Developer");
		emp.setRating(3);
		emp.setSalary(36100.0);

		// Mock employee repository findById
		when(employeeRepo.findById(1)).thenReturn(java.util.Optional.of(emp));

		// Mock tax calculation
		double mockTax = payrollService.calculateTax(emp.getSalary());
		
		// Mock payroll repository save
		Payroll payroll = new Payroll();
		payroll.setGrossSalary(emp.getSalary());
		payroll.setTaxAmount(mockTax);
		payroll.setNetSalary(emp.getSalary() - mockTax);
		payroll.setPayMonth("August");
		payroll.setPayYear(2023);
		payroll.setPayDate(LocalDate.of(2023, 8, 27));
		payroll.setEmployeeId(emp);

		when(payrollRepo.save(payroll)).thenReturn(payroll);

		// Call the service method
		Payroll result = payrollService.processingPayroll(1, "2023-08-27", "August", 2023);

		// Verify the result
		Assertions.assertNotNull(result);
		Assertions.assertEquals(36100.0, result.getGrossSalary(), 0.01);
		Assertions.assertEquals(mockTax, result.getTaxAmount(), 0.01);
		Assertions.assertEquals(31100.0, result.getNetSalary(), 0.01);
		Assertions.assertEquals("August", result.getPayMonth());
		Assertions.assertEquals(2023, result.getPayYear());
		Assertions.assertEquals(LocalDate.of(2023, 8, 27), result.getPayDate());
	}

	@Test
	public void testGeneratePayStub() {
		// Create an Employees object
		Employees emp = new Employees();
		emp.setEmployeeId(1);
		emp.setName("Ravi Badodi");
		emp.setAge(23);
		emp.setGender("Male");
		emp.setDesignation("Software Developer");

		// Create a Payroll object
		Payroll payroll = new Payroll();
		payroll.setGrossSalary(36100.0);
		payroll.setTaxAmount(5000.0);
		payroll.setNetSalary(31100.0);
		payroll.setPayMonth("August");
		payroll.setPayYear(2023);
		payroll.setPayDate(LocalDate.of(2023, 8, 27));
		payroll.setEmployeeId(emp);

		// Mock employee repository findById
		when(employeeRepo.findById(1)).thenReturn(java.util.Optional.of(emp));

		// Mock payroll repository findByEmployeeIdAndPayMonthAndPayYear
		List<Payroll> payrollList = new ArrayList<>();
		payrollList.add(payroll);
		when(payrollRepo.findByEmployeeIdAndPayMonthAndPayYear(1, "August", 2023)).thenReturn(payrollList);

		// Call the service method
		String payStub = payrollService.generatePayStub(1, "August", 2023);

		// Verify the result
		Assertions.assertNotNull(payStub);
		Assertions.assertTrue(payStub.contains("Ravi Badodi"));
		Assertions.assertTrue(payStub.contains("Software Developer"));
		Assertions.assertTrue(payStub.contains("36100.0"));
		Assertions.assertTrue(payStub.contains("5000.0"));
		Assertions.assertTrue(payStub.contains("31100.0"));
	}

	@Test
	public void testFindPayrollByEmployeeId() {
		// Create an Employees object
		Employees emp = new Employees();
		emp.setEmployeeId(1);
		emp.setName("Ravi Badodi");

		// Create a Payroll object
		Payroll payroll = new Payroll();
		payroll.setEmployeeId(emp);

		List<Payroll> payrollList = new ArrayList<>();
		payrollList.add(payroll);

		// Mock payroll repository findByEmployeeId_EmployeeId
		when(payrollRepo.findByEmployeeId_EmployeeId(1)).thenReturn(payrollList);

		// Call the service method
		List<Payroll> result = payrollService.findPayrollByEmployeeId(1);

		// Verify the result
		Assertions.assertNotNull(result);
		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals(1, result.get(0).getEmployeeId().getEmployeeId());
	}

	@Test
	public void testCalculateSalary() {
		// Create an Employees object
		Employees emp = new Employees();
		emp.setEmployeeId(1);
		emp.setAge(23);
		emp.setDesignation("Software Developer");
		emp.setRating(3);

		// Call the calculateSalary method
		double salary = payrollService.calculateSalary(emp);

		// Verify the calculated salary based on rating and designation
		Assertions.assertNotNull(salary);
		Assertions.assertTrue(salary > 0);
	}

	@Test
	public void testCalculateTax() {
		// Call the calculateTax method with a salary of 50000.0
		double tax = payrollService.calculateTax(50000.0);

		// Verify the tax amount
		Assertions.assertNotNull(tax);
		Assertions.assertTrue(tax >= 0);
	}

	@Test
	public void testFindAllPayrolls() {
		// Mock payroll repository findAll
		List<Payroll> payrollList = new ArrayList<>();
		Payroll payroll = new Payroll();
		payroll.setGrossSalary(36100.0);
		payroll.setTaxAmount(5000.0);
		payroll.setNetSalary(31100.0);
		payrollList.add(payroll);

		when(payrollRepo.findAll()).thenReturn(payrollList);

		// Call the service method
		List<Payroll> result = payrollService.findAllPayrolls();

		// Verify the result
		Assertions.assertNotNull(result);
		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals(36100.0, result.get(0).getGrossSalary(), 0.01);
	}
}
