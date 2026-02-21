package com.mylinks.auth.service;


import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mylinks.repo.StoreRepository;
import com.mylinks.repo.UserRepository;
import com.mylinks.users.entity.Store;

@Service
public class StoreService {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private NotificationService notificationService;
    
    @Autowired
    private AuditService auditService;

    @Autowired
    private SubscriptionValidationService validationService;

    public Store createStore(Store store, UUID userId) {

        store.setOwnerId(userId);
        store.setApprovalStatus("PENDING");
        store.setIsActive(false);

        storeRepository.save(store);

        // 🔔 Notify admin
        notificationService.notifyAdmins(
            "NEW_STORE_PENDING",
            Map.of("storeId", store.getId())
        );

        return store;
    }
    
    public void approveStore(UUID storeId, UUID adminId) {

        Store store = storeRepository.findById(storeId).orElseThrow();

        store.setApprovalStatus("APPROVED");
        store.setApprovedBy(adminId);
        store.setApprovedAt(LocalDateTime.now());
        store.setIsActive(true);

        storeRepository.save(store);

        auditService.log(
            adminId,
            "STORE_APPROVED",
            "STORE",
            storeId,
            "PENDING",
            "APPROVED"
        );
    }
    
    public void rejectStore(UUID storeId, String reason, UUID adminId) {

        Store store = storeRepository.findById(storeId).orElseThrow();

        store.setApprovalStatus("REJECTED");
        store.setRejectionReason(reason);
        store.setIsActive(false);

        storeRepository.save(store);

        auditService.log(
            adminId,
            "STORE_REJECTED",
            "STORE",
            storeId,
            "PENDING",
            "REJECTED"
        );
    }

}
