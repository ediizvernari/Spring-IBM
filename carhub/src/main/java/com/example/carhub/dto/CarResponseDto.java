package com.example.carhub.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
public class CarResponseDto {
    private Long id;
    private Long ownerId;

    @NotBlank
    private String make;
    
    @NotBlank
    private String model;
    
    @NotNull
    private int year;
    
    @NotNull
    private double price;
}
