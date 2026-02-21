package com.mylinks.admin.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mylinks.auth.service.CustomUserDetails;
import com.mylinks.repo.AuditRepository;
import com.mylinks.users.entity.AuditLog;

@RestController
@RequestMapping("/api/admin/audit")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminAuditController {

    @Autowired
    private AuditRepository auditRepository;

    @GetMapping
    public List<AuditLog> logs(Authentication auth) {
        UUID storeId = ((CustomUserDetails) auth.getPrincipal()).getStoreId();
        return auditRepository.findByEntityTypeAndEntityIdOrderByCreatedAtDesc(
                "STORE", storeId);
    }
}