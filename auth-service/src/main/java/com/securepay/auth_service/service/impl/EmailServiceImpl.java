
package com.securepay.auth_service.service.impl;

import com.securepay.auth_service.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

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
        message.setTo(email);
        message.setSubject("Verify your SecurePay account");
        message.setText(
                "Hello " + username + ",\n\n"
                        + "Thank you for registering with SecurePay.\n\n"
                        + "Click the link below to verify your account:\n"
                        + verificationUrl + "\n\n"
                        + "This link expires in 24 hours."
        );

        mailSender.send(message);
    }

    @Override
    public void sendPasswordResetOtp(String email, String username, String otp) {

        try {
            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper =
                    new MimeMessageHelper(message, false, "UTF-8");

            helper.setFrom(from);
            helper.setTo(testRecipient);
            helper.setSubject("SecurePay Password Reset OTP");

            helper.setText(
                    "Hello " + username + ",\n\n" +
                            "Your password reset OTP is: " + otp + "\n\n" +
                            "This OTP expires in 5 minutes."
            );

            System.out.println("TO ADDRESS: " +
                    Arrays.toString(message.getAllRecipients()));

            mailSender.send(message);

            System.out.println("EMAIL SENT SUCCESSFULLY");

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Password reset email failed", e);
        }
    }
}
