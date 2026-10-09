package com.securepay.auth_service.service;

import com.securepay.auth_service.dto.*;
import com.securepay.auth_service.entity.Role;

import java.util.UUID;

public interface AuthService {

    void register(UserRegisterRequest request);

    LoginResponse login(LoginRequest request);

    LoginResponse refresh(RefreshRequest request);

    void logout(String refreshToken);

    void changePassword(
            String email,
            ChangePasswordRequest request);

    void updateRole(UpdateRoleRequest request);

    void createRole(Role request);

    void verifyEmail(String token);

    void forgotPassword(String email);

    void resetPassword(String token, String newPassword);

    UUID getCurrentUserId(String email);
}
