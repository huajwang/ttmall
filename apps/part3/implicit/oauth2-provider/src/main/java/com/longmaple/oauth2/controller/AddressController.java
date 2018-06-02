package com.longmaple.oauth2.controller;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.longmaple.oauth2.data.Address;

@RestController
public class AddressController {

	@GetMapping("/address")
	@CrossOrigin(origins = "http://localhost:8282")
	public ResponseEntity<Address> getAddress(Principal principal) {
		String username = principal.getName();
		Address addr = null;
		if (username.equals("joe")) {
			addr = new Address("175 Columnbia St. W.", "Waterloo", "Ontario", "N2K 2LC");
		} else if (username.equals("don")) {
			addr = new Address("2355 King Stree North", "Toronto", "Ontario", "N2M 3KZ");
		}
		return ResponseEntity.ok(addr);
	}
}
