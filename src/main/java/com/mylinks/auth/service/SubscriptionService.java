package com.mylinks.auth.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mylinks.repo.PlanRepository;
import com.mylinks.repo.SubscriptionRepository;
import com.mylinks.users.entity.Plan;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private PlanRepository planRepository;

    public Plan getActivePlan(UUID userId) {

        return subscriptionRepository.findActiveSubscription(userId)
                .map(sub -> planRepository.findById(sub.getPlanId()).orElseThrow())
                .orElseGet(this::getFreePlan);
    }

    private Plan getFreePlan() {
        Plan free = new Plan();
        free.setMaxPages(1);
        free.setMaxStores(0);
        return free;
    }
    
    
}
