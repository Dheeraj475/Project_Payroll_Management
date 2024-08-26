package com.emsb.dto;

public class PayrollRequestDTO {

	private int employeeId;
	private String payMonth;
	private int payYear;
	private String payDate;

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
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

	public String getPayDate() {
		return payDate;
	}

	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}

	public PayrollRequestDTO(int employeeId, String payMonth, int payYear, String payDate) {
		super();
		this.employeeId = employeeId;
		this.payMonth = payMonth;
		this.payYear = payYear;
		this.payDate = payDate;
	}

	public PayrollRequestDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "PayrollRequestDTO [employeeId=" + employeeId + ", payMonth=" + payMonth + ", payYear=" + payYear
				+ ", payDate=" + payDate + "]";
	}

}
