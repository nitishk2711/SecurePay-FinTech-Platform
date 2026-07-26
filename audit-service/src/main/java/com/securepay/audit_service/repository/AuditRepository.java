package com.securepay.audit_service.repository;

import com.securepay.audit_service.entity.AuditLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditRepository extends MongoRepository<AuditLog, String> {

    List<AuditLog> findByUserId(String userId);
}
