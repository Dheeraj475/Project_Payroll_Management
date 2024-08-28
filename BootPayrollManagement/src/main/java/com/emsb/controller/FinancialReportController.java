package com.emsb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.emsb.exception.EmployeesException;
import com.emsb.exception.ErrorResponse;
import com.emsb.model.PayrollSummaryResponse;
import com.emsb.service.FinancialReportService;

@RestController
@RequestMapping("/api/income")
public class FinancialReportController {

    @Autowired
    private FinancialReportService financialReportService;

    @GetMapping("{month}/{year}")
    public ResponseEntity<?> incomesByMonthAndYear(@PathVariable String month, @PathVariable int year) {
        try {
            PayrollSummaryResponse summary = financialReportService.getIncomesByMonthAndYear(month, year);
            return new ResponseEntity<>(summary, HttpStatus.OK);
        } catch (EmployeesException exception) {
            ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{year}")
    public ResponseEntity<?> incomessByYear(@PathVariable int year) {
        try {
            PayrollSummaryResponse summary = financialReportService.getIncomesByYear(year);
            return new ResponseEntity<>(summary, HttpStatus.OK);
        } catch (EmployeesException exception) {
            ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
    }
}
