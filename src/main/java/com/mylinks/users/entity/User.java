package com.mylinks.users.entity;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue
    private UUID id;
    private UUID storeId;
    private String email;
    private String password;
    private String status;
    private String provider;
    private String providerId;
    private String phone;
    private String name;
    private String bio;
    private String image;
    private boolean active;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")   // FK column in users table
    private Role role;

//    @OneToMany(fetch = FetchType.EAGER)
//    @JoinColumn(name = "default_page_id")
//    private Page page;
    
}
