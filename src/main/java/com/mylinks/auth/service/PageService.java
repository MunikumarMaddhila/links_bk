package com.mylinks.auth.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mylinks.repo.PageRepository;
import com.mylinks.repo.UserRepository;
import com.mylinks.users.entity.Page;
import com.mylinks.users.entity.User;

@Service
public class PageService {

    @Autowired
    private PageRepository pageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubscriptionValidationService validationService;

    public List<Page> getMyPages(String email) {
        UUID userId = userRepository.findByEmail(email).orElseThrow().getId();
        return pageRepository.findByUserId(userId);
    }

    public Page createPage(String email, String title) {

        User user = userRepository.findByEmail(email).orElseThrow();
        validationService.validatePageLimit(user.getId());

        Page page = new Page();
        page.setUserId(user.getId());
        page.setTitle(title);
        page.setSlug(title.toLowerCase().replace(" ", "-") + "-" + System.currentTimeMillis());
        page.setDefault(false);

        return pageRepository.save(page);
    }

    public void deletePage(UUID pageId) {
        Page page = pageRepository.findById(pageId).orElseThrow();
        if (page.isDefault()) {
            throw new RuntimeException("Default page cannot be deleted");
        }
        pageRepository.delete(page);
    }
}

