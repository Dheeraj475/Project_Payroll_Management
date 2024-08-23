package com.emsb.service;

public class FinancialReport {
    private double totalGrossSalary;
    private double totalTaxAmount;
    private double totalNetSalary;

    public FinancialReport(double totalGrossSalary, double totalTaxAmount, double totalNetSalary) {
        this.totalGrossSalary = totalGrossSalary;
        this.totalTaxAmount = totalTaxAmount;
        this.totalNetSalary = totalNetSalary;
    }

    // Getters and Setters

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

    @Override
    public String toString() {
        return "FinancialReport [Total Gross Salary=" + totalGrossSalary +
                ", Total Tax Amount=" + totalTaxAmount +
                ", Total Net Salary=" + totalNetSalary + "]";
    }
}
