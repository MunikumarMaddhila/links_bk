package com.mylinks.repo;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mylinks.users.entity.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {

    // 🔍 Used during verification
    Optional<Payment> findByRazorpayOrderId(String razorpayOrderId);

    // 🔍 Used for refunds
    Optional<Payment> findByOrderId(UUID orderId);
}