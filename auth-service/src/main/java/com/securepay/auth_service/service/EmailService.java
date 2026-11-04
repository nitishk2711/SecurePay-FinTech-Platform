package com.securepay.auth_service.service;

public interface EmailService {
    void sendVerificationEmail(String email, String username, String token);

}
