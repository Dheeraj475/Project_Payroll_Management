package com.emsb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emsb.entity.Employees;
import com.emsb.exception.EmployeesException;
import com.emsb.exception.ErrorResponse;
import com.emsb.service.EmployeesService;


@RestController
@RequestMapping("/api")
public class EmployeesController {
	
	@Autowired
	private EmployeesService employeeService;
	
	
	/*POST Adding employee*/
	@PostMapping("/employee")
	public ResponseEntity<?> addEmployee(@RequestBody Employees employee){
		try {
			return new ResponseEntity<>(employeeService.addingEmployee(employee), HttpStatus.CREATED);
		} catch (EmployeesException exception) {
			ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), HttpStatus.BAD_REQUEST.value());
			return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);			
		}
	}
	
	
	/* GET Getting all employee */
	@GetMapping("/all-employee")
	public ResponseEntity<List<Employees>> getAllEmployee(){
		return new ResponseEntity<>(employeeService.findAllEmployee(), HttpStatus.OK);
	}
	
	/*GET Getting employee with respective id*/
	@GetMapping("/employee/{id}")
	public ResponseEntity<?> getEmployeeById(@PathVariable int id){
		try {
			return new ResponseEntity<>(employeeService.findEmployeeById(id), HttpStatus.OK);
		} catch (EmployeesException exception) {
			ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND.value());
			return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
		}
	}		
	
	
	/*PUT Updating a employee*/
	@PutMapping("/update-employee/{id}")
	public ResponseEntity<?> updateEmployee(@PathVariable int id ,@RequestBody Employees employee){	
		try {	
			return new ResponseEntity<>(employeeService.updateEmployee(id, employee), HttpStatus.OK);
			
		} catch (EmployeesException exception) {
			ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND.value());
			return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
		}	
	}
	
	
	/*DELETE Deleting employee*/
	@DeleteMapping("/delete-employee/{id}")
	public ResponseEntity<?> deleteEmployee(@PathVariable int id){
		
		Employees employee = employeeService.findEmployeeById(id);
		
		if(employee == null) {
			ErrorResponse errorResponse = new ErrorResponse("Employee not found with id : "+id, HttpStatus.NOT_FOUND.value());
			return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);		
		}
		
		employeeService.deleteEmployee(id);
		return new ResponseEntity<>(employee, HttpStatus.OK);
		
	}
	
	
	
	

}
