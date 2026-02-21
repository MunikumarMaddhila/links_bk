package com.mylinks.auth.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.mylinks.repo.PageRepository;
import com.mylinks.repo.RoleRepository;
import com.mylinks.repo.UserRepository;
import com.mylinks.users.entity.Page;
import com.mylinks.users.entity.Role;
import com.mylinks.users.entity.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PageRepository pageRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException {

        OAuth2User oAuthUser = (OAuth2User) authentication.getPrincipal();

        String provider =
                authentication.getAuthorities().toString().contains("FACEBOOK")
                        ? "FACEBOOK"
                        : "GOOGLE";

        String providerId = oAuthUser.getAttribute("id");
        String email = oAuthUser.getAttribute("email");

        // ⚠️ Facebook may not return email
        if (email == null) {
            email = provider + "_" + providerId + "@oauth.local";
        }

        User user = userRepository.findByEmail(email).orElse(null);

        // 🔹 REGISTER
        if (user == null) {
            user = new User();
            user.setEmail(email);
            user.setStatus("ACTIVE");
            user.setProvider(provider);
            user.setProviderId(providerId);

            userRepository.save(user);

            // Assign USER role
            Role role = roleRepository.findByName("USER").orElseThrow();
            userRepository.assignRole(user.getId(), role.getId());

            // Create default page
            Page page = new Page();
            page.setUserId(user.getId());
            page.setTitle("My Links");
            page.setSlug("user-" + user.getId());
            page.setDefault(true);

            pageRepository.save(page);
            userRepository.updateDefaultPage(user.getId(), page.getId());
        }

        // 🔹 LOGIN (JWT)
        String token = jwtUtil.generateToken(user.getEmail());

        response.setContentType("application/json");
        response.getWriter().write("""
            {
              "token": "%s"
            }
        """.formatted(token));
    }
}
