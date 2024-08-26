package com.emsb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.emsb.model.FinancialReport;
import com.emsb.service.FinancialReportService;

@RestController
@RequestMapping("/reports")
public class FinancialReportController {

    @Autowired
    private FinancialReportService financialReportService;

   
    @GetMapping("/income/monthly/{month}/{year}")
    public ResponseEntity<FinancialReport> getMonthlyIncomeStatement(@PathVariable String month, @PathVariable int year) {
        FinancialReport report = financialReportService.generateMonthlyIncomeStatement(month, year);
        return ResponseEntity.ok(report);
    }

   
    @GetMapping("/income/yearly/{year}")
    public ResponseEntity<FinancialReport> getYearlyIncomeStatement(@PathVariable int year) {
        FinancialReport report = financialReportService.generateYearlyIncomeStatement(year);
        return ResponseEntity.ok(report);
    }

   
    @GetMapping("/tax/monthly/{month}/{year}")
    public ResponseEntity<Double> getMonthlyTaxSummary(@PathVariable String month, @PathVariable int year) {
        double totalTax = financialReportService.generateMonthlyTaxSummary(month, year);
        return ResponseEntity.ok(totalTax);
    }


    @GetMapping("/tax/yearly/{year}")
    public ResponseEntity<Double> getYearlyTaxSummary(@PathVariable int year) {
        double totalTax = financialReportService.generateYearlyTaxSummary(year);
        return ResponseEntity.ok(totalTax);
    }
}
