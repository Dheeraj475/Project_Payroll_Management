//package com.emsb;
//
//import static org.mockito.Mockito.when;
//import static org.mockito.Mockito.verify;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import com.emsb.entity.Employees;
//import com.emsb.exception.EmployeesException;
//import com.emsb.repository.EmployeesRepository;
//import com.emsb.service.EmployeesService;
//import com.emsb.service.PayrollService;
//
//@SpringBootTest(classes = BootPayrollManagementApplication.class)
//public class EmployeesServiceTest {
//
//    @Mock
//    private EmployeesRepository employeeRepo;
//
//    @Mock
//    private PayrollService payrollService;
//
//    @InjectMocks
//    private EmployeesService employeeService;
//
//    private Employees employee;
//    private double mockCalculatedSalary;
//
//    @BeforeEach
//    public void setup() {
//        employee = new Employees();
//        employee.setEmployeeId(1);
//        employee.setName("Ravi Badodi");
//        employee.setAge(23);
//        employee.setGender("Male");
//        employee.setDesignation("Software Developer");
//        employee.setRating(1);
//        
//        mockCalculatedSalary = 40000 * (employee.getRating() > 1 ? Math.pow(0.95, employee.getRating() - 1) : employee.getRating());
//
//        when(payrollService.calculateSalary(employee)).thenReturn(mockCalculatedSalary);
//        when(payrollService.formatToTwoDecimalPoints(mockCalculatedSalary)).thenReturn(mockCalculatedSalary);
//    }
//
//    @Test
//    public void testAddingEmployee() throws EmployeesException {
//        when(employeeRepo.save(Mockito.any(Employees.class))).thenReturn(employee);
//
//        Employees result = employeeService.addingEmployee(employee);
//
//        Assertions.assertNotNull(result);
//        Assertions.assertEquals(40000, result.getSalary(), 0.01);
//
//        verify(payrollService).calculateSalary(employee);
//        verify(employeeRepo).save(employee);
//    }
//
//    @Test
//    public void testFindAllEmployee() {
//        List<Employees> employeesList = new ArrayList<>();
//        employeesList.add(employee);
//
//        when(employeeRepo.findAll()).thenReturn(employeesList);
//
//        List<Employees> result = employeeService.findAllEmployee();
//
//        Assertions.assertNotNull(result);
//        Assertions.assertFalse(result.isEmpty());
//        Assertions.assertEquals(1, result.size());
//        Assertions.assertEquals("Ravi Badodi", result.get(0).getName());
//    }
//
//    @Test
//    public void testFindEmployeeById() throws EmployeesException {
//        when(employeeRepo.findById(1)).thenReturn(Optional.of(employee));
//
//        Employees result = employeeService.findEmployeeById(1);
//
//        Assertions.assertNotNull(result);
//        Assertions.assertEquals("Ravi Badodi", result.getName());
//        Assertions.assertEquals(1, result.getEmployeeId());
//    }
//
//    @Test
//    public void testFindEmployeeById_ThrowsException() {
//    	int id = 1;
//    	
//        when(employeeRepo.findById(id)).thenReturn(Optional.empty());
//
//        EmployeesException thrown = Assertions.assertThrows(EmployeesException.class, () -> {
//            employeeService.findEmployeeById(id);
//        });
//
//        Assertions.assertEquals(id+" : These id employee not found!", thrown.getMessage());
//    }
//
//    @Test
//    public void testUpdateEmployee() throws EmployeesException {
//        // Create an existing Employees object
//        Employees existingEmp = new Employees();
//        existingEmp.setEmployeeId(1);
//        existingEmp.setName("Ravi Badodi");
//        existingEmp.setAge(23);
//        existingEmp.setGender("Male");
//        existingEmp.setDesignation("Software Developer");
//        existingEmp.setRating(3);
//        existingEmp.setSalary(36100.0);
//
//        // Create an updated Employees object
//        Employees updatedEmp = new Employees();
//        updatedEmp.setName("Ravi Kumar");
//        updatedEmp.setAge(25);
//        updatedEmp.setGender("Male");
//        updatedEmp.setDesignation("Software Developer");
//        int rating = 2;
//        updatedEmp.setRating(rating);
//
//        double mockUpdatedSalary = 40000 * (rating>1 ? Math.pow(0.95, rating-1) : rating);
//
//        // Mock repository findById and save methods
//        when(employeeRepo.findById(1)).thenReturn(Optional.of(existingEmp));
//        when(employeeRepo.save(Mockito.any(Employees.class))).thenReturn(existingEmp);
//
//        // Mock payroll service calculation methods
//        when(payrollService.calculateSalary(existingEmp)).thenReturn(mockUpdatedSalary);
//        when(payrollService.formatToTwoDecimalPoints(mockUpdatedSalary)).thenReturn(mockUpdatedSalary);
//
//        // Call the service method
//        Employees result = employeeService.updateEmployee(1, updatedEmp);
//
//        // Verify the result
//        Assertions.assertNotNull(result);
//        Assertions.assertEquals("Ravi Kumar", result.getName());
//        Assertions.assertEquals(38000.0, result.getSalary(), 0.01);
//    }
//
//
//    @Test
//    public void testDeleteEmployee() throws EmployeesException {
//    	int empId = 1;
//    	
//        when(employeeRepo.existsById(empId)).thenReturn(true);
//
//        employeeService.deleteEmployee(6);
//
//        verify(employeeRepo).deleteById(empId);
//    }
//
//    @Test
//    public void testDeleteEmployee_ThrowsException() {
//    	int empId = 1;
//    	
//        when(employeeRepo.existsById(empId)).thenReturn(false);
//
//        EmployeesException thrown = Assertions.assertThrows(EmployeesException.class, () -> {
//            employeeService.deleteEmployee(7);
//        });
//
//        Assertions.assertEquals("1 : This employee ID not found!", thrown.getMessage());
//    }
//}
