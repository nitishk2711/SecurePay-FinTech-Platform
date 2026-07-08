package com.securepay.auth_service.repository;

import com.securepay.auth_service.entity.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role, String> {
}
