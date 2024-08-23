package com.emsb.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "payroll")
public class Payroll {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int payrollId;

	private double grossSalary;

	private double taxAmount;

	private double netSalary;

	private String payMonth;

	private int payYear;

	private Date payDate;

	@ManyToOne
	@JoinColumn(name = "employee_id")
	private Employees employeeId;

	public int getPayrollId() {
		return payrollId;
	}

	public void setPayrollId(int payrollId) {
		this.payrollId = payrollId;
	}

	public double getGrossSalary() {
		return grossSalary;
	}

	public void setGrossSalary(double grossSalary) {
		this.grossSalary = grossSalary;
	}

	public double getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(double taxAmount) {
		this.taxAmount = taxAmount;
	}

	public double getNetSalary() {
		return netSalary;
	}

	public void setNetSalary(double netSalary) {
		this.netSalary = netSalary;
	}

	public String getPayMonth() {
		return payMonth;
	}

	public void setPayMonth(String payMonth) {
		this.payMonth = payMonth;
	}

	public int getPayYear() {
		return payYear;
	}

	public void setPayYear(int payYear) {
		this.payYear = payYear;
	}

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	public Employees getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Employees employeeId) {
		this.employeeId = employeeId;
	}

	public Payroll(int payrollId, double grossSalary, double taxAmount, double netSalary, String payMonth, int payYear,
			Date payDate, Employees employeeId) {
		super();
		this.payrollId = payrollId;
		this.grossSalary = grossSalary;
		this.taxAmount = taxAmount;
		this.netSalary = netSalary;
		this.payMonth = payMonth;
		this.payYear = payYear;
		this.payDate = payDate;
		this.employeeId = employeeId;
	}

	public Payroll() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Payroll [payrollId=" + payrollId + ", grossSalary=" + grossSalary + ", taxAmount=" + taxAmount
				+ ", netSalary=" + netSalary + ", payMonth=" + payMonth + ", payYear=" + payYear + ", payDate="
				+ payDate + ", employeeId=" + employeeId + "]";
	}

}
