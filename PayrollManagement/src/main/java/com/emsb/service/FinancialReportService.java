package com.emsb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emsb.entity.Payroll;
import com.emsb.model.FinancialReport;
import com.emsb.repository.PayrollRepository;

@Service
public class FinancialReportService {

    @Autowired
    private PayrollRepository payrollRepo;

    // Generate monthly income statement
    public FinancialReport generateMonthlyIncomeStatement(String month, int year) {
        List<Payroll> payrolls = payrollRepo.findByPayMonthAndPayYear(month, year);
        return generateIncomeStatement(payrolls);
    }

    // Generate yearly income statement
    public FinancialReport generateYearlyIncomeStatement(int year) {
        List<Payroll> payrolls = payrollRepo.findByPayYear(year);
        return generateIncomeStatement(payrolls);
    }

    // Generate monthly tax summary
    public double generateMonthlyTaxSummary(String month, int year) {
        List<Payroll> payrolls = payrollRepo.findByPayMonthAndPayYear(month, year);
        return payrolls.stream().mapToDouble(Payroll::getTaxAmount).sum();
    }

    // Generate yearly tax summary
    public double generateYearlyTaxSummary(int year) {
        List<Payroll> payrolls = payrollRepo.findByPayYear(year);
        return payrolls.stream().mapToDouble(Payroll::getTaxAmount).sum();
    }

    // Helper method to generate income statement
    private FinancialReport generateIncomeStatement(List<Payroll> payrolls) {
        double totalGrossSalary = payrolls.stream().mapToDouble(Payroll::getGrossSalary).sum();
        double totalNetSalary = payrolls.stream().mapToDouble(Payroll::getNetSalary).sum();
        double totalTaxAmount = payrolls.stream().mapToDouble(Payroll::getTaxAmount).sum();

        return new FinancialReport(totalGrossSalary, totalTaxAmount, totalNetSalary);
    }
}
