package com.mylinks.auth.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mylinks.repo.PageLinkRepository;
import com.mylinks.users.entity.PageLink;

@Service
public class PageLinkService {

    @Autowired
    private PageLinkRepository pageLinkRepository;

    public List<PageLink> getLinks(UUID pageId) {
        return pageLinkRepository.findByPageIdOrderByPosition(pageId);
    }

    public PageLink addLink(UUID pageId, String title, String url) {

        int nextPosition = pageLinkRepository
                .findByPageIdOrderByPosition(pageId)
                .size() + 1;

        PageLink link = new PageLink();
        link.setPageId(pageId);
        link.setTitle(title);
        link.setUrl(url);
        link.setPosition(nextPosition);

        return pageLinkRepository.save(link);
    }

    public PageLink updateLink(UUID linkId, String title, String url) {
        PageLink link = pageLinkRepository.findById(linkId).orElseThrow();
        link.setTitle(title);
        link.setUrl(url);
        return pageLinkRepository.save(link);
    }

    public void toggleLink(UUID linkId, boolean active) {
        PageLink link = pageLinkRepository.findById(linkId).orElseThrow();
        link.setActive(active);
        pageLinkRepository.save(link);
    }

    public void deleteLink(UUID linkId) {
        pageLinkRepository.deleteById(linkId);
    }
}
