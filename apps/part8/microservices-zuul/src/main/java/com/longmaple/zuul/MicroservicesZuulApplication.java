package com.longmaple.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import com.longmaple.zuul.filter.MyZuulFilter;

@SpringBootApplication
@EnableZuulProxy
public class MicroservicesZuulApplication {
	
	@Bean
	public MyZuulFilter zuulFilter() {
		return new MyZuulFilter();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(MicroservicesZuulApplication.class, args);
	}
}
