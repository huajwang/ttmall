package com.longmaple.oauth2.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.longmaple.oauth2.data.Address;

@RestController
public class AddressController {

	@GetMapping("/address")
	public ResponseEntity<Address> getAddress() {
		Address addr = new Address("555 Bay Sreet", "Q2K 3Z5", "Montreal", "Quebec");
		return ResponseEntity.ok(addr);
	}
}
