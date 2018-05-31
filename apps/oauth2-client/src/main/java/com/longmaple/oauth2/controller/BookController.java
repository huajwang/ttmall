package com.longmaple.oauth2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

	@GetMapping("/author")
	public String author() {
		return "Huajian Wang";
	}
}
