package com.securepay.audit_service.entity;


import lombok.*;

import org.springframework.data.annotation.Id;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "audit_logs")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuditLog {

    @Id
    private String id;

    private String userId;

    private String action;

    private String service;

    private String status;

    private String timestamp;

}
