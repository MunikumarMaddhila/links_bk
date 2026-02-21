package com.mylinks.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mylinks.auth.dto.RegisterRequest;
import com.mylinks.repo.RoleRepository;
import com.mylinks.repo.UserRepository;
import com.mylinks.users.entity.Role;
import com.mylinks.users.entity.User;

import jakarta.transaction.Transactional;

@Service
public class SuperAdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void createAdmin(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        User admin = new User();
        admin.setEmail(request.getEmail());
        admin.setPassword(passwordEncoder.encode(request.getPassword()));
        admin.setStatus("ACTIVE");

        userRepository.save(admin);

        Role adminRole = roleRepository.findByName("ADMIN")
                .orElseThrow(() -> new RuntimeException("ADMIN role missing"));

        userRepository.assignRole(admin.getId(), adminRole.getId());
    }
}
