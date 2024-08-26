package com.emsb;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

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
    public void testFindAllEmployees() throws EmployeesException {
        // Create an Employees object
        Employees emp = new Employees();
        emp.setName("Ravi Badodi");
        emp.setAge(23);
        emp.setGender("Male");
        emp.setDesignation("Software Developer");
        emp.setRating(3);

        // Define the mock salary value
        double mockCalculatedSalary = 40000 * Math.pow(0.95, 2); // Example value, adjust as needed

        // Mock the calculateSalary method
        when(payrollService.calculateSalary(emp)).thenReturn(mockCalculatedSalary);
        // Mock the formatToTwoDecimalPoints method to return the formatted salary
        when(payrollService.formatToTwoDecimalPoints(mockCalculatedSalary)).thenReturn(mockCalculatedSalary);

        // Simulate the behavior of adding an employee
        // Mock the repository's save method to return the employee with calculated salary
        when(employeeRepo.save(Mockito.any(Employees.class))).thenAnswer(invocation -> {
            Employees savedEmployee = invocation.getArgument(0);
            savedEmployee.setSalary(mockCalculatedSalary);
            return savedEmployee;
        });
        
        // Add the employee to the service, which will use the mocked PayrollService to calculate salary
        employeeService.addingEmployee(emp);

        // Prepare the employee list
        List<Employees> employees = new ArrayList<>();
        emp.setSalary(mockCalculatedSalary); // Ensure the employee has the correct salary
        employees.add(emp);
        
        // Mock the repository's findAll method
        when(employeeRepo.findAll()).thenReturn(employees);
        
        // Call the service method to find all employees
        List<Employees> result = employeeService.findAllEmployee();
        
        // Verify the result
        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals("Ravi Badodi", result.get(0).getName());
        Assertions.assertEquals(36100.0, result.get(0).getSalary(), 0.01); // Use a delta for floating-point comparison
    }
}
