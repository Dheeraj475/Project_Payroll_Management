package com.emsb.service;

import org.springframework.stereotype.Service;



@Service
public class TaxService {
	
	public double calculateTax(double salary) {
        double tax = 0.0;

        /*1. No tax for income up to 500,000*/
        if (salary <= 500000) {
            tax = 0;
        } 
        /*2. 10% tax for income between 500,001 and 700,000*/
        else if (salary <= 700000) {
            tax = (salary - 500000) * 0.10;
        } 
        /*3. 20% tax for income between 700,001 and 1,000,000*/
        else if (salary <= 1000000) {
            tax = (200000 * 0.10) + ((salary - 700000) * 0.20);
        } 
        /*4. 30% tax for income above 1,000,000*/
        else {
            tax = (200000 * 0.10) + (300000 * 0.20) + ((salary - 1000000) * 0.30);
        }
        
        return tax;
    }

}
