package com.mylinks.response;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.mylinks.users.entity.Role;

import lombok.Data;

@Data

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {

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
	private Role role;

}
