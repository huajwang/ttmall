package com.longmaple.oauth2.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenProviderChain;
import org.springframework.security.oauth2.client.token.ClientTokenServices;
import org.springframework.security.oauth2.client.token.grant.implicit.ImplicitAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.implicit.ImplicitResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@Configuration
@EnableOAuth2Client
public class ClientConfig {

	@Autowired
	private OAuth2ClientContext clientContext;
	@Autowired
	private ClientTokenServices clientTokenServices;
	
	@Bean
	public OAuth2RestTemplate restTemplate() {
		OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(resource(), clientContext);
		AccessTokenProviderChain providerChain = new AccessTokenProviderChain(
				Arrays.asList(new ImplicitAccessTokenProvider()));
		providerChain.setClientTokenServices(clientTokenServices);
		restTemplate.setAccessTokenProvider(providerChain);
		return restTemplate;
	}

	private OAuth2ProtectedResourceDetails resource() {
		ImplicitResourceDetails resourceDetails = new ImplicitResourceDetails();
		resourceDetails.setAccessTokenUri("http://localhost:8080/oauth/token");
		resourceDetails.setUserAuthorizationUri("http://localhost:8080/oauth/authorize");
		resourceDetails.setClientId("webapp2");
		resourceDetails.setPreEstablishedRedirectUri("http://localhost:8282/callback");
		resourceDetails.setUseCurrentUri(false);
		resourceDetails.setClientAuthenticationScheme(AuthenticationScheme.query);
		return resourceDetails;
	}
	
}
