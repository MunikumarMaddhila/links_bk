package com.mylinks.repo;

import java.util.Set;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mylinks.users.entity.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, UUID> {

    @Query(value = """
        SELECT DISTINCT p.code
        FROM permissions p
        JOIN role_permissions rp ON rp.permission_id = p.id
        JOIN user_roles ur ON ur.role_id = rp.role_id
        WHERE ur.user_id = :userId
    """, nativeQuery = true)
    Set<String> findPermissionsByUserId(@Param("userId") UUID userId);
}
