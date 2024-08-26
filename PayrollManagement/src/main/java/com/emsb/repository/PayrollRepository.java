package com.emsb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.emsb.entity.Payroll;

@Repository
public interface PayrollRepository extends JpaRepository<Payroll, Integer> {

	List<Payroll> findByEmployeeId_EmployeeId(int employeeId);

	@Query("SELECT p FROM Payroll p WHERE p.payMonth = :payMonth AND p.payYear = :payYear")
    List<Payroll> findByPayMonthAndPayYear(@Param("payMonth") String payMonth, @Param("payYear") int payYear);

    @Query("SELECT p FROM Payroll p WHERE p.payYear = :payYear")
    List<Payroll> findByPayYear(@Param("payYear") int payYear);

    @Query("SELECT p FROM Payroll p WHERE p.payMonth = :payMonth")
    List<Payroll> findByPayMonth(@Param("payMonth") String payMonth);
    
    @Query("SELECT p FROM Payroll p WHERE p.employeeId.employeeId = :employeeId AND p.payMonth = :payMonth AND p.payYear = :payYear")
    List<Payroll> findByEmployeeIdAndPayMonthAndPayYear(@Param("employeeId") int employeeId, @Param("payMonth") String payMonth, @Param("payYear") int payYear);


}
