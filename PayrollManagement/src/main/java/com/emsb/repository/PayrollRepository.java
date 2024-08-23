package com.emsb.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emsb.entity.Payroll;

@Repository
public interface PayrollRepository extends JpaRepository<Payroll, Integer>{
	
   

}
