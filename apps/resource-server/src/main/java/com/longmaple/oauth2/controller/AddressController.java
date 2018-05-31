package com.longmaple.oauth2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.longmaple.oauth2.data.Address;
import com.longmaple.oauth2.data.EMallUser;

@RestController
public class AddressController {

	@Autowired
	private AddressRepo addrRepo;

	@GetMapping("/address")
	@PreAuthorize("#oauth2.hasScope('user:profile:read')")
	public ResponseEntity<Address> getAddress(Authentication auth) {
		EMallUser user = (EMallUser) auth.getPrincipal();
		Address addr = addrRepo.findByUserId(user.getId());
		return ResponseEntity.ok(addr);
	}
}
