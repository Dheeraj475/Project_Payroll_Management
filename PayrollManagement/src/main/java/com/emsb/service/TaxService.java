package com.emsb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emsb.repository.PayrollRepository;

@Service
public class TaxService {
	
	@Autowired
	private PayrollRepository payrollRepo;
	
	public double calculateTax(double salary) {
	    if (salary <= 500000) {
	        return 0;
	    } else if (salary <= 700000) {
	        return salary * 0.10;
	    } else if (salary <= 1000000) {
	        return salary * 0.20;
	    } else {
	        return salary * 0.30;
	    }
	}

}
