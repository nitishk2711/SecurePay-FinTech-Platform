package com.securepay.auth_service.controller;

import com.securepay.auth_service.dto.*;
import com.securepay.auth_service.entity.Role;
import com.securepay.auth_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponseDto<Object>> register(@RequestBody UserRegisterRequest request) {
        authService.register(request);
        return ResponseEntity.ok(new ApiResponseDto<>(1, "User registered successfully", null));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDto<LoginResponse>> login(@RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(new ApiResponseDto<>(1, "Login successful", response));
    }

    @PostMapping("/role")
    public ResponseEntity<ApiResponseDto<Object>> updateRole(@RequestBody UpdateRoleRequest request) {

        authService.updateRole(request);
        return ResponseEntity.ok(new ApiResponseDto<>(1, "Role updated successfully", null));
    }

    @PostMapping("/roles")
    public ResponseEntity<ApiResponseDto<Object>> createRole(@RequestBody Role request) {
        try {
        authService.createRole(request);
        return ResponseEntity.ok(new ApiResponseDto<>(1, "Role created successfully", null));
    }catch(Exception e){
            e.printStackTrace(); return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR) .body(new ApiResponseDto<>( 500, e.getMessage(), null ));
        }
    }
}
