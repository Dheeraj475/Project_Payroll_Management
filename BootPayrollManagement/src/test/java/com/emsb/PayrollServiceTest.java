package com.emsb;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.emsb.entity.Employees;
import com.emsb.entity.Payroll;
import com.emsb.exception.EmployeesException;
import com.emsb.repository.EmployeesRepository;
import com.emsb.repository.PayrollRepository;
import com.emsb.service.PayrollService;
import com.emsb.service.SpecialService;

@SpringBootTest
public class PayrollServiceTest {

    @Mock
    private EmployeesRepository employeeRepo;

    @Mock
    private PayrollRepository payrollRepo;

    @Mock
    private SpecialService specialService;

    @InjectMocks
    private PayrollService payrollService;

    private Employees employee;
    private Payroll payroll;
    private double mockGrossSalary;
    private double mockTaxAmount;
    private double mockNetSalary;

    @BeforeEach
    public void setup() {
        employee = new Employees();
        employee.setEmployeeId(1);
        employee.setName("Ravi Badodi");
        employee.setAge(23);
        employee.setGender("Male");
        employee.setDesignation("Software Developer");
        employee.setRating(3);
        employee.setSalary(36100.0);

        mockGrossSalary = employee.getSalary();
        mockTaxAmount = 0;
        mockNetSalary = mockGrossSalary - mockTaxAmount;

        payroll = new Payroll();
        payroll.setGrossSalary(mockGrossSalary);
        payroll.setTaxAmount(mockTaxAmount);
        payroll.setNetSalary(mockNetSalary);
        payroll.setPayMonth("August");
        payroll.setPayYear(2023);
        payroll.setPayDate(LocalDate.of(2023, 8, 27));
        payroll.setEmployeeId(employee);

        when(specialService.findEmployeeById(1)).thenReturn(employee);
        when(specialService.calculateTax(mockGrossSalary)).thenReturn(mockTaxAmount);
        when(specialService.formatToTwoDecimalPoints(mockGrossSalary)).thenReturn(mockGrossSalary);
        when(specialService.formatToTwoDecimalPoints(mockTaxAmount)).thenReturn(mockTaxAmount);
        when(specialService.formatToTwoDecimalPoints(mockNetSalary)).thenReturn(mockNetSalary);
    }

    @Test
    public void testAddingPayroll() {
        Employees mockEmployee = new Employees();
        mockEmployee.setEmployeeId(1);
        mockEmployee.setSalary(32000);

        // Setting up the mock responses for the specialService methods
        when(specialService.findEmployeeById(1)).thenReturn(mockEmployee);
        when(specialService.calculateTax(32000)).thenReturn(0.0);  
        when(specialService.formatToTwoDecimalPoints(anyDouble())).thenAnswer(invocation -> invocation.getArgument(0));
        when(specialService.getMonthNumber("August")).thenReturn(8);

        Payroll expectedPayroll = new Payroll();
        expectedPayroll.setGrossSalary(32000);
        expectedPayroll.setTaxAmount(0);
        expectedPayroll.setNetSalary(32000);
        expectedPayroll.setPayMonth("August");
        expectedPayroll.setPayYear(2023);
        expectedPayroll.setPayDate(LocalDate.of(2023, 8, 27));

     
        when(payrollRepo.save(any(Payroll.class))).thenReturn(expectedPayroll);

        Payroll result = payrollService.addingPayroll(1, "2023-08-27", "August", 2023);

        Assertions.assertEquals(expectedPayroll, result);

        verify(specialService).findEmployeeById(1);
        verify(specialService).calculateTax(32000);
        verify(payrollRepo).save(any(Payroll.class));
        verify(specialService).getMonthNumber("August");
    }



    @Test
    public void testGetAllPayrolls() {
        List<Payroll> payrollList = new ArrayList<>();
        payrollList.add(payroll);

        when(payrollRepo.findAll()).thenReturn(payrollList);

        List<Payroll> result = payrollService.getAllPayrolls();

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(mockGrossSalary, result.get(0).getGrossSalary(), 0.01);

        verify(payrollRepo).findAll();
    }

    @Test
    public void testGetPayrollByEmployeeId() {
        List<Payroll> payrollList = new ArrayList<>();
        payrollList.add(payroll);

        when(payrollRepo.findByEmployeeId_EmployeeId(1)).thenReturn(payrollList);

        List<Payroll> result = payrollService.getPayrollByEmployeeId(1);

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(employee, result.get(0).getEmployeeId());

        verify(specialService).findEmployeeById(1);
        verify(payrollRepo).findByEmployeeId_EmployeeId(1);
    }

    @Test
    public void testGetPayrollByEmployeeId_ThrowsException() {
        when(payrollRepo.findByEmployeeId_EmployeeId(1)).thenReturn(new ArrayList<>());

        EmployeesException thrown = Assertions.assertThrows(EmployeesException.class, () -> {
            payrollService.getPayrollByEmployeeId(1);
        });

        Assertions.assertEquals("Employee payroll record not found for the EmployeeId : 1", thrown.getMessage());

        verify(specialService).findEmployeeById(1);
        verify(payrollRepo).findByEmployeeId_EmployeeId(1);
    }

    @Test
    public void testGetEmployeePayrollByMonthAndYear() {
        List<Payroll> payrollList = new ArrayList<>();
        payrollList.add(payroll);

        when(payrollRepo.findByEmployeeIdAndPayMonthAndPayYear(1, "August", 2023)).thenReturn(payrollList);

        List<Payroll> result = payrollService.getEmployeePayrollByMonthAndYear(1, "August", 2023);

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("August", result.get(0).getPayMonth());

        verify(specialService).findEmployeeById(1);
        verify(payrollRepo).findByEmployeeIdAndPayMonthAndPayYear(1, "August", 2023);
    }

    @Test
    public void testGetEmployeePayrollByYear() {
        List<Payroll> payrollList = new ArrayList<>();
        payrollList.add(payroll);

        when(payrollRepo.findByEmployeeIdAndPayYear(1, 2023)).thenReturn(payrollList);

        List<Payroll> result = payrollService.getEmployeePayrollByYear(1, 2023);

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(2023, result.get(0).getPayYear());

        verify(specialService).findEmployeeById(1);
        verify(payrollRepo).findByEmployeeIdAndPayYear(1, 2023);
    }

    @Test
    public void testGetPayrollsByMonthYearRange() {
        List<Payroll> payrollList = new ArrayList<>();
        payrollList.add(payroll);

        LocalDate startDate = LocalDate.of(2023, 8, 1);
        LocalDate endDate = LocalDate.of(2023, 8, 31);

        when(specialService.parseMonthYearToLocalDate("August 2023", true)).thenReturn(startDate);
        when(specialService.parseMonthYearToLocalDate("August 2023", false)).thenReturn(endDate);
        when(payrollRepo.findByEmployeeIdAndPayDateBetween(1, startDate, endDate)).thenReturn(payrollList);

        List<Payroll> result = payrollService.getPayrollsByMonthYearRange(1, "August 2023", "August 2023");

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("August", result.get(0).getPayMonth());

        verify(specialService).findEmployeeById(1);
        verify(specialService).parseMonthYearToLocalDate("August 2023", true);
        verify(specialService).parseMonthYearToLocalDate("August 2023", false);
        verify(payrollRepo).findByEmployeeIdAndPayDateBetween(1, startDate, endDate);
    }
}
