package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	
	@GetMapping("/api/userInfo")
	public ResponseEntity<String> getUserInfo() {
		return ResponseEntity.ok("Protected user info");
	}
	
	@GetMapping("/callback")
	public String callback() {
		return "callback";
	}

}
