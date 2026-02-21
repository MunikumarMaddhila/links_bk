package com.mylinks.admin.service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.mylinks.auth.service.AuditService;
import com.mylinks.auth.service.CustomUserDetails;
import com.mylinks.repo.UserRepository;
import com.mylinks.users.entity.User;

@Service
public class AdminUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuditService auditService;

    public List<User> listUsers(UUID storeId) {
        return userRepository.findByStoreId(storeId);
    }

    public User createUser(User user, UUID storeId) {
        user.setId(UUID.randomUUID());
        user.setStoreId(storeId);
        user.setActive(true);
//        user.setRole("USER");

        User saved = userRepository.save(user);

        auditService.log(
                null,
                "CREATE_USER",
                "USER",
                saved.getId(),
                null,
                saved.getEmail()
        );

        return saved;
    }

    public void suspendUser(UUID userId, Authentication auth) throws AccessDeniedException {
        User user = userRepository.findById(userId).orElseThrow();
        UUID storeId = ((CustomUserDetails) auth.getPrincipal()).getStoreId();

        if (!user.getStoreId().equals(storeId)) {
            throw new AccessDeniedException("Forbidden");
        }

        user.setActive(false);
        userRepository.save(user);

        auditService.log(
                ((CustomUserDetails) auth.getPrincipal()).getUserId(),
                "SUSPEND_USER",
                "USER",
                userId,
                "ACTIVE",
                "SUSPENDED"
        );
    }

    public void activateUser(UUID userId, Authentication auth) throws AccessDeniedException {
        User user = userRepository.findById(userId).orElseThrow();
        UUID storeId = ((CustomUserDetails) auth.getPrincipal()).getStoreId();

        if (!user.getStoreId().equals(storeId)) {
            throw new AccessDeniedException("Forbidden");
        }

        user.setActive(true);
        userRepository.save(user);

        auditService.log(
                ((CustomUserDetails) auth.getPrincipal()).getUserId(),
                "ACTIVATE_USER",
                "USER",
                userId,
                "SUSPENDED",
                "ACTIVE"
        );
    }
}
