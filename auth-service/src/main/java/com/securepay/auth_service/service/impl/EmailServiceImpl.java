package com.securepay.auth_service.service.impl;

import com.securepay.auth_service.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl  implements EmailService {
    private final JavaMailSender mailSender;

    @Value("${securepay.email.from}")
    private String from;

    @Value("${securepay.email.verification-base-url}")
    private String verificationBaseUrl;

    @Value("${securepay.email.test-recipient}")
    private String testRecipient;

    @Override
    public void sendVerificationEmail(
            String email,
            String username,
            String token) {

        String verificationUrl = verificationBaseUrl
                + "/auth/verify-email?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(testRecipient);
        message.setSubject("Verify your SecurePay account");
        message.setText(
                "Hello " + username + ",\n\n"
                        + "Thank you for registering with SecurePay.\n\n"
                        + "Click the link below to verify the account:\n"
                        + verificationUrl + "\n\n"
                        + "This link expires in 24 hours.\n\n"
                        + "If you did not register, you can ignore this email."
        );

        mailSender.send(message);
    }
}
