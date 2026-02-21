package com.mylinks.auth.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mylinks.auth.service.PageService;
import com.mylinks.users.entity.Page;

@RestController
@RequestMapping("/api/pages")
public class PageController {

    @Autowired
    private PageService pageService;

    @PreAuthorize("hasAuthority('CREATE_PAGE')")
    @PostMapping
    public Page createPage(@RequestParam String title,
                           Authentication auth) {
        return pageService.createPage(auth.getName(), title);
    }

    @GetMapping("/my")
    public List<Page> myPages(Authentication auth) {
        return pageService.getMyPages(auth.getName());
    }

    @DeleteMapping("/{id}")
    public void deletePage(@PathVariable UUID id) {
        pageService.deletePage(id);
    }
    
    @GetMapping("/me")
    public String me(Authentication authentication) {
    	if (authentication == null) {
            return "AUTH IS NULL";
        }

        return authentication.getName();
    }
}
