package com.mylinks.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mylinks.auth.service.ProfileService;
import com.mylinks.common.vo.UserVo;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

	@Autowired
	private ProfileService profileService;
	
	public ResponseEntity<?> saveProfile(@RequestBody UserVo user){
		
		return profileService.saveProfile(user);
	}
}
