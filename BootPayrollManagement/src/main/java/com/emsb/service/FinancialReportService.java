package com.emsb.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emsb.entity.Payroll;
import com.emsb.exception.EmployeesException;
import com.emsb.model.FinancialSummaryResponse;
import com.emsb.repository.PayrollRepository;

@Service
public class FinancialReportService {

	@Autowired
	private PayrollRepository payrollRepo;

	@Autowired
	private SpecialService specialService;

	public FinancialSummaryResponse getAllSummary() {
		List<Payroll> payrolls = payrollRepo.findAll();

		return specialService.calculateTotals(payrolls);
	}

	public FinancialSummaryResponse getSummaryByMonthAndYear(String month, int year) throws EmployeesException {
		List<Payroll> payrolls = payrollRepo.findByPayMonthAndPayYear(month, year);
		if (payrolls == null || payrolls.isEmpty()) {
			throw new EmployeesException("Record not found for the Month : " + month + " and Year : " + year);
		}

		return specialService.calculateTotals(payrolls);
	}

	public FinancialSummaryResponse getSummaryByYear(int year) throws EmployeesException {
		List<Payroll> payrolls = payrollRepo.findByPayYear(year);

		if (payrolls == null || payrolls.isEmpty()) {
			throw new EmployeesException("Record not found for the Year : " + year);
		}

		return specialService.calculateTotals(payrolls);
	}

	public FinancialSummaryResponse getSummaryByMonthYearRange(String startMonthYear, String endMonthYear) {

		LocalDate startDate = specialService.parseMonthYearToLocalDate(startMonthYear, true);
		LocalDate endDate = specialService.parseMonthYearToLocalDate(endMonthYear, false);

		List<Payroll> payrolls = payrollRepo.findByPayDateBetween(startDate, endDate);

		if (payrolls == null || payrolls.isEmpty()) {
			throw new EmployeesException("Records found for the date between : " + startMonthYear + " and " + endMonthYear);
		}

		return specialService.calculateTotals(payrolls);

	}

}
