package com.emsb.controller;

import java.time.format.DateTimeParseException;
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
@RequestMapping("/api/payroll")
public class PayrollController {

	@Autowired
	private PayrollService payrollService;

	@PostMapping("/add")
	public ResponseEntity<?> addPayroll(@RequestBody PayrollRequestDTO payrollRequest) {
		
		try {
			
			Payroll payroll = payrollService.addingPayroll(payrollRequest.getEmployeeId(),payrollRequest.getPayDate(),payrollRequest.getPayMonth(), payrollRequest.getPayYear());
			return new ResponseEntity<>(payroll, HttpStatus.CREATED);
		
		} catch (EmployeesException exception) {
			
			ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND.value());
			return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);

		}
	}
	
	
    @GetMapping("/all")
    public ResponseEntity<List<Payroll>> allPayrolls() {
        return new ResponseEntity<>(payrollService.getAllPayrolls(), HttpStatus.OK);
    }

    /* GET Getting employee with respective id */
    @GetMapping("/employeeId:{employeeId}")
    public ResponseEntity<?> payrollByEmployeeId(@PathVariable int employeeId) {
        try {
            return new ResponseEntity<>(payrollService.getPayrollByEmployeeId(employeeId), HttpStatus.OK);
        } catch (EmployeesException exception) {
            ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/employeeId:{employeeId}/{month}/{year}")
    public ResponseEntity<?> payrollByEmployeeMonthAndYear(@PathVariable int employeeId, @PathVariable String month, @PathVariable int year) {
        try {
            List<Payroll> payroll = payrollService.getEmployeePayrollByMonthAndYear(employeeId, month, year);
            return new ResponseEntity<>(payroll, HttpStatus.OK);
        } catch (EmployeesException exception) {
            ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/employeeId:{employeeId}/{year}")
    public ResponseEntity<?> payrollByEmployeeYear(@PathVariable int employeeId, @PathVariable int year) {
        try {
            List<Payroll> payroll = payrollService.getEmployeePayrollByYear(employeeId, year);
            return new ResponseEntity<>(payroll, HttpStatus.OK);
        } catch (EmployeesException exception) {
            ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
    }

    
    @GetMapping("/employee/payPeriod")
    public ResponseEntity<?> payrollByEmployeeMonthYearRange(
            @RequestParam int employeeId,
            @RequestParam String startMonthYear,
            @RequestParam String endMonthYear) {
        try {
            List<Payroll> payrolls = payrollService.getPayrollsByMonthYearRange(employeeId, startMonthYear, endMonthYear);
            return new ResponseEntity<>(payrolls, HttpStatus.OK);
        } catch (EmployeesException exception) {
            ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (DateTimeParseException e) {
            ErrorResponse errorResponse = new ErrorResponse("Invalid date format. Please use MM-YYYY.", HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }


    
    
}