package com.longmaple.oauth2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.longmaple.oauth2.data.Address;

@RestController
public class UserController {
	
	@Autowired
	private OAuth2RestTemplate restTemplate;

	@GetMapping("/profile")
	public Address getProfile() {
		Address addr = restTemplate.getForObject("http://localhost:8080/address", Address.class);
		return addr;
	}
}
