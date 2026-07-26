package com.securepay.audit_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuditRequest {

    private String userId;

    private String action;

    private String service;

    private String status;
}
