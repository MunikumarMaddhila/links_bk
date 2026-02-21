package com.mylinks.repo;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mylinks.users.entity.StorePaymentConfig;

public interface StorePaymentConfigRepository
extends JpaRepository<StorePaymentConfig, UUID> {

Optional<StorePaymentConfig> findByStoreIdAndActiveTrue(UUID storeId);
}