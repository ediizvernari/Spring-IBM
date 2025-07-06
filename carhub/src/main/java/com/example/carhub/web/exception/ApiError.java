package com.example.carhub.web.exception;

import lombok.*;
import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class ApiError {
    private Instant timestamp;
    private int status;
    private String error;
    private String path;
    private List<String> messages;
}