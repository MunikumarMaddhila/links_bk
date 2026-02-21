package com.mylinks.auth.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mylinks.repo.PageRepository;
import com.mylinks.repo.StoreRepository;
import com.mylinks.users.entity.Plan;

@Service
public class SubscriptionValidationService {

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private PageRepository pageRepository;

    @Autowired
    private StoreRepository storeRepository;

    // 🔒 PAGE LIMIT CHECK
    public void validatePageLimit(UUID userId) {

        Plan plan = subscriptionService.getActivePlan(userId);
        long pageCount = pageRepository.countByUserId(userId);

        if (pageCount >= plan.getMaxPages()) {
            throw new RuntimeException("Page limit exceeded. Upgrade your plan.");
        }
    }

    // 🔒 STORE LIMIT CHECK
    public void validateStoreLimit(UUID userId) {

        Plan plan = subscriptionService.getActivePlan(userId);
        long storeCount = storeRepository.countByUserId(userId);

        if (storeCount >= plan.getMaxStores()) {
            throw new RuntimeException("Store limit exceeded. Upgrade your plan.");
        }
    }
}
