package com.securepay.audit_service.service;

import com.securepay.audit_service.dto.AuditRequest;
import com.securepay.audit_service.entity.AuditLog;

import java.util.List;

public interface AuditService {

    void saveAudit(AuditRequest request);

    List<AuditLog> getUserHistory(String userId);
}
