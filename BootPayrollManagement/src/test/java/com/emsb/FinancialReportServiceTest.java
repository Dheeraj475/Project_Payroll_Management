package com.emsb;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.emsb.entity.Payroll;
import com.emsb.model.FinancialReport;
import com.emsb.repository.PayrollRepository;
import com.emsb.service.FinancialReportService;

@SpringBootTest(classes = BootPayrollManagementApplication.class)
public class FinancialReportServiceTest {

    @Mock
    private PayrollRepository payrollRepo;

    @InjectMocks
    private FinancialReportService financialReportService;

    @Test
    public void testGenerateMonthlyIncomeStatement() {
        // Create a list of Payroll objects
        List<Payroll> payrollList = new ArrayList<>();
        
        Payroll payroll1 = new Payroll();
        payroll1.setGrossSalary(50000.0);
        payroll1.setNetSalary(40000.0);
        payroll1.setTaxAmount(10000.0);
        
        Payroll payroll2 = new Payroll();
        payroll2.setGrossSalary(60000.0);
        payroll2.setNetSalary(48000.0);
        payroll2.setTaxAmount(12000.0);
        
        payrollList.add(payroll1);
        payrollList.add(payroll2);

        // Mock repository to return payrolls for the month and year
        when(payrollRepo.findByPayMonthAndPayYear("August", 2023)).thenReturn(payrollList);

        // Call the service method
        FinancialReport result = financialReportService.generateMonthlyIncomeStatement("August", 2023);

        // Verify the results
        Assertions.assertNotNull(result);
        Assertions.assertEquals(110000.0, result.getTotalGrossSalary(), 0.01);
        Assertions.assertEquals(22000.0, result.getTotalTaxAmount(), 0.01);
        Assertions.assertEquals(88000.0, result.getTotalNetSalary(), 0.01);
    }

    @Test
    public void testGenerateYearlyIncomeStatement() {
        // Create a list of Payroll objects
        List<Payroll> payrollList = new ArrayList<>();
        
        Payroll payroll1 = new Payroll();
        payroll1.setGrossSalary(50000.0);
        payroll1.setNetSalary(40000.0);
        payroll1.setTaxAmount(10000.0);
        
        Payroll payroll2 = new Payroll();
        payroll2.setGrossSalary(60000.0);
        payroll2.setNetSalary(48000.0);
        payroll2.setTaxAmount(12000.0);
        
        payrollList.add(payroll1);
        payrollList.add(payroll2);

        // Mock repository to return payrolls for the year
        when(payrollRepo.findByPayYear(2023)).thenReturn(payrollList);

        // Call the service method
        FinancialReport result = financialReportService.generateYearlyIncomeStatement(2023);

        // Verify the results
        Assertions.assertNotNull(result);
        Assertions.assertEquals(110000.0, result.getTotalGrossSalary(), 0.01);
        Assertions.assertEquals(22000.0, result.getTotalTaxAmount(), 0.01);
        Assertions.assertEquals(88000.0, result.getTotalNetSalary(), 0.01);
    }

    @Test
    public void testGenerateMonthlyTaxSummary() {
        // Create a list of Payroll objects
        List<Payroll> payrollList = new ArrayList<>();
        
        Payroll payroll1 = new Payroll();
        payroll1.setTaxAmount(10000.0);
        
        Payroll payroll2 = new Payroll();
        payroll2.setTaxAmount(12000.0);
        
        payrollList.add(payroll1);
        payrollList.add(payroll2);

        // Mock repository to return payrolls for the month and year
        when(payrollRepo.findByPayMonthAndPayYear("August", 2023)).thenReturn(payrollList);

        // Call the service method
        double totalTax = financialReportService.generateMonthlyTaxSummary("August", 2023);

        // Verify the result
        Assertions.assertEquals(22000.0, totalTax, 0.01);
    }

    @Test
    public void testGenerateYearlyTaxSummary() {
        // Create a list of Payroll objects
        List<Payroll> payrollList = new ArrayList<>();
        
        Payroll payroll1 = new Payroll();
        payroll1.setTaxAmount(10000.0);
        
        Payroll payroll2 = new Payroll();
        payroll2.setTaxAmount(12000.0);
        
        payrollList.add(payroll1);
        payrollList.add(payroll2);

        // Mock repository to return payrolls for the year
        when(payrollRepo.findByPayYear(2023)).thenReturn(payrollList);

        // Call the service method
        double totalTax = financialReportService.generateYearlyTaxSummary(2023);

        // Verify the result
        Assertions.assertEquals(22000.0, totalTax, 0.01);
    }
}
