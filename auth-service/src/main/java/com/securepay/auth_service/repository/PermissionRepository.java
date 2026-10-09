package com.securepay.auth_service.repository;

import com.securepay.auth_service.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PermissionRepository
        extends JpaRepository<Permission, UUID> {
}
