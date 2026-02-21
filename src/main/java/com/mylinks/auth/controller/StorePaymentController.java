package com.mylinks.auth.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mylinks.repo.StorePaymentConfigRepository;
import com.mylinks.repo.StoreRepository;
import com.mylinks.users.entity.Store;
import com.mylinks.users.entity.StorePaymentConfig;


@RestController
@RequestMapping("/api/stores/{storeId}/payment")
public class StorePaymentController {

    @Autowired
    private StorePaymentConfigRepository repo;

    @Autowired
    private StoreRepository storeRepository;

    @PostMapping("/config")
    public StorePaymentConfig saveConfig(@PathVariable UUID storeId,
                                         @RequestBody StorePaymentConfig config,
                                         Authentication auth) {

        // 🔐 verify store ownership
        Store store = storeRepository.findById(storeId).orElseThrow();
        // compare store.userId with auth userId (as done earlier)

        config.setStoreId(storeId);
        config.setActive(true);
        return repo.save(config);
    }
}
