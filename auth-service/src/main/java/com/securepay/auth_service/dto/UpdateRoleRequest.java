package com.securepay.auth_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateRoleRequest {

    private Long userId;

    private Long roleId;
}