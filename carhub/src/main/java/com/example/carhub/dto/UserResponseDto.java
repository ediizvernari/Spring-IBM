package com.example.carhub.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
public class UserResponseDto {
    private Long id;
    
    @NotBlank
    private String username;
    
    @Email
    @NotBlank
    private String email;
}
