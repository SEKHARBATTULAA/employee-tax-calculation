package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    public int statusCode;
    public String message;

    public ErrorResponse(String message){
        super();
        this.message = message;
    }
}
