package com.securepay.audit_service.service.serviceImpl;

import com.securepay.audit_service.dto.AuditRequest;
import com.securepay.audit_service.entity.AuditLog;
import com.securepay.audit_service.repository.AuditRepository;
import com.securepay.audit_service.service.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {

    private final AuditRepository repository;

    @Override
    public void saveAudit(AuditRequest request) {
        AuditLog log = new AuditLog();
        log.setUserId(request.getUserId());
        log.setAction(request.getAction());
        log.setService(request.getService());
        log.setStatus(request.getStatus());
        log.setTimestamp(LocalDateTime.now().toString());
        repository.save(log);
    }


    @Override
    public List<AuditLog> getUserHistory(String userId) {
        return repository.findByUserId(userId);
    }

}
