package com.mylinks.repo;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mylinks.users.entity.Role;

import jakarta.transaction.Transactional;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByName(String name);
    
    @Modifying
    @Transactional
    @Query(value = """
        INSERT INTO role_permissions (role_id, permission_id)
        VALUES (:roleId, :permissionId)
        ON CONFLICT DO NOTHING
    """, nativeQuery = true)
    void assignPermission(@Param("roleId") UUID roleId,
                          @Param("permissionId") UUID permissionId);

    @Modifying
    @Transactional
    @Query(value = """
        DELETE FROM role_permissions
        WHERE role_id = :roleId AND permission_id = :permissionId
    """, nativeQuery = true)
    void removePermission(@Param("roleId") UUID roleId,
                          @Param("permissionId") UUID permissionId);
}