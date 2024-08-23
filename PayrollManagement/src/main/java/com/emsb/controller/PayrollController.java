package com.emsb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.emsb.dto.PayrollRequestDTO;
import com.emsb.entity.Employees;
import com.emsb.entity.Payroll;
import com.emsb.exception.EmployeesException;
import com.emsb.exception.ErrorResponse;
import com.emsb.service.PayrollService;

@RestController
@RequestMapping("/api")
public class PayrollController {

	@Autowired
	private PayrollService payrollService;

	@PostMapping("/add/payroll")
	public ResponseEntity<?> processPayroll(@RequestBody PayrollRequestDTO payrollRequest) {
		
		try {
			
			Payroll payroll = payrollService.processingPayroll(payrollRequest.getEmployeeId(),payrollRequest.getPayDate(),payrollRequest.getPayMonth(), payrollRequest.getPayYear());
			return new ResponseEntity<>(payroll, HttpStatus.CREATED);
		
		} catch (EmployeesException exception) {
			
			ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND.value());
			return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);

		}
	}
	
	
	
    @GetMapping("/all/payroll")
    public ResponseEntity<List<Payroll>> getAllPayrolls() {
        return new ResponseEntity<>(payrollService.findAllPayrolls(), HttpStatus.OK);
    }

    /* GET Getting employee with respective id */
    @GetMapping("/get/payroll:{employeeId}")
    public ResponseEntity<?> getPayrollByEmployeeId(@PathVariable int employeeId) {
        try {
            return new ResponseEntity<>(payrollService.findPayrollByEmployeeId(employeeId), HttpStatus.OK);
        } catch (EmployeesException exception) {
            ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
    }
}