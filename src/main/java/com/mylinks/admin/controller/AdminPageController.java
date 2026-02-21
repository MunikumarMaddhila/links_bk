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

import com.mylinks.auth.service.CustomUserDetails;
import com.mylinks.repo.PageRepository;
import com.mylinks.users.entity.Page;

@RestController
@RequestMapping("/api/admin/pages")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminPageController {

    @Autowired
    private PageRepository pageRepository;

    @GetMapping
    public List<Page> myPages(Authentication auth) {
        UUID storeId = ((CustomUserDetails) auth.getPrincipal()).getStoreId();
        return pageRepository.findByStoreId(storeId);
    }

    @PostMapping
    public Page create(@RequestBody Page page, Authentication auth) {
        UUID storeId = ((CustomUserDetails) auth.getPrincipal()).getStoreId();
        page.setId(UUID.randomUUID());
        page.setStoreId(storeId);
        page.setPublished(false);
        return pageRepository.save(page);
    }

    @PutMapping("/{pageId}")
    public Page update(
            @PathVariable UUID pageId,
            @RequestBody Page page,
            Authentication auth) throws AccessDeniedException {

        UUID storeId = ((CustomUserDetails) auth.getPrincipal()).getStoreId();
        Page existing = pageRepository.findById(pageId).orElseThrow();

        if (!existing.getStoreId().equals(storeId)) {
            throw new AccessDeniedException("Forbidden");
        }

        existing.setTitle(page.getTitle());
        existing.setTheme(page.getTheme());
        return pageRepository.save(existing);
    }

    @PutMapping("/{pageId}/publish")
    public void publish(@PathVariable UUID pageId, Authentication auth) {
        try {
			togglePublish(pageId, true, auth);
		} catch (AccessDeniedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @PutMapping("/{pageId}/unpublish")
    public void unpublish(@PathVariable UUID pageId, Authentication auth) {
        try {
			togglePublish(pageId, false, auth);
		} catch (AccessDeniedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    private void togglePublish(UUID pageId, boolean value, Authentication auth) throws AccessDeniedException {
        UUID storeId = ((CustomUserDetails) auth.getPrincipal()).getStoreId();
        Page page = pageRepository.findById(pageId).orElseThrow();

        if (!page.getStoreId().equals(storeId)) {
            throw new AccessDeniedException("Forbidden");
        }

        page.setPublished(value);
        pageRepository.save(page);
    }
}
