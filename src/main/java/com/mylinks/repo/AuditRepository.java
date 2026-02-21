package com.mylinks.repo;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mylinks.users.entity.AuditLog;

@Repository
public interface AuditRepository extends JpaRepository<AuditLog, UUID> {

    // 🔍 Fetch audit logs for an entity
    List<AuditLog> findByEntityTypeAndEntityIdOrderByCreatedAtDesc(
            String entityType,
            UUID entityId
    );

    // 🔍 Fetch actions done by a user/admin
    List<AuditLog> findByActorIdOrderByCreatedAtDesc(UUID actorId);
}
