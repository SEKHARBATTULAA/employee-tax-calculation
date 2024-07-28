package com.example.demo.controller;

import com.example.demo.model.TaxResponse;
import com.example.demo.service.EmployeeTaxCalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("v1/taxcalculation")
public class TaxCaluculatorController {
    @Autowired
    private EmployeeTaxCalculatorService taxCalculatorService;

    @GetMapping()
    public ResponseEntity<TaxResponse> calculateTax(@RequestParam(required = true) String empID, @RequestParam (required = false) String regime) {
        TaxResponse taxResponse = taxCalculatorService.calculateTax(empID,regime);
        return new ResponseEntity<>(taxResponse, HttpStatus.OK);
    }
}
