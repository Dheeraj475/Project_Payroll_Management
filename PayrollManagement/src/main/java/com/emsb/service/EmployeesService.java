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
	
	@Autowired
	private PayrollService payrollService;

	
	
	/* Adding a employees */
	public Employees addingEmployee(Employees employee) throws EmployeesException {
			double salary = payrollService.calculateSalary(employee);
			employee.setSalary(salary);
			return employeesrepo.save(employee);


	}

	/* All employee */
	public List<Employees> findAllEmployee() {
		return employeesrepo.findAll();

	}

	/* Getting employee by id */
	public Employees findEmployeeById(int id) throws EmployeesException {
		return employeesrepo.findById(id)
				.orElseThrow(() -> new EmployeesException(id + " : These id employee not found!"));
	}

	/* Updating a employee */
	public Employees updateEmployee(int id, Employees employee) throws EmployeesException {
		Optional<Employees> existingEmployee = employeesrepo.findById(id);

		if (employee.getSalary() != 0.0) {
			throw new EmployeesException(
					"Salary is computed by payroll processing and should not be set manually or should be set to zero.");
		}

		if (existingEmployee.isPresent()) {
			Employees updateEmployee = existingEmployee.get();

			updateEmployee.setName(employee.getName());
			updateEmployee.setAge(employee.getAge());
			updateEmployee.setGender(employee.getGender());
			updateEmployee.setDesignation(employee.getDesignation());
			updateEmployee.setRating(employee.getRating());

			return employeesrepo.save(updateEmployee);

		} else {
			throw new EmployeesException(id + " : This employee id not found");
		}

	}

	/* Deleting a employee */
	public void deleteEmployee(int id) throws EmployeesException {
		if (!employeesrepo.existsById(id)) {
			throw new EmployeesException(id + " : This employee ID not found!");
		}
		employeesrepo.deleteById(id);
	}

}
