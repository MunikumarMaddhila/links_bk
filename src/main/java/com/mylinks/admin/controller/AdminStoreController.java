package com.mylinks.admin.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mylinks.auth.service.CustomUserDetails;
import com.mylinks.repo.StoreRepository;
import com.mylinks.users.entity.Store;

@RestController
@RequestMapping("/api/admin/store")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminStoreController {

    @Autowired
    private StoreRepository storeRepository;

    @GetMapping
    public Store myStore(Authentication auth) {
        UUID storeId = ((CustomUserDetails) auth.getPrincipal()).getStoreId();
        return storeRepository.findById(storeId).orElseThrow();
    }

    @PutMapping
    public Store update(@RequestBody Store store, Authentication auth) {
        UUID storeId = ((CustomUserDetails) auth.getPrincipal()).getStoreId();
        store.setId(storeId);
        return storeRepository.save(store);
    }
}
