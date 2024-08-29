package com.emsb.model;

import java.util.List;
import com.emsb.entity.Payroll;

public class FinancialSummaryResponse {

	private List<Payroll> payrolls;

	private double totalGrossSalary;

	private double totalTaxAmount;

	private double totalNetSalary;

	public List<Payroll> getPayrolls() {
		return payrolls;
	}

	public void setPayrolls(List<Payroll> payrolls) {
		this.payrolls = payrolls;
	}

	public double getTotalGrossSalary() {
		return totalGrossSalary;
	}

	public void setTotalGrossSalary(double totalGrossSalary) {
		this.totalGrossSalary = totalGrossSalary;
	}

	public double getTotalTaxAmount() {
		return totalTaxAmount;
	}

	public void setTotalTaxAmount(double totalTaxAmount) {
		this.totalTaxAmount = totalTaxAmount;
	}

	public double getTotalNetSalary() {
		return totalNetSalary;
	}

	public void setTotalNetSalary(double totalNetSalary) {
		this.totalNetSalary = totalNetSalary;
	}

	public FinancialSummaryResponse(List<Payroll> payrolls, double totalGrossSalary, double totalTaxAmount,
			double totalNetSalary) {
		super();
		this.payrolls = payrolls;
		this.totalGrossSalary = totalGrossSalary;
		this.totalTaxAmount = totalTaxAmount;
		this.totalNetSalary = totalNetSalary;
	}

}
