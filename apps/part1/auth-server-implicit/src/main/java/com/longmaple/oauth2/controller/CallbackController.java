package com.longmaple.oauth2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CallbackController {

	@GetMapping("/callback")
	public String callback() {
		return "access token is delivered to this url";
	}

}
