package com.securepay.audit_service.repository;

import com.securepay.audit_service.entity.SecurityEvent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecurityEventRepository extends MongoRepository<SecurityEvent, String> {

}
