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
@Document(collection = "service_events")
public class ServiceEvent {

    @Id
    private String id;

    private String serviceName;

    private String event;

    private String status;

    private String timestamp;
}
