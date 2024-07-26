package com.example.demo.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Document(collection = "Employee")
public class Employee {

    @Id
    @NonNull
    private String employeeID;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    @Email
    private String email;

    @NonNull
    List<String> phoneNumbers;

    @NonNull
    @PastOrPresent
    private LocalDateTime dateOfJoin;

    @NonNull
    private BigDecimal salary;

}
