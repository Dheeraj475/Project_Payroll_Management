package com.emsb;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.emsb.entity.Employees;
import com.emsb.exception.EmployeesException;
import com.emsb.repository.EmployeesRepository;
import com.emsb.service.EmployeesService;
import com.emsb.service.SpecialService;

@SpringBootTest
public class EmployeesServiceTest {

    @Mock
    private EmployeesRepository employeesRepo;

    @Mock
    private SpecialService specialService;

    @InjectMocks
    private EmployeesService employeesService;

    private Employees employee;
    private double mockCalculatedSalary;

    @BeforeEach
    public void setup() {
        employee = new Employees();
        employee.setEmployeeId(1);
        employee.setName("Ravi Badodi");
        employee.setAge(23);
        employee.setGender("Male");
        employee.setDesignation("Software Developer");
        employee.setRating(1);
        
        mockCalculatedSalary = 40000.0;

        when(specialService.calculateSalary(employee)).thenReturn(mockCalculatedSalary);
        when(specialService.formatToTwoDecimalPoints(mockCalculatedSalary)).thenReturn(mockCalculatedSalary);
    }

    @Test
    public void testAddingEmployee() throws EmployeesException {
        when(employeesRepo.save(Mockito.any(Employees.class))).thenReturn(employee);

        Employees result = employeesService.addingEmployee(employee);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(mockCalculatedSalary, result.getSalary(), 0.01);

        verify(specialService).calculateSalary(employee);
        verify(specialService).formatToTwoDecimalPoints(mockCalculatedSalary);
        verify(employeesRepo).save(employee);
    }

    @Test
    public void testGetAllEmployee() {
        List<Employees> employeesList = new ArrayList<>();
        employeesList.add(employee);

        when(employeesRepo.findAll()).thenReturn(employeesList);

        List<Employees> result = employeesService.getAllEmployee();

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("Ravi Badodi", result.get(0).getName());
    }

    @Test
    public void testGetEmployeeById() throws EmployeesException {
        when(specialService.findEmployeeById(1)).thenReturn(employee);

        Employees result = employeesService.getEmployeeById(1);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("Ravi Badodi", result.getName());
        Assertions.assertEquals(1, result.getEmployeeId());
    }

    @Test
    public void testGetEmployeeById_ThrowsException() {
        int id = 1;

        when(specialService.findEmployeeById(id)).thenThrow(new EmployeesException("Employee not found"));

        EmployeesException thrown = Assertions.assertThrows(EmployeesException.class, () -> {
            employeesService.getEmployeeById(id);
        });

        Assertions.assertEquals("Employee not found", thrown.getMessage());
    }

    @Test
    public void testUpdateEmployee() throws EmployeesException {
        Employees existingEmployee = new Employees();
        existingEmployee.setEmployeeId(1);
        existingEmployee.setName("Ravi Badodi");
        existingEmployee.setAge(23);
        existingEmployee.setGender("Male");
        existingEmployee.setDesignation("Software Developer");
        existingEmployee.setRating(3);
        existingEmployee.setSalary(36100.0);

        Employees updatedEmployee = new Employees();
        updatedEmployee.setName("Ravi Kumar");
        updatedEmployee.setAge(25);
        updatedEmployee.setGender("Male");
        updatedEmployee.setDesignation("Software Developer");
        updatedEmployee.setRating(2);

        double mockUpdatedSalary = 38000.0;

        when(employeesRepo.findById(1)).thenReturn(Optional.of(existingEmployee));
        when(employeesRepo.save(Mockito.any(Employees.class))).thenReturn(existingEmployee);
        when(specialService.calculateSalary(existingEmployee)).thenReturn(mockUpdatedSalary);
        when(specialService.formatToTwoDecimalPoints(mockUpdatedSalary)).thenReturn(mockUpdatedSalary);

        Employees result = employeesService.updateEmployee(1, updatedEmployee);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("Ravi Kumar", result.getName());
        Assertions.assertEquals(100, result.getSalary(), 0.01);

        verify(employeesRepo).findById(1);
        verify(employeesRepo).save(existingEmployee);
        verify(specialService).calculateSalary(existingEmployee);
        verify(specialService).formatToTwoDecimalPoints(mockUpdatedSalary);
    }

    @Test
    public void testDeleteEmployee() throws EmployeesException {
        int empId = 1;

        when(employeesRepo.existsById(empId)).thenReturn(true);

        employeesService.deleteEmployee(empId);

        verify(employeesRepo).deleteById(empId);
    }

    @Test
    public void testDeleteEmployee_ThrowsException() {
        int empId = 1;

        when(employeesRepo.existsById(empId)).thenReturn(false);

        EmployeesException thrown = Assertions.assertThrows(EmployeesException.class, () -> {
            employeesService.deleteEmployee(empId);
        });

        Assertions.assertEquals("Employee id not found for deleting this EmployeeId : " + empId, thrown.getMessage());
    }
}
