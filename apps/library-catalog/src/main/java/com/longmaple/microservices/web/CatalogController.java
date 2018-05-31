package com.longmaple.microservices.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.longmaple.microservices.data.Book;

@RestController
public class CatalogController {

	@Value("${catalog.size}")
	private int size;
	
	@RequestMapping("/catalog")
	@CrossOrigin
	public List<Book> getCatalog() {
		return Book.getBooks().subList(0, size);
	}
}
