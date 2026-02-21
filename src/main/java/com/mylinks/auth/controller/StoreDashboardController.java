package com.mylinks.auth.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mylinks.auth.dto.StoreDashboardResponse;
import com.mylinks.auth.service.StoreDashboardService;
import com.mylinks.repo.StoreRepository;
import com.mylinks.repo.UserRepository;
import com.mylinks.users.entity.Store;

@RestController
@RequestMapping("/api/stores/{storeId}/dashboard")
public class StoreDashboardController {

    @Autowired
    private StoreDashboardService dashboardService;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public StoreDashboardResponse dashboard(@PathVariable UUID storeId,
                                            Authentication auth) {

        // 🔐 verify store ownership
        UUID userId = userRepository.findByEmail(auth.getName())
                .orElseThrow()
                .getId();

        Store store = storeRepository.findById(storeId).orElseThrow();

        if (!store.getOwnerId().equals(userId)) {
            throw new RuntimeException("Unauthorized access");
        }

        return dashboardService.getDashboard(storeId);
    }
}

