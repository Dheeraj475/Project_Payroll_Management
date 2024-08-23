package com.emsb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.emsb.dto.EmployeeDTO;
import com.emsb.entity.Employees;
import com.emsb.exception.EmployeesException;
import com.emsb.exception.ErrorResponse;
import com.emsb.service.EmployeesService;

@RestController
@RequestMapping("/api")
public class EmployeesController {

    @Autowired
    private EmployeesService employeeService;
  

    /* POST Adding employee */
    @PostMapping("/add/employee")
    public ResponseEntity<?> addEmployee(@RequestBody EmployeeDTO employeeDTO) {
        try {
            Employees employee = new Employees();
            employee.setName(employeeDTO.getName());
            employee.setAge(employeeDTO.getAge());
            employee.setGender(employeeDTO.getGender());
            employee.setDesignation(employeeDTO.getDesignation());
            employee.setRating(employeeDTO.getRating());
            
            return new ResponseEntity<>(employeeService.addingEmployee(employee), HttpStatus.CREATED);
        } catch (EmployeesException exception) {
            ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    /* GET Getting all employees */
    @GetMapping("/all/employee")
    public ResponseEntity<List<Employees>> getAllEmployee() {
        return new ResponseEntity<>(employeeService.findAllEmployee(), HttpStatus.OK);
    }

    /* GET Getting employee with respective id */
    @GetMapping("/get/employee:{employeeId}")
    public ResponseEntity<?> getEmployeeById(@PathVariable int employeeId) {
        try {
            return new ResponseEntity<>(employeeService.findEmployeeById(employeeId), HttpStatus.OK);
        } catch (EmployeesException exception) {
            ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
    }

    /* PUT Updating an employee */
    @PutMapping("/update/employee:{employeeId}")
    public ResponseEntity<?> updateEmployee(@PathVariable int employeeId, @RequestBody EmployeeDTO employeeDTO) {
        try {
            Employees employee = new Employees();
            employee.setName(employeeDTO.getName());
            employee.setAge(employeeDTO.getAge());
            employee.setGender(employeeDTO.getGender());
            employee.setDesignation(employeeDTO.getDesignation());
            employee.setRating(employeeDTO.getRating());

            return new ResponseEntity<>(employeeService.updateEmployee(employeeId, employee), HttpStatus.OK);
        } catch (EmployeesException exception) {
            ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
    }

    /* DELETE Deleting an employee */
    @DeleteMapping("/delete/employee:{employeeId}")
    public ResponseEntity<?> deleteEmployee(@PathVariable int employeeId) {
        try {
            Employees employee = employeeService.findEmployeeById(employeeId);
            employeeService.deleteEmployee(employeeId);
            return new ResponseEntity<>(employee, HttpStatus.OK);
        } catch (EmployeesException exception) {
            ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
    }
}
