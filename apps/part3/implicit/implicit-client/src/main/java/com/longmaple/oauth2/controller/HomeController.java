package com.longmaple.oauth2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
	
	@Autowired
	private OAuth2RestTemplate restTemplate;
	
	@GetMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping("/callback")
	public ModelAndView callback() {
		return new ModelAndView("dashboard");
	}
	
	@GetMapping("/dashboard")
	public void dashboard() {
		restTemplate.getAccessToken();
	}
}
