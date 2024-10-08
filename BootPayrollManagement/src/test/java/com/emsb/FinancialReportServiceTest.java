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

import com.emsb.entity.Payroll;
import com.emsb.exception.EmployeesException;
import com.emsb.model.FinancialSummaryResponse;
import com.emsb.repository.PayrollRepository;
import com.emsb.service.FinancialReportService;
import com.emsb.service.SpecialService;

@SpringBootTest(classes = BootPayrollManagementApplication.class)
public class FinancialReportServiceTest {

    @Mock
    private PayrollRepository payrollRepo;

    @Mock
    private SpecialService specialService;

    @InjectMocks
    private FinancialReportService financialReportService;

    @Test
    public void testGetAllSummary() {
      
        List<Payroll> payrollList = new ArrayList<>();
        Payroll payroll = createMockPayroll(50000.0, 10000.0, 40000.0);
        payrollList.add(payroll);
        
        when(payrollRepo.findAll()).thenReturn(payrollList);
        when(specialService.calculateTotals(payrollList)).thenReturn(createMockFinancialSummary(payrollList));
    
        FinancialSummaryResponse result = financialReportService.getAllSummary();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(50000.0, result.getTotalGrossSalary(), 0.01);
    }

    @Test
    public void testGetSummaryByMonthAndYear() {
        String month = "August";
        int year = 2023;

        List<Payroll> payrollList = new ArrayList<>();
        Payroll payroll = createMockPayroll(60000.0, 12000.0, 48000.0);
        payrollList.add(payroll);
        
        when(payrollRepo.findByPayMonthAndPayYear(month, year)).thenReturn(payrollList);
        when(specialService.calculateTotals(payrollList)).thenReturn(createMockFinancialSummary(payrollList));

        FinancialSummaryResponse result = financialReportService.getSummaryByMonthAndYear(month, year);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(60000.0, result.getTotalGrossSalary(), 0.01);
    }

    @Test
    public void testGetSummaryByMonthAndYear_RecordNotFound() {
        String month = "August";
        int year = 2023;
        
        when(payrollRepo.findByPayMonthAndPayYear(month, year)).thenReturn(new ArrayList<>());

        EmployeesException exception = Assertions.assertThrows(EmployeesException.class, () ->
            financialReportService.getSummaryByMonthAndYear(month, year)
        );

        Assertions.assertEquals("Record not found for the Month : " + month + " and Year : " + year, exception.getMessage());
    }

    @Test
    public void testGetSummaryByYear() {
        int year = 2023;

        List<Payroll> payrollList = new ArrayList<>();
        Payroll payroll = createMockPayroll(70000.0, 14000.0, 56000.0);
        payrollList.add(payroll);
        
        when(payrollRepo.findByPayYear(year)).thenReturn(payrollList);
        when(specialService.calculateTotals(payrollList)).thenReturn(createMockFinancialSummary(payrollList));

        FinancialSummaryResponse result = financialReportService.getSummaryByYear(year);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(70000.0, result.getTotalGrossSalary(), 0.01);
    }

    @Test
    public void testGetSummaryByYear_RecordNotFound() {
        int year = 2023;

        when(payrollRepo.findByPayYear(year)).thenReturn(new ArrayList<>());

        EmployeesException exception = Assertions.assertThrows(EmployeesException.class, () ->
            financialReportService.getSummaryByYear(year)
        );

        Assertions.assertEquals("Record not found for the Year : " + year, exception.getMessage());
    }

    @Test
    public void testGetSummaryByMonthYearRange() {
        String startMonthYear = "January 2023";
        String endMonthYear = "August 2023";
        
        List<Payroll> payrollList = new ArrayList<>();
        Payroll payroll = createMockPayroll(80000.0, 16000.0, 64000.0);
        payrollList.add(payroll);

        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 8, 31);

        when(specialService.parseMonthYearToLocalDate(startMonthYear, true)).thenReturn(startDate);
        when(specialService.parseMonthYearToLocalDate(endMonthYear, false)).thenReturn(endDate);
        when(payrollRepo.findByPayDateBetween(startDate, endDate)).thenReturn(payrollList);
        when(specialService.calculateTotals(payrollList)).thenReturn(createMockFinancialSummary(payrollList));

        FinancialSummaryResponse result = financialReportService.getSummaryByMonthYearRange(startMonthYear, endMonthYear);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(80000.0, result.getTotalGrossSalary(), 0.01);
    }

    @Test
    public void testGetSummaryByMonthYearRange_RecordNotFound() {
        String startMonthYear = "January 2023";
        String endMonthYear = "August 2023";

        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 8, 31);

        when(specialService.parseMonthYearToLocalDate(startMonthYear, true)).thenReturn(startDate);
        when(specialService.parseMonthYearToLocalDate(endMonthYear, false)).thenReturn(endDate);
        when(payrollRepo.findByPayDateBetween(startDate, endDate)).thenReturn(new ArrayList<>());

        EmployeesException exception = Assertions.assertThrows(EmployeesException.class, () ->
            financialReportService.getSummaryByMonthYearRange(startMonthYear, endMonthYear)
        );

        Assertions.assertEquals("Records found for the date between : " + startMonthYear + " and " + endMonthYear, exception.getMessage());
    }

    // Create mock data
    private Payroll createMockPayroll(double grossSalary, double taxAmount, double netSalary) {
        Payroll payroll = new Payroll();
        payroll.setGrossSalary(grossSalary);
        payroll.setTaxAmount(taxAmount);
        payroll.setNetSalary(netSalary);
        return payroll;
    }

    private FinancialSummaryResponse createMockFinancialSummary(List<Payroll> payrollList) {
        FinancialSummaryResponse response = new FinancialSummaryResponse();
        
     
        double totalGrossSalary = payrollList.stream().mapToDouble(payroll -> payroll.getGrossSalary()).sum();
		double totalTaxAmount = payrollList.stream().mapToDouble(payroll -> payroll.getTaxAmount()).sum();
		double totalNetSalary = payrollList.stream().mapToDouble(payroll -> payroll.getNetSalary()).sum();
        
        response.setTotalGrossSalary(totalGrossSalary);
        response.setTotalTaxAmount(totalTaxAmount);
        response.setTotalNetSalary(totalNetSalary);
        
        return response;
    }

}
