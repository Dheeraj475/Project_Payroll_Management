package com.emsb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emsb.entity.Employees;


public interface EmployeesRepository extends JpaRepository<Employees, Integer>{

	
}
