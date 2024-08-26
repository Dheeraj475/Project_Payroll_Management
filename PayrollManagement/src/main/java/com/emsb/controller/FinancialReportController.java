package com.emsb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.emsb.service.FinancialReportService;

@RestController
@RequestMapping("/api")
public class FinancialReportController {

	@Autowired
	private FinancialReportService financialReportService;

	@GetMapping("/income/monthly/{month}/{year}")
	public ResponseEntity<?> getMonthlyIncomeStatement(@PathVariable String month, @PathVariable int year) {

		return new ResponseEntity<>(financialReportService.generateMonthlyIncomeStatement(month, year), HttpStatus.OK);

	}

	@GetMapping("/income/yearly/{year}")
	public ResponseEntity<?> getYearlyIncomeStatement(@PathVariable int year) {
		return new ResponseEntity<>(financialReportService.generateYearlyIncomeStatement(year), HttpStatus.OK);
	}

	@GetMapping("/tax/monthly/{month}/{year}")
	public ResponseEntity<?> getMonthlyTaxSummary(@PathVariable String month, @PathVariable int year) {
		return new ResponseEntity<>(financialReportService.generateMonthlyTaxSummary(month, year), HttpStatus.OK);
		
	}

	@GetMapping("/tax/yearly/{year}")
	public ResponseEntity<?> getYearlyTaxSummary(@PathVariable int year) {
		return new ResponseEntity<>(financialReportService.generateYearlyTaxSummary(year), HttpStatus.OK);
		
	}
}
