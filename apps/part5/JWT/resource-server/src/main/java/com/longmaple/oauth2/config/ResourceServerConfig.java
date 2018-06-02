package com.longmaple.oauth2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.JwtAccessTokenConverterConfigurer;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter implements JwtAccessTokenConverterConfigurer {
	
	@Autowired    
	private UserDetailsService userDetailsService;   
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.anyRequest().authenticated()
		.and()
		.requestMatchers().antMatchers("/address")
		.and().cors();
	} 
	
	private DefaultAccessTokenConverter tokenConverter() {        
		DefaultUserAuthenticationConverter converter = new DefaultUserAuthenticationConverter();        
		converter.setUserDetailsService(userDetailsService);     
		DefaultAccessTokenConverter tokenConverter = new DefaultAccessTokenConverter();   
		tokenConverter.setUserTokenConverter(converter); 
		return tokenConverter;    
	}

	@Override
	public void configure(JwtAccessTokenConverter converter) {
		converter.setAccessTokenConverter(tokenConverter());
	}    
}
