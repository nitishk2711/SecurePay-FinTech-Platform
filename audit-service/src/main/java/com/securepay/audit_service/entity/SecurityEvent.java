package com.securepay.audit_service.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "security_events")
public class SecurityEvent {

    @Id
    private String id;

    private String userId;

    private String event;

    private String ipAddress;

    private String timestamp;
}
