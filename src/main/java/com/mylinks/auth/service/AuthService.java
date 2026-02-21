package com.mylinks.auth.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mylinks.auth.dto.AuthResponse;
import com.mylinks.auth.dto.RegisterRequest;
import com.mylinks.auth.jwt.JwtUtil;
import com.mylinks.repo.PageRepository;
import com.mylinks.repo.RoleRepository;
import com.mylinks.repo.UserRepository;
import com.mylinks.response.UserResponse;
import com.mylinks.users.entity.LoginRequest;
import com.mylinks.users.entity.Page;
import com.mylinks.users.entity.Role;
import com.mylinks.users.entity.User;

import jakarta.transaction.Transactional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PageRepository pageRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Transactional
    public AuthResponse register(RegisterRequest request) {

        // 1️⃣ Check user exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        // 2️⃣ Create user
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setStatus("ACTIVE");
        userRepository.save(user);

        // 3️⃣ Assign USER role
        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("USER role missing"));

        userRepository.assignRole(user.getId(), userRole.getId());
        // 👉 Native query (shown below)

        // 4️⃣ Create DEFAULT page
        Page page = new Page();
        page.setUserId(user.getId());
        page.setTitle("My Links");
        page.setSlug("user-" + user.getId());
        page.setDefault(true);
        pageRepository.save(page);

        // 5️⃣ Update default_page_id
        userRepository.updateDefaultPage(user.getId(), page.getId());

        // 6️⃣ Generate JWT
        String token = jwtUtil.generateToken(user.getEmail());

        UserResponse users = new UserResponse();
        users.setId(user.getId());
        users.setStoreId(user.getStoreId());
        users.setEmail(user.getEmail());
//        user.setPassword(token);
        users.setStatus(token);
        users.setProvider(token);
        users.setProviderId(token);
        users.setPhone(token);
        users.setName(token);
        users.setBio(token);
        users.setImage(token);
        
        return new AuthResponse(token,users);
    }
    
    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        if (!"ACTIVE".equals(user.getStatus())) {
            throw new RuntimeException("User is blocked");
        }

        String token = jwtUtil.generateToken(user.getEmail());
        
        UserResponse users = new UserResponse();
        users.setId(user.getId());
        users.setStoreId(user.getStoreId());
        users.setEmail(user.getEmail());
//        user.setPassword(token);
        users.setStatus(user.getStatus());
//        users.setPhone(token);
        users.setName(user.getName());
        users.setBio(user.getBio());
        users.setImage(user.getImage());
        users.setRole(user.getRole());
        return new AuthResponse(token,users);
    }
}
