package com.securepay.audit_service.controller;

import com.securepay.audit_service.service.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/audit")
@RequiredArgsConstructor
public class AuditController {

    private final AuditService service;

    @GetMapping("/{userId}")
    public ResponseEntity<?> history(@PathVariable String userId) {
        return ResponseEntity.ok(service.getUserHistory(userId));
    }
}
