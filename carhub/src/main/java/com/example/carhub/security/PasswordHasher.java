package com.example.carhub.security;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordHasher {
    private final Argon2PasswordEncoder argon2Encoder;

    public PasswordHasher() {
        this.argon2Encoder = new Argon2PasswordEncoder(16, 32, 1, 2048, 8);
    }

    public String hashPassword(String rawPassword) {
        return argon2Encoder.encode(rawPassword);
    }

    //For further development in the context of an auth functionality
    public boolean matchesPassword(String rawPassword, String hashedPassword) {
        return argon2Encoder.matches(rawPassword, hashedPassword);
    }
} 
