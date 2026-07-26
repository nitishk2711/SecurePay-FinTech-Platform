package com.securepay.audit_service.repository;

import com.securepay.audit_service.entity.ServiceEvent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceEventRepository extends MongoRepository<ServiceEvent, String> {

}
