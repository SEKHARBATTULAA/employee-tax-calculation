package com.example.demo.controller;

import com.example.demo.entity.Employee;
import com.example.demo.model.TaxResponse;
import com.example.demo.service.EmployeeTaxCalculatorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.mockito.Mockito.when;

@WebMvcTest(TaxCaluculatorController.class)
public class TaxCaluculatorControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private EmployeeTaxCalculatorService taxCalculatorService;

  //  calculateTax

    @Test
    public void shouldcalculateTax() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        TaxResponse taxResponse = TaxResponse.builder().
                taxAmount(BigDecimal.valueOf(688500))
                .salary(BigDecimal.valueOf(3504000))
                .dateOfJoin(LocalDateTime.now())
                .financialYear("2024-2025")
                .cessAmount(BigDecimal.valueOf(8400))
                .employeeName(" fi, la").build();
        when(taxCalculatorService.calculateTax("1234","new tax")).thenReturn(taxResponse);

        ResultActions response = mvc.perform( MockMvcRequestBuilders
                .get("/v1/taxcalculation?empID=v11")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(taxResponse)));


        response.andExpect(MockMvcResultMatchers.status().isOk());
    }
}
