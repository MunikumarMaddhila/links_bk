package com.mylinks.repo;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mylinks.users.entity.Refund;

public interface RefundRepository extends JpaRepository<Refund, UUID> {
    Optional<Refund> findByOrderId(UUID orderId);
}