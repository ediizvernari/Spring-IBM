package com.example.carhub.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Builder
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class CarDto {
    private Long id;
    @NotBlank
    private String make;
    @NotBlank
    private String model;
    @NotNull
    private int year;
    @NotNull
    private double price;
}
