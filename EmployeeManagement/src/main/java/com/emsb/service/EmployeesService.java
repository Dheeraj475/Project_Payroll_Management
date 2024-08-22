package com.emsb.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emsb.entity.Employees;
import com.emsb.repository.EmployeesRepository;

@Service
public class EmployeesService {
	
	@Autowired
	private EmployeesRepository employeesrepo;
	
	/*Adding a employees*/
	public Employees addEmployee(Employees employee) {
		return employeesrepo.save(employee);
		
	}
	
	
	/*All employee*/
	public List<Employees> getAllEmployee(List<Employees> employees){
		return employeesrepo.findAll(employees);
		
	}
	
	/*Getting employee by id*/
	public Employees getEmployeeById(int id) {
		 return employeesrepo.findById(id).orElseThrow(() -> new RuntimeException(id + " : These id employee not found!"));
	}
	
	
	/*Updating a employee*/
	public Employees updateEmployee(int id, Employees employee) {
		Optional<Employees> existingEmployee = employeesrepo.findById(id);
		
		if(existingEmployee.isPresent()) {
			Employees updateEmployee = existingEmployee.get();
			
			updateEmployee.setName(employee.getName());
			updateEmployee.setAge(employee.getAge());
			updateEmployee.setGender(employee.getGender());
			updateEmployee.setDesignation(employee.getDesignation());
			updateEmployee.setRating(employee.getRating());
			
			return employeesrepo.save(updateEmployee);
			
		}else {
			throw new RuntimeException(id +" : This employee id not found");
		}
		
	}
	

	/*Deleting a employee*/
	
	
	

}
