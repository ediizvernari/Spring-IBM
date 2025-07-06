package com.example.carhub.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Builder
public class CarRequestDto {    
    @NotBlank
    private String make;
    
    @NotBlank
    private String model;
    
    @NotNull
    private int year;
    
    @NotNull
    private double price;
}
