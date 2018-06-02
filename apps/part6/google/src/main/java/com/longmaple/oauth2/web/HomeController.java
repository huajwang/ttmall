package com.longmaple.oauth2.web;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.longmaple.oauth2.data.GoogleUser;
import com.longmaple.oauth2.data.GoogleUserRepo;

@Controller
public class HomeController {
	
	@Autowired
	private GoogleUserRepo userRepo;
	
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@GetMapping("/welcome")
	public String index(Authentication auth, Model model) {
		DefaultOidcUser user = (DefaultOidcUser) auth.getPrincipal();
		Map<String, Object> claims = user.getUserInfo().getClaims();
		GoogleUser googleUser = findOrCreate(claims);
		model.addAttribute("user", googleUser);
		return "welcome";
	}
	
	private GoogleUser findOrCreate(Map<String, Object> claims) {
		Optional<GoogleUser> opt = userRepo.findOneBySub(claims.get("sub").toString());
		
		GoogleUser googleUser = opt.orElseGet(() -> {   
			GoogleUser user = new GoogleUser();
			user.setSub(claims.get("sub").toString());
			userRepo.save(user);
			return user;        
		});
		
		googleUser.setName(claims.get("name").toString());
		googleUser.setGender(claims.get("gender").toString());
		googleUser.setProfile(claims.get("profile").toString());
		googleUser.setEmail(claims.get("email").toString());
		googleUser.setPicture(claims.get("picture").toString());
		return googleUser;
	}

	@GetMapping("/login/oauth2")
	public String loginPage() {
		return "login";
	}
}
