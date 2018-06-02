package com.longmaple.oauth2.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.longmaple.oauth2.data.Address;

@RestController
public class AddressController {

	@GetMapping("/address")
	@PreAuthorize("#oauth2.hasScope('user:profile:read')")
	public ResponseEntity<Address> getAddress() {
		Address addr = new Address();
		addr.setStreet("502 East Bridge");
		addr.setCity("Waterloo");
		addr.setProvince("Ontario");
		addr.setPostalCode("N2K3Z5");
		return ResponseEntity.ok(addr);
	}
}
