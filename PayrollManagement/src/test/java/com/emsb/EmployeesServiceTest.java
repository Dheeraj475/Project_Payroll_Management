package com.emsb;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.emsb.entity.Employees;
import com.emsb.exception.EmployeesException;
import com.emsb.repository.EmployeesRepository;
import com.emsb.service.EmployeesService;
import com.emsb.service.PayrollService;

@SpringBootTest(classes = PayrollManagementApplication.class)
public class EmployeesServiceTest {

    @Mock
    private EmployeesRepository employeeRepo;

    @Mock
    private PayrollService payrollService;

    @InjectMocks
    private EmployeesService employeeService;

    @Test
    public void testAddingEmployee() throws EmployeesException {
        // Create an Employees object
        Employees emp = new Employees();
        emp.setName("Ravi Badodi");
        emp.setAge(23);
        emp.setGender("Male");
        emp.setDesignation("Software Developer");
        emp.setRating(3);

        double mockCalculatedSalary = 36100.0; // Mock calculated salary

        // Mock payroll service calculation methods
        when(payrollService.calculateSalary(emp)).thenReturn(mockCalculatedSalary);
        when(payrollService.formatToTwoDecimalPoints(mockCalculatedSalary)).thenReturn(mockCalculatedSalary);

        // Mock repository save method
        when(employeeRepo.save(Mockito.any(Employees.class))).thenReturn(emp);

        // Call the service method
        Employees result = employeeService.addingEmployee(emp);

        // Verify the result
        Assertions.assertNotNull(result);
        Assertions.assertEquals(mockCalculatedSalary, result.getSalary(), 0.01);

        // Verify the interactions
        verify(payrollService).calculateSalary(emp);
        verify(employeeRepo).save(emp);
    }

    @Test
    public void testFindAllEmployee() {
        // Create an Employees object
        Employees emp = new Employees();
        emp.setName("Ravi Badodi");
        emp.setAge(23);
        emp.setGender("Male");
        emp.setDesignation("Software Developer");
        emp.setSalary(36100.0);

        List<Employees> employeesList = new ArrayList<>();
        employeesList.add(emp);

        // Mock repository findAll method
        when(employeeRepo.findAll()).thenReturn(employeesList);

        // Call the service method
        List<Employees> result = employeeService.findAllEmployee();

        // Verify the result
        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("Ravi Badodi", result.get(0).getName());
    }

    @Test
    public void testFindEmployeeById() throws EmployeesException {
        // Create an Employees object
        Employees emp = new Employees();
        emp.setEmployeeId(1);
        emp.setName("Ravi Badodi");

        // Mock repository findById method
        when(employeeRepo.findById(1)).thenReturn(Optional.of(emp));

        // Call the service method
        Employees result = employeeService.findEmployeeById(1);

        // Verify the result
        Assertions.assertNotNull(result);
        Assertions.assertEquals("Ravi Badodi", result.getName());
        Assertions.assertEquals(1, result.getEmployeeId());
    }

    @Test
    public void testFindEmployeeById_ThrowsException() {
        // Mock repository findById method to return empty
        when(employeeRepo.findById(1)).thenReturn(Optional.empty());

        // Call the service method and expect an exception
        EmployeesException thrown = Assertions.assertThrows(EmployeesException.class, () -> {
            employeeService.findEmployeeById(1);
        });

        // Verify the exception message
        Assertions.assertEquals("1 : These id employee not found!", thrown.getMessage());
    }

    @Test
    public void testUpdateEmployee() throws EmployeesException {
        // Create an existing Employees object
        Employees existingEmp = new Employees();
        existingEmp.setEmployeeId(1);
        existingEmp.setName("Ravi Badodi");
        existingEmp.setAge(23);
        existingEmp.setGender("Male");
        existingEmp.setDesignation("Software Developer");
        existingEmp.setRating(3);
        existingEmp.setSalary(36100.0);

        // Create an updated Employees object
        Employees updatedEmp = new Employees();
        updatedEmp.setName("Ravi Kumar");
        updatedEmp.setAge(25);
        updatedEmp.setGender("Male");
        updatedEmp.setDesignation("Senior Developer");
        updatedEmp.setRating(4);

        double mockUpdatedSalary = 38000.0;

        // Mock repository findById and save methods
        when(employeeRepo.findById(1)).thenReturn(Optional.of(existingEmp));
        when(employeeRepo.save(Mockito.any(Employees.class))).thenReturn(existingEmp);

        // Mock payroll service calculation methods
        when(payrollService.calculateSalary(existingEmp)).thenReturn(mockUpdatedSalary);
        when(payrollService.formatToTwoDecimalPoints(mockUpdatedSalary)).thenReturn(mockUpdatedSalary);

        // Call the service method
        Employees result = employeeService.updateEmployee(1, updatedEmp);

        // Verify the result
        Assertions.assertNotNull(result);
        Assertions.assertEquals("Ravi Kumar", result.getName());
        Assertions.assertEquals(38000.0, result.getSalary(), 0.01);
    }

    @Test
    public void testDeleteEmployee() throws EmployeesException {
        // Mock repository existsById method
        when(employeeRepo.existsById(1)).thenReturn(true);

        // Call the service method
        employeeService.deleteEmployee(1);

        // Verify the interaction with the repository deleteById method
        verify(employeeRepo).deleteById(1);
    }

    @Test
    public void testDeleteEmployee_ThrowsException() {
        // Mock repository existsById method to return false
        when(employeeRepo.existsById(1)).thenReturn(false);

        // Call the service method and expect an exception
        EmployeesException thrown = Assertions.assertThrows(EmployeesException.class, () -> {
            employeeService.deleteEmployee(1);
        });

        // Verify the exception message
        Assertions.assertEquals("1 : This employee ID not found!", thrown.getMessage());
    }
}
