package com.mylinks.auth.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mylinks.repo.PlanRepository;
import com.mylinks.repo.SubscriptionRepository;
import com.mylinks.users.entity.Plan;
import com.mylinks.users.entity.Subscription;

@RestController
@RequestMapping("/api/superadmin/plans")
public class SuperAdminPlanController {

    @Autowired
    private PlanRepository planRepository;
    
    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @PreAuthorize("hasAuthority('FULL_ACCESS')")
    @PostMapping
    public Plan createPlan(@RequestBody Plan plan) {
        return planRepository.save(plan);
    }

    @PreAuthorize("hasAuthority('FULL_ACCESS')")
    @GetMapping
    public List<Plan> getAllPlans() {
        return planRepository.findAll();
    }
    
    @PreAuthorize("hasAuthority('FULL_ACCESS')")
    @PostMapping("/assign")
    public void assignPlanToUser(@RequestParam UUID userId,
                                 @RequestParam UUID planId,
                                 @RequestParam int days) {

        Subscription sub = new Subscription();
        sub.setUserId(userId);
        sub.setPlanId(planId);
        sub.setStartDate(LocalDate.now());
        sub.setEndDate(LocalDate.now().plusDays(days));
        sub.setStatus("ACTIVE");

        subscriptionRepository.save(sub);
    }
    
    
}

