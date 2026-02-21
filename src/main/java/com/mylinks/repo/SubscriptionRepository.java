package com.mylinks.repo;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mylinks.users.entity.Subscription;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {

    @Query("""
        SELECT s FROM Subscription s
        WHERE s.userId = :userId
          AND s.status = 'ACTIVE'
          AND s.endDate >= CURRENT_DATE
    """)
    Optional<Subscription> findActiveSubscription(UUID userId);
}