package com.mylinks.repo;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mylinks.users.entity.Page;

@Repository
public interface PageRepository extends JpaRepository<Page, UUID> {
    long countByUserId(UUID userId);
    
    List<Page> findByUserId(UUID userId);

	List<Page> findByStoreId(UUID storeId);

}