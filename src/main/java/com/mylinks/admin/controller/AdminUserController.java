package com.mylinks.admin.controller;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mylinks.admin.service.AdminUserService;
import com.mylinks.auth.service.CustomUserDetails;
import com.mylinks.users.entity.User;


@RestController
@RequestMapping("/api/admin/users")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminUserController {

    @Autowired
    private AdminUserService adminUserService;

    @GetMapping
    public List<User> list(Authentication auth) {
        UUID storeId = ((CustomUserDetails) auth.getPrincipal()).getStoreId();
        return adminUserService.listUsers(storeId);
    }

    @PostMapping
    public User create(@RequestBody User user, Authentication auth) {
        UUID storeId = ((CustomUserDetails) auth.getPrincipal()).getStoreId();
        return adminUserService.createUser(user, storeId);
    }

    @PutMapping("/{id}/suspend")
    public void suspend(@PathVariable UUID id, Authentication auth) {
        try {
			adminUserService.suspendUser(id, auth);
		} catch (AccessDeniedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @PutMapping("/{id}/activate")
    public void activate(@PathVariable UUID id, Authentication auth) {
        try {
			adminUserService.activateUser(id, auth);
		} catch (AccessDeniedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
