package com.mylinks.repo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mylinks.users.entity.User;

public interface UserRepository extends JpaRepository<User, UUID> {
	
    Optional<User> findByEmail(String email);
    
    @Modifying
    @Query(value = "INSERT INTO user_roles(user_id, role_id) VALUES (:userId, :roleId)", nativeQuery = true)
    void assignRole(UUID userId, UUID roleId);

    @Modifying
    @Query(value = "UPDATE users SET default_page_id = :pageId WHERE id = :userId", nativeQuery = true)
    void updateDefaultPage(UUID userId, UUID pageId);

    @Modifying
    @Query("UPDATE User u SET u.status = :status WHERE u.id = :id")
    void updateStatus(@Param("id") UUID id, @Param("status") String status);
    
    List<User> findByStoreId(UUID storeId);

	long countByStoreId(UUID storeId);
}
