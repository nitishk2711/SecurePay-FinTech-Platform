package com.securepay.auth_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class UpdateRoleRequest {

    @NotNull
    private Long userId;

    @NotNull
    private Long roleId;
}