package com.mylinks.auth.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mylinks.auth.service.PageLinkService;
import com.mylinks.users.entity.PageLink;

@RestController
@RequestMapping("/api/pages/{pageId}/links")
public class PageLinkController {

    @Autowired
    private PageLinkService pageLinkService;

    @GetMapping
    public List<PageLink> getLinks(@PathVariable UUID pageId) {
        return pageLinkService.getLinks(pageId);
    }

    @PostMapping
    public PageLink addLink(@PathVariable UUID pageId,
                            @RequestParam String title,
                            @RequestParam String url) {
        return pageLinkService.addLink(pageId, title, url);
    }

    
    
    @PutMapping("/{linkId}")
    public PageLink updateLink(@PathVariable UUID linkId,
                               @RequestParam String title,
                               @RequestParam String url) {
        return pageLinkService.updateLink(linkId, title, url);
    }

    @PutMapping("/{linkId}/status")
    public void toggleLink(@PathVariable UUID linkId,
                           @RequestParam boolean active) {
        pageLinkService.toggleLink(linkId, active);
    }

    @DeleteMapping("/{linkId}")
    public void deleteLink(@PathVariable UUID linkId) {
        pageLinkService.deleteLink(linkId);
    }
}
