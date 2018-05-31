package com.longmaple.microservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class LibraryReservationApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryReservationApplication.class, args);
	}
}
