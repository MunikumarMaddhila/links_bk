package com.mylinks.repo;

import java.util.UUID;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mylinks.users.entity.Store;

import jakarta.transaction.Transactional;

@Repository
public interface StoreRepository extends JpaRepository<Store, UUID> {

	long countByUserId(UUID userId); // ✅ ADD THIS
	
    @Modifying
    @Transactional
    @Query("UPDATE Store s SET s.status = :status WHERE s.id = :id")
    void updateStatus(@Param("id") UUID id,
                      @Param("status") String status);
}