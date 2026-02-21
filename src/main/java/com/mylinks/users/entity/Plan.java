package com.mylinks.users.entity;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "plans")
@Data
public class Plan {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    private BigDecimal price;

    @Column(name = "max_pages")
    private int maxPages;

    @Column(name = "max_stores")
    private int maxStores;

    @Column(columnDefinition = "jsonb")
    private String features;

    // getters & setters
}
