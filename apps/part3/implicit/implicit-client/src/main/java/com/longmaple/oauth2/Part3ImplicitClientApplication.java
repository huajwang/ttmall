package com.longmaple.oauth2;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletContextInitializer;

@SpringBootApplication
public class Part3ImplicitClientApplication implements ServletContextInitializer {

	public static void main(String[] args) {
		SpringApplication.run(Part3ImplicitClientApplication.class, args);
	}

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		servletContext.getSessionCookieConfig().setName("client-app-session");
	}
}
