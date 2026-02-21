package com.mylinks.repo;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mylinks.users.entity.PageLink;

@Repository
public interface PageLinkRepository extends JpaRepository<PageLink, UUID> {

    List<PageLink> findByPageIdOrderByPosition(UUID pageId);
}
