package com.example.demo.service.impl;

import com.example.demo.entity.Employee;
import com.example.demo.model.TaxResponse;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.EmployeeTaxCalculatorService;
import org.apache.catalina.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class EmployeeTaxCalculatorServiceImpl implements EmployeeTaxCalculatorService {

    public static final int CESS_CAP_AMOUNT = 2500000;
    public static final double CESS_PCT = 0.02;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Override
    public TaxResponse calculateTax(String empID, String regime) {
        Optional<Employee> employeeOptional = employeeRepository.findById(empID);
        int noOfMonths = 0;
        double taxCalculatedAmount = 0.0;
        TaxResponse taxResponse = null;
        if(employeeOptional.isPresent()){
            Employee employee=employeeOptional.get();
            taxCalculatedAmount = taxCalculator(employee) + calculateCess(employee);
            System.out.printf("taxCalculatedAmount:"+taxCalculatedAmount);
            taxResponse= buildResponse(employeeOptional.get(),taxCalculator(employee), calculateCess(employee));
        }
        return taxResponse;
    }

    private TaxResponse buildResponse(Employee employee, double incomeTaxAmount, double cessTaxAmount) {
        return TaxResponse.builder()
                .employeeName(new StringBuilder()
                        .append(employee.getFirstName())
                        .append(", ")
                        .append(employee.getLastName()).toString())
                .taxAmount(BigDecimal.valueOf(incomeTaxAmount))
                .dateOfJoin(employee.getDateOfJoin())
                .salary(employee.getSalary().multiply( new BigDecimal("12")))
                .financialYear(getFinancialYear(LocalDateTime.now().getYear(), Math.addExact(LocalDateTime.now().getYear(),1)))
                .cessAmount(BigDecimal.valueOf(cessTaxAmount))
                .build();
    }

    public String getFinancialYear(int currentYear, int nextYear) {
        return new StringBuilder()
                .append(currentYear)
                .append("-")
                .append(nextYear)
                .toString();
    }


    private double calcAnnualIncome(Employee employee){
        long months = 0;
        LocalDateTime startDate = null;
        LocalDateTime endDate = null;
        if(!isDateInFirstQuarter()){
            startDate = LocalDateTime.of(LocalDate.now().getYear(),4,01,0,0);
            endDate = LocalDateTime.of(LocalDate.now().getYear()+1,3,31,0,0);
        }else{
            startDate = LocalDateTime.of(LocalDate.now().getYear()-1,4,01,0,0);
            endDate = LocalDateTime.of(LocalDate.now().getYear(),3,31,0,0);
        }
        if(employee.getDateOfJoin().isAfter(startDate)){
             months = ChronoUnit.MONTHS.between(employee.getDateOfJoin(),endDate)+1;
             if(employee.getDateOfJoin().getDayOfMonth()>15){
                 months--;
             }
            return employee.getSalary().doubleValue()*months;
        }else{
            return employee.getSalary().doubleValue()*12;
        }



    }

    public boolean isDateInFirstQuarter() {
        // Get the month from the date
        Month month = LocalDateTime.now().getMonth();
        // Months in the first quarter: January, February, March
        return month == Month.JANUARY || month == Month.FEBRUARY || month == Month.MARCH;
    }

    private double taxCalculator(Employee employee){
        double tax = 0.0;
        double income=calcAnnualIncome(employee);

        if (income <= 250000) {
            return tax;
        } else if (income <= 500000) {
            tax = (income - 250000) * 0.05;
        } else if (income <= 1000000) {
            tax = (250000 * 0.05) + (income - 500000) * 0.2;
        } else {
            tax = (250000 * 0.05) + (500000 * 0.2) + (income - 1000000) * 0.3;
        }
        return tax;
    }

    private double calculateCess(Employee employee) {
        double annualIncome = calcAnnualIncome(employee);
        double cessTaxAmount = 0.0;
        if(annualIncome > CESS_CAP_AMOUNT){
            double cessAmount = annualIncome - CESS_CAP_AMOUNT;
            cessTaxAmount= cessAmount * CESS_PCT;
        }
        return cessTaxAmount;
    }

}

