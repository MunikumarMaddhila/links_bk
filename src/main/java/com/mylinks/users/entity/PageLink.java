package com.mylinks.users.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "page_links")
@Data
public class PageLink {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "page_id")
    private UUID pageId;

    private String title;

    private String url;

    private Integer position;

    @Column(name = "is_active")
    private boolean active = true;

    // getters & setters
}
