```java
package com.securepay.auth_service.controller;

import com.securepay.auth_service.dto.ApiResponseDto;
import com.securepay.auth_service.dto.UpdateRoleRequest;
import com.securepay.auth_service.entity.Role;
import com.securepay.auth_service.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ApiResponseDto<Map<String, String>>> createRole(
            @Valid @RequestBody Role role) {
        try {
            authService.createRole(role);

            return ResponseEntity.ok(
                    new ApiResponseDto<>(
                            HttpStatus.OK.value(),
                            "Role created",
                            null
                    )
            );

        } catch (Exception e) {
            return ResponseEntity.ok(
                    new ApiResponseDto<>(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Failed to create role",
                            null
                    )
            );
        }
    }

    @PutMapping("/assign")
    public ResponseEntity<ApiResponseDto<Map<String, String>>> updateRole(
            @Valid @RequestBody UpdateRoleRequest request) {
        try {
            authService.updateRole(request);

            return ResponseEntity.ok(
                    new ApiResponseDto<>(
                            HttpStatus.OK.value(),
                            "Role assigned",
                            null
                    )
            );

        } catch (Exception e) {
            return ResponseEntity.ok(
                    new ApiResponseDto<>(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Failed to assign role",
                            null
                    )
            );
        }
    }
}
```
