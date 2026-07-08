package com.securepay.auth_service.service;

import com.securepay.auth_service.dto.JwtResponse;
import com.securepay.auth_service.dto.LoginRequest;
import com.securepay.auth_service.dto.UserRegisterRequest;
import com.securepay.auth_service.entity.Role;
import com.securepay.auth_service.entity.User;
import com.securepay.auth_service.repository.UserRepository;

import java.util.Optional;

public interface AuthService {
    User register(UserRegisterRequest request);

    JwtResponse login(LoginRequest request);

    Role createRoles(Role role);

    Role getRoleId(String id);

    Role updateRole(String id, Role role);

    String deleteRole(String id);

    User updateRoleId(String userId, String roleId);
}
