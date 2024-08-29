package com.emsb.controller;

import java.time.format.DateTimeParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.emsb.exception.EmployeesException;
import com.emsb.exception.ErrorResponse;
import com.emsb.model.FinancialSummaryResponse;
import com.emsb.service.FinancialReportService;

@RestController
@RequestMapping("/api/summary")
public class FinancialReportController {

    @Autowired
    private FinancialReportService financialReportService;
    
    
    @GetMapping("/all")
    public ResponseEntity<?> getAllSummary(){
    	FinancialSummaryResponse summary = financialReportService.getAllSummary();
    	return new ResponseEntity<>(summary, HttpStatus.OK);
    }

    @GetMapping("{month}/{year}")
    public ResponseEntity<?> summaryByMonthAndYear(@PathVariable String month, @PathVariable int year) {
        try {
        	FinancialSummaryResponse summary = financialReportService.getSummaryByMonthAndYear(month, year);
            return new ResponseEntity<>(summary, HttpStatus.OK);
        } catch (EmployeesException exception) {
            ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{year}")
    public ResponseEntity<?> summaryByYear(@PathVariable int year) {
        try {
        	FinancialSummaryResponse summary = financialReportService.getSummaryByYear(year);
            return new ResponseEntity<>(summary, HttpStatus.OK);
        } catch (EmployeesException exception) {
            ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
    }
    
    
    @GetMapping("/payPeriod")
    public ResponseEntity<?> summaryByMonthYearRange( @RequestParam String startMonthYear,
            @RequestParam String endMonthYear) {
    	try {
    		FinancialSummaryResponse summary = financialReportService.getSummaryByMonthYearRange(startMonthYear,endMonthYear);
    		return new ResponseEntity<>(summary, HttpStatus.OK);
    	} catch (EmployeesException exception) {
    		ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND.value());
    		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    	} catch (DateTimeParseException e) {
            ErrorResponse errorResponse = new ErrorResponse("Invalid date format. Please use MM-YYYY.", HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    	}
    }
    
    
    
}
