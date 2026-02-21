package com.mylinks.auth.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mylinks.repo.AuditRepository;
import com.mylinks.users.entity.AuditLog;

@Service
public class AuditService {

    @Autowired
    private AuditRepository auditRepository;

    public void log(
            UUID actorId,
            String action,
            String entityType,
            UUID entityId,
            String oldValue,
            String newValue
    ) {
        AuditLog log = new AuditLog(
                UUID.randomUUID(),
                actorId,
                action,
                entityType,
                entityId,
                oldValue,
                newValue,
                LocalDateTime.now()
        );

        auditRepository.save(log);
    }
}

