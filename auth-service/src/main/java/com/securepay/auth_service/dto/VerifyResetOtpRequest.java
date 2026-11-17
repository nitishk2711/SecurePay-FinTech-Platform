package com.securepay.auth_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class VerifyResetOtpRequest {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "^[0-9]{6}$", message = "OTP must contain 6 digits")
    private String otp;
}