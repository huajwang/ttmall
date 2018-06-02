package com.longmaple.oauth2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@Configuration
@EnableOAuth2Client
public class ClientConfig {

	@Autowired
	private OAuth2ClientContext clientContext;
	
	@Bean
	public OAuth2RestTemplate restTemplate() {
		OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(resourceDetails(), clientContext);
		return restTemplate;
	}
	
	private OAuth2ProtectedResourceDetails resourceDetails() {
		ClientCredentialsResourceDetails resourceDetails = new ClientCredentialsResourceDetails();
		resourceDetails.setAccessTokenUri("http://localhost:8080/oauth/token");
		resourceDetails.setClientId("webapp");
		resourceDetails.setClientSecret("abcd1234");
		resourceDetails.setClientAuthenticationScheme(AuthenticationScheme.header);
		resourceDetails.setGrantType("client_credentials");
		return resourceDetails;
	}
}
