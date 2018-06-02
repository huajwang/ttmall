package com.longmaple.game.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.longmaple.game.data.ClientUserDetails;
import com.longmaple.game.data.UserProfile;

@RestController
public class UserController {

	@GetMapping("/user/getProfile")
	@PreAuthorize("#oauth2.hasScope('user:profile:read')")
	public ResponseEntity<UserProfile> profile(Authentication auth) {
		ClientUserDetails user = (ClientUserDetails) auth.getPrincipal();
        String email = user.getUsername() + "@longmaple.com";

        UserProfile profile = new UserProfile();
        profile.setName(user.getUsername());
        profile.setEmail(email);

        return ResponseEntity.ok(profile);
    }
	
	
	@PutMapping("/user/updateProfile")
	@PreAuthorize("#oauth2.hasScope('user:profile:write')")
	public void updateProfile(UserProfile up) {
		System.out.println("user name: " + up.getName());
		System.out.println("email: " + up.getEmail());
	}
}
