package com.longmaple.oauth2.controller;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.longmaple.oauth2.data.Address;

@RestController
public class AddressController {

	@GetMapping("/address")
	public ResponseEntity<Address> getAddress(Principal principal) {
		String username = principal.getName();
		Address addr = null;
		if (username.equals("joe")) {
			addr = new Address("175 Columnbia St. W.", "Waterloo", "N2K 2LC", "Ontario");
		} else if (username.equals("don")) {
			addr = new Address("2355 King Stree North", "Toronto", "N2M 3KZ", "Ontario");
		}
		return ResponseEntity.ok(addr);
	}
}
