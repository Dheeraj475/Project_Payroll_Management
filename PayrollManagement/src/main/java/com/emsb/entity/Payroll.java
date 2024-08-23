package com.emsb.entity;

import java.time.LocalDate;

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
	private int id;

	private double grossSalary;

	private double tax;

	private double netSalary;

	private String month;

	private int year;

	private LocalDate payDate;

	@ManyToOne
	@JoinColumn(name = "employee_id")
	private Employees employeeId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getGrossSalary() {
		return grossSalary;
	}

	public void setGrossSalary(double grossSalary) {
		this.grossSalary = grossSalary;
	}

	public double getTax() {
		return tax;
	}

	public void setTax(double tax) {
		this.tax = tax;
	}

	public double getNetSalary() {
		return netSalary;
	}

	public void setNetSalary(double netSalary) {
		this.netSalary = netSalary;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public LocalDate getPayDate() {
		return payDate;
	}

	public void setPayDate(LocalDate payDate) {
		this.payDate = payDate;
	}

	public Employees getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Employees employeeId) {
		this.employeeId = employeeId;
	}

	public Payroll(int id, double grossSalary, double tax, double netSalary, String month, int year, LocalDate payDate,
			Employees employeeId) {
		super();
		this.id = id;
		this.grossSalary = grossSalary;
		this.tax = tax;
		this.netSalary = netSalary;
		this.month = month;
		this.year = year;
		this.payDate = payDate;
		this.employeeId = employeeId;
	}

	public Payroll() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Payroll [id=" + id + ", grossSalary=" + grossSalary + ", tax=" + tax + ", netSalary=" + netSalary
				+ ", month=" + month + ", year=" + year + ", payDate=" + payDate + ", employeeId=" + employeeId + "]";
	}

}
