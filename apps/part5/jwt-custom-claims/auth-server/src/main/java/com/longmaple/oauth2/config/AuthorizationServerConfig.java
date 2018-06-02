package com.longmaple.oauth2.config;

import java.util.Arrays;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientRegistrationService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import com.longmaple.oauth2.util.CustomClaimsTokenEnhancer;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
	
	@Autowired
	private DataSource dataSource;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private CustomClaimsTokenEnhancer tokenEnhancer;
	
	@Value("${signing.key}")
	private String signingKey;

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(4);
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.jdbc(dataSource);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
	    TokenEnhancerChain chain = new TokenEnhancerChain();    
	    chain.setTokenEnhancers(Arrays.asList(tokenEnhancer, accessTokenConverter()));
		
		endpoints.tokenStore(tokenStore()).tokenEnhancer(chain).accessTokenConverter(accessTokenConverter())
		.userDetailsService(userDetailsService).authenticationManager(authenticationManager);
	}
	
	private JwtAccessTokenConverter accessTokenConverter() {    
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter(); 
		converter.setSigningKey(signingKey);    
		return converter;
		
	}
	
	private JwtTokenStore tokenStore() {    
		return new JwtTokenStore(accessTokenConverter());
		
	}	
	
	@Bean    
	public ClientRegistrationService clientRegistrationService() {        
		return new JdbcClientDetailsService(dataSource);    	
	}
}
