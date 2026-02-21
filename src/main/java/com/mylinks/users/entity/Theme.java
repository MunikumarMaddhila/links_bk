package com.mylinks.users.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "themes")
public class Theme {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    @Column(name = "css_path")
    private String cssPath;

    @Column(name = "is_paid")
    private boolean paid;

    // getters & setters
}

