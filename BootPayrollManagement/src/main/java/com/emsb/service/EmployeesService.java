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
		salary = payrollService.formatToTwoDecimalPoints(salary);
		employee.setSalary(salary);
		return employeesrepo.save(employee);

	}

	/* All employee */
	public List<Employees> getAllEmployee() {
		return employeesrepo.findAll();

	}

	/* Getting employee by id */
	public Employees getEmployeeById(int employeeId) throws EmployeesException {
		return employeesrepo.findById(employeeId)
				.orElseThrow(() -> new EmployeesException("Employee record not found for the EmployeeId : "+employeeId));
	}

	/* Updating a employee */
	public Employees updateEmployee(int employeeId, Employees employee) throws EmployeesException {
		Optional<Employees> existingEmployee = employeesrepo.findById(employeeId);

		if (existingEmployee.isPresent()) {
			Employees updateEmployee = existingEmployee.get();

			updateEmployee.setName(employee.getName());
			updateEmployee.setAge(employee.getAge());
			updateEmployee.setGender(employee.getGender());
			updateEmployee.setDesignation(employee.getDesignation());
			updateEmployee.setRating(employee.getRating());

			double salary = payrollService.calculateSalary(updateEmployee);
			salary = payrollService.formatToTwoDecimalPoints(salary);
			updateEmployee.setSalary(salary);

			return employeesrepo.save(updateEmployee);

		} else {
			throw new EmployeesException("Employee record not found for updating this EmployeeId : "+employeeId);
		}

	}

	/* Deleting a employee */
	public void deleteEmployee(int employeeId) throws EmployeesException {
		if (!employeesrepo.existsById(employeeId)) {
			throw new EmployeesException("Employee record not found for deleting this EmployeeId : "+employeeId);
		}
		employeesrepo.deleteById(employeeId);
	}

}
