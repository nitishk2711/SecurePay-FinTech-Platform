package com.securepay.auth_service.service;

import com.securepay.auth_service.dto.LoginRequest;
import com.securepay.auth_service.dto.LoginResponse;
import com.securepay.auth_service.dto.UpdateRoleRequest;
import com.securepay.auth_service.dto.UserRegisterRequest;
import com.securepay.auth_service.entity.Role;


public interface AuthService {

    void register(UserRegisterRequest request);

    LoginResponse login(LoginRequest request);

    void updateRole(UpdateRoleRequest request);

    void createRole(Role request);

}
