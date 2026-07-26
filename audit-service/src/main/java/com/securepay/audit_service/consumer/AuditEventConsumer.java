package com.securepay.audit_service.consumer;

import com.securepay.audit_service.dto.AuditRequest;
import com.securepay.audit_service.service.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuditEventConsumer {

    private final AuditService service;

    @KafkaListener(
            topics = "audit-events",
            groupId = "audit-group"
    )
    public void consume(String event) {
        AuditRequest request = new AuditRequest();
        request.setUserId("SYSTEM");
        request.setAction(event);
        request.setService("UNKNOWN");
        request.setStatus("SUCCESS");
        service.saveAudit(request);
    }
}
