package com.longmaple.oauth2.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableOAuth2Sso
public class OAuth2SocialConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
        http
        	.csrf().disable()
			.authorizeRequests()
			.antMatchers("/index")
			.permitAll()
			.anyRequest()
			.authenticated()
		.and()
			.logout().logoutSuccessUrl("/index").permitAll();
	}
}
