package com.example.demo.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ToString
@Builder
public class TaxResponse {

    private String employeeName;
    private LocalDateTime dateOfJoin;
    private String financialYear;
    private BigDecimal salary;
    private BigDecimal taxAmount;
    private BigDecimal cessAmount;

}
