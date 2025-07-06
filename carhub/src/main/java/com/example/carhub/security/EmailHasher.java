package com.example.carhub.security;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Component
public class EmailHasher {

    @Value("${security.email.hmac-secret}")
    private String secretKey;

    private Mac hmacSha256;

    @PostConstruct
    public void init() throws Exception {
        System.out.println("Loaded HMAC secret: " + secretKey);
        hmacSha256 = Mac.getInstance("HmacSHA256");
        SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
        hmacSha256.init(keySpec);
    }

    public String signEmail(String email) {
        byte[] hashedEmail = hmacSha256.doFinal(email.getBytes());
        return Base64.getUrlEncoder().withoutPadding().encodeToString(hashedEmail);
    }

    public boolean verifyEmail(String rawEmail, String hashedEmail) {
        String expectedSignedEmail = signEmail(rawEmail);
        return expectedSignedEmail.equals(hashedEmail);
    }
}