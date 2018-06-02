package com.longmaple.oauth2.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.implicit.ImplicitResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@Configuration
@EnableOAuth2Client
public class ClientConfig {

	@Autowired
	private OAuth2ClientContext clientContext;
	
	@Bean
	public OAuth2RestTemplate restTemplate() {
		OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(resource(), clientContext);
		restTemplate.setAccessTokenProvider(new CustomImplicitAccessTokenProvider());
		return restTemplate;
	}

	private OAuth2ProtectedResourceDetails resource() {
		ImplicitResourceDetails resourceDetails = new ImplicitResourceDetails();
		resourceDetails.setUserAuthorizationUri("http://localhost:8080/oauth/authorize");
		resourceDetails.setClientId("webapp");
		resourceDetails.setPreEstablishedRedirectUri("http://localhost:8282/callback");
		resourceDetails.setScope(Arrays.asList("user:profile:read"));
		resourceDetails.setUseCurrentUri(false);
		return resourceDetails;
	}
}
