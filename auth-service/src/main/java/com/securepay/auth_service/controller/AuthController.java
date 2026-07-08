package com.securepay.auth_service.controller;

import com.securepay.auth_service.dto.ApiResponseDto;
import com.securepay.auth_service.dto.JwtResponse;
import com.securepay.auth_service.dto.LoginRequest;
import com.securepay.auth_service.dto.UserRegisterRequest;
import com.securepay.auth_service.entity.Role;
import com.securepay.auth_service.entity.User;
import com.securepay.auth_service.security.JwtUtil;
import com.securepay.auth_service.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<ApiResponseDto<User>> register(@RequestBody UserRegisterRequest request) {
        try {
            User user = authService.register(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDto<>(201, "User registered successfully", user));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto<>(400, e.getMessage(), null));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDto<JwtResponse>> login(@RequestBody LoginRequest request) {
        try {
            JwtResponse response = authService.login(request);
            return ResponseEntity.ok(new ApiResponseDto<>(200, "Login Successfull", response));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponseDto<>(401, e.getMessage(), null));
        }
    }

    @GetMapping("/dashboard")
    public ResponseEntity<ApiResponseDto<String>> dashboard(@RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.substring(7);

            String email = jwtUtil.extractUsername(token);

            return ResponseEntity.ok(new ApiResponseDto<>(200, "Success", "Welcome " + email));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponseDto<>(200, "Success", "Welcome to Dashboard"));
        }

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/createRole")
    public ResponseEntity<ApiResponseDto<Role>> createRoles(@RequestBody Role role) {

        try {

            Role createdRole = authService.createRoles(role);

            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDto<>(201, "Role created successfully", createdRole));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponseDto<>(500, e.getMessage(), null));
        }
    }

    @PreAuthorize("hasRole('ADMIN')")

    @GetMapping("/getRoleId/{id}")
    public ResponseEntity<ApiResponseDto<Role>> getRoleId(@PathVariable String id) {

        try {
            Role role = authService.getRoleId(id);
            return ResponseEntity.ok(new ApiResponseDto<>(200, "Role fetched successfully", role));


        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponseDto<>(500, e.getMessage(), null));
        }
    }

    @PreAuthorize("hasRole('ADMIN')")

    @PutMapping("/updateRole/{id}")
    public ResponseEntity<ApiResponseDto<Role>> updateRole(@RequestBody Role role, @PathVariable String id) {
        try {

            Role updateRole = authService.updateRole(id, role);

            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDto<>(201, "Role updated successfully", updateRole));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponseDto<>(500, e.getMessage(), null));
        }
    }

    @PreAuthorize("hasRole('ADMIN')")

    @DeleteMapping("/deleteRole/{id}")
    public ResponseEntity<ApiResponseDto<Role>> deleteRole(@PathVariable String id) {
        try {
            String message = authService.deleteRole(id);

            return ResponseEntity.ok(new ApiResponseDto<>(200, message, null));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponseDto<>(500, e.getMessage(), null));
        }
    }

    @PreAuthorize("hasRole('ADMIN')")

    @PutMapping("/updateRoleId/{userId}")
    public ResponseEntity<ApiResponseDto<User>> updateRoleId(@PathVariable String userId, @RequestParam String roleId) {
        try {
            User updateUser = authService.updateRoleId(userId, roleId);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDto<>(201, "User Role Id updated Successfully", updateUser));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponseDto<>(500, e.getMessage(), null));
        }
    }
}

