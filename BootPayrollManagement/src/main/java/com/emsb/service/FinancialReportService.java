package com.emsb.service;

import java.text.DecimalFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emsb.entity.Payroll;
import com.emsb.exception.EmployeesException;
import com.emsb.model.PayrollSummaryResponse;
import com.emsb.repository.PayrollRepository;

@Service
public class FinancialReportService {

    @Autowired
    private PayrollRepository payrollRepo;
    
   
	

    public PayrollSummaryResponse getIncomesByMonthAndYear(String month, int year) throws EmployeesException {
        List<Payroll> payrolls = payrollRepo.findByPayMonthAndPayYear(month, year);
        if (payrolls == null || payrolls.isEmpty()) {
            throw new EmployeesException("Income record not found for the Month : " + month + " and Year : " + year);
        }

        double totalGrossSalary = payrolls.stream().mapToDouble(Payroll::getGrossSalary).sum();
        double totalTaxAmount = payrolls.stream().mapToDouble(Payroll::getTaxAmount).sum();
        double totalNetSalary = payrolls.stream().mapToDouble(Payroll::getNetSalary).sum();
        
        totalGrossSalary = formatToTwoDecimalPoints(totalGrossSalary);
        totalTaxAmount = formatToTwoDecimalPoints(totalTaxAmount);
        totalNetSalary = formatToTwoDecimalPoints(totalNetSalary);

        return new PayrollSummaryResponse(payrolls, totalGrossSalary, totalTaxAmount, totalNetSalary);
    }

    public PayrollSummaryResponse getIncomesByYear(int year) throws EmployeesException {
        List<Payroll> payrolls = payrollRepo.findByPayYear(year);
        if (payrolls == null || payrolls.isEmpty()) {
            throw new EmployeesException("Income record not found for the Year : " + year);
        }

        double totalGrossSalary = payrolls.stream().mapToDouble(Payroll::getGrossSalary).sum();
        double totalTaxAmount = payrolls.stream().mapToDouble(Payroll::getTaxAmount).sum();
        double totalNetSalary = payrolls.stream().mapToDouble(Payroll::getNetSalary).sum();

        totalGrossSalary = formatToTwoDecimalPoints(totalGrossSalary);
        totalTaxAmount = formatToTwoDecimalPoints(totalTaxAmount);
        totalNetSalary = formatToTwoDecimalPoints(totalNetSalary);
        
        return new PayrollSummaryResponse(payrolls, totalGrossSalary, totalTaxAmount, totalNetSalary);
    }
    
    public double formatToTwoDecimalPoints(double value) {
		DecimalFormat df = new DecimalFormat("#.##");
		return Double.parseDouble(df.format(value));
	}
    
}
