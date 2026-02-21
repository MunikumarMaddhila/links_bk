package com.mylinks.repo;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mylinks.users.entity.ProductVariant;


public interface ProductVariantRepository 
        extends JpaRepository<ProductVariant, UUID> {

    List<ProductVariant> findByStoreId(UUID storeId);

    Optional<ProductVariant> findByIdAndStoreId(UUID id, UUID storeId);

	long countByStoreId(UUID storeId);
}