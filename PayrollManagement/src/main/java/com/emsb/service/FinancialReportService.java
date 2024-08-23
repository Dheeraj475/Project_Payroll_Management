package com.emsb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emsb.repository.PayrollRepository;

@Service
public class FinancialReportService {
	
	@Autowired
	private PayrollRepository payrollRepo;
	
	

}
