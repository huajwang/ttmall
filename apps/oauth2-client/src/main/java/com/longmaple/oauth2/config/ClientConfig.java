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
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@Configuration
@EnableOAuth2Client
public class ClientConfig {
	
	@Autowired
	public OAuth2ClientContext clientContext;
	
	@Autowired
	public ClientTokenServices clientTokenServices;
	
	@Bean
	public OAuth2RestTemplate restTemplate() {
		OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(resource(), clientContext);
		AccessTokenProviderChain providerChain = 
				new AccessTokenProviderChain(Arrays.asList(new AuthorizationCodeAccessTokenProvider()));
		providerChain.setClientTokenServices(clientTokenServices);
		restTemplate.setAccessTokenProvider(providerChain);
		return restTemplate;
	}

	private OAuth2ProtectedResourceDetails resource() {
		AuthorizationCodeResourceDetails resourceDetails = new AuthorizationCodeResourceDetails();
		resourceDetails.setAccessTokenUri("http://localhost:7001/oauth/token");
		resourceDetails.setUserAuthorizationUri("http://localhost:7001/oauth/authorize");
		resourceDetails.setClientId("webapp");
		resourceDetails.setClientSecret("abcd1234");
		resourceDetails.setScope(Arrays.asList("user:profile:read", "user:profile:write"));
		resourceDetails.setPreEstablishedRedirectUri("http://localhost:7003/callback");
		resourceDetails.setUseCurrentUri(false);
		resourceDetails.setClientAuthenticationScheme(AuthenticationScheme.header);
		return resourceDetails;
	}
}
