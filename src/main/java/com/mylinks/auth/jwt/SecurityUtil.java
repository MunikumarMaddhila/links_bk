package com.mylinks.auth.jwt;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.mylinks.repo.UserRepository;

@Component
public class SecurityUtil {

    @Autowired
    private UserRepository userRepository;

    public UUID getCurrentUserId(Authentication auth) {
        return userRepository.findByEmail(auth.getName())
                .orElseThrow()
                .getId();
    }
}