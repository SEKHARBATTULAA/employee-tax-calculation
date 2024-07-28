package com.example.demo.entity;

import com.example.demo.validator.BeforeDate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
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
    @BeforeDate()
    private LocalDateTime dateOfJoin;

    @NonNull
    private BigDecimal salary;

}
