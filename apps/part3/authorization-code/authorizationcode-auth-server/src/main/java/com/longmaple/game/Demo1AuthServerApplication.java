package com.longmaple.game;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class Demo1AuthServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(Demo1AuthServerApplication.class, args);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(4);    
		String clientSecret = "abcd1234";    
		clientSecret = encoder.encode(clientSecret);    
		System.out.println("client secret: " + clientSecret);
	}
}
