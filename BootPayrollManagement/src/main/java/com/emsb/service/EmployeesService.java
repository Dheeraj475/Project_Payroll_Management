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
	private EmployeesRepository employeesRepo;
	
	@Autowired
	private SpecialService specialService;
	

	/* Adding a employees */
	public Employees addingEmployee(Employees employee) throws EmployeesException {
		double salary = specialService.calculateSalary(employee);
		salary = specialService.formatToTwoDecimalPoints(salary);
		employee.setSalary(salary);
		return employeesRepo.save(employee);

	}

	/* All employee */
	public List<Employees> getAllEmployee() {
		return employeesRepo.findAll();

	}

	/* Getting employee by id */
	public Employees getEmployeeById(int employeeId) throws EmployeesException {
		return specialService.findEmployeeById(employeeId);
		
	}
	

	/* Updating a employee */
	public Employees updateEmployee(int employeeId, Employees employee) throws EmployeesException {
		Optional<Employees> existingEmployee = employeesRepo.findById(employeeId);

		if (existingEmployee.isPresent()) {
			Employees updateEmployee = existingEmployee.get();

			updateEmployee.setName(employee.getName());
			updateEmployee.setAge(employee.getAge());
			updateEmployee.setGender(employee.getGender());
			updateEmployee.setDesignation(employee.getDesignation());
			updateEmployee.setRating(employee.getRating());

			double salary = specialService.calculateSalary(updateEmployee);
			salary = specialService.formatToTwoDecimalPoints(salary);
			updateEmployee.setSalary(salary);

			return employeesRepo.save(updateEmployee);

		} else {
			throw new EmployeesException("Employee id not found for updating this EmployeeId : "+employeeId);
		}

	}

	/* Deleting a employee */
	
	public Optional<Employees> findByEmployeeId(int employeeId){
		return employeesRepo.findById(employeeId);
	}
	
	public void deleteEmployee(int employeeId) throws EmployeesException {
		
	if (!employeesRepo.existsById(employeeId)) {
			throw new EmployeesException("Employee id not found for deleting this EmployeeId : "+employeeId);
		}
		employeesRepo.deleteById(employeeId);
	}

}
