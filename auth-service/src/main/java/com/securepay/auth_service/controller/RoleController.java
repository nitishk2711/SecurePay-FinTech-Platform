package com.securepay.auth_service.controller;

import com.securepay.auth_service.dto.UpdateRoleRequest;
import com.securepay.auth_service.entity.Role;
import com.securepay.auth_service.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin/roles")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class RoleController {

    private final AuthService authService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, String> createRole(
            @RequestBody Role role) {

        authService.createRole(role);

        return Map.of("message", "Role created");
    }

    @PutMapping("/assign")
    public Map<String, String> updateRole(
            @Valid @RequestBody UpdateRoleRequest request) {

        authService.updateRole(request);

        return Map.of("message", "Role assigned");
    }
}
