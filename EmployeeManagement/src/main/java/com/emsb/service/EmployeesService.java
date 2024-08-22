package com.emsb.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emsb.entity.Employees;
import com.emsb.exception.EmployeesException;
import com.emsb.repository.EmployeesRepository;

@Service
public class EmployeesService {
	
	@Autowired
	private EmployeesRepository employeesrepo;
	
	/*Adding a employees*/
	public Employees addEmployee(Employees employee) throws EmployeesException{
		
		try {
			return employeesrepo.save(employee);
			
		} catch (Exception exception) {
			throw new EmployeesException("Failed to add employee!");
		}
		
	}
	
	
	/*All employee*/
	public List<Employees> findAllEmployee(){
		return employeesrepo.findAll();
		
	}
	
	/*Getting employee by id*/
	public Employees findEmployeeById(int id) throws EmployeesException {
		 return employeesrepo.findById(id).orElseThrow(() -> new EmployeesException(id + " : These id employee not found!"));
	}
	
	
	/*Updating a employee*/
	public Employees updateEmployee(int id, Employees employee) throws EmployeesException {
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
			throw new EmployeesException(id +" : This employee id not found");
		}
		
	}
	
	
	/*Deleting a employee*/
	public void deleteEmployee(int id) {
		employeesrepo.deleteById(id);
	}
	
	

}
