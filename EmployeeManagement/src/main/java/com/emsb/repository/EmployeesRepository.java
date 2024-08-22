package com.emsb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emsb.entity.Employees;


public interface EmployeesRepository extends JpaRepository<Employees, Integer>{

	List<Employees> findAll(List<Employees> employees);

	
}
