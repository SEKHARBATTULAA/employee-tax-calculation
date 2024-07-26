package com.example.demo.service;

import com.example.demo.model.TaxResponse;

import java.math.BigDecimal;


public interface EmployeeTaxCalculatorService {

    TaxResponse calculateTax(String empID, String regime);
}
