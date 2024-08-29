package com.emsb;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.emsb.entity.Employees;
import com.emsb.exception.EmployeesException;
import com.emsb.repository.EmployeesRepository;
import com.emsb.service.SpecialService;

@SpringBootTest
public class SpecialServiceTest {

    @Mock
    private EmployeesRepository employeesRepo;

    @InjectMocks
    private SpecialService specialService;

    private Employees employee;

    @BeforeEach
    public void setup() {
        employee = new Employees();
        employee.setEmployeeId(1);
        employee.setName("Ravi Badodi");
        employee.setAge(23);
        employee.setGender("Male");
        employee.setDesignation("Software Developer");
        employee.setRating(3);
        employee.setSalary(36100.0);

        when(employeesRepo.findById(1)).thenReturn(Optional.of(employee));
    }

    @Test
    public void testFindEmployeeById_Success() {
        Employees result = specialService.findEmployeeById(1);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(employee.getEmployeeId(), result.getEmployeeId());
        Assertions.assertEquals(employee.getName(), result.getName());
        verify(employeesRepo).findById(1);
    }

    @Test
    public void testFindEmployeeById_ThrowsException() {
        when(employeesRepo.findById(1)).thenReturn(Optional.empty());

        EmployeesException thrown = Assertions.assertThrows(EmployeesException.class, () -> {
            specialService.findEmployeeById(1);
        });

        Assertions.assertEquals("Employee id not found for the EmployeeId: 1", thrown.getMessage());
        verify(employeesRepo).findById(1);
    }

    @Test
    public void testFormatToTwoDecimalPoints() {
        double result = specialService.formatToTwoDecimalPoints(123.456789);

        Assertions.assertEquals(123.46, result, 0.01);
    }

    @Test
    public void testCalculateSalary() {
        double result = specialService.calculateSalary(employee);

        // Assuming the expected salary based on your logic in SpecialService
        double expectedSalary = 36100.0;
        Assertions.assertEquals(expectedSalary, result, 0.01);
    }

    @Test
    public void testCalculateTax() {
        double result = specialService.calculateTax(36100.0);

        // Assuming the expected tax amount based on your logic in SpecialService
        double expectedTax = 5000.0;
        Assertions.assertEquals(expectedTax, result, 0.01);
    }

    @Test
    public void testGetMonthNumber() {
        int result = specialService.getMonthNumber("August");

        Assertions.assertEquals(8, result);
    }

    @Test
    public void testParseMonthYearToLocalDate_Start() {
        LocalDate result = specialService.parseMonthYearToLocalDate("August 2023", true);

        Assertions.assertEquals(LocalDate.of(2023, 8, 1), result);
    }

    @Test
    public void testParseMonthYearToLocalDate_End() {
        LocalDate result = specialService.parseMonthYearToLocalDate("August 2023", false);

        Assertions.assertEquals(LocalDate.of(2023, 8, 31), result);
    }
}
