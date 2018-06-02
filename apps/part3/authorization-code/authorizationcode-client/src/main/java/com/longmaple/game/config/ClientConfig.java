package com.longmaple.game.config;

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
	private OAuth2ClientContext clientContext;
	@Autowired
	private ClientTokenServices clientTokenServices;
	
	@Bean
	public OAuth2RestTemplate restTemplate() {
		OAuth2RestTemplate template = new OAuth2RestTemplate(resource(), clientContext);
		AccessTokenProviderChain provider = new AccessTokenProviderChain(
				Arrays.asList(new AuthorizationCodeAccessTokenProvider()));
		provider.setClientTokenServices(clientTokenServices);
		template.setAccessTokenProvider(provider);
		
		return template;
	}

	@Bean
	public OAuth2ProtectedResourceDetails resource() {
		AuthorizationCodeResourceDetails resourceDetails = new AuthorizationCodeResourceDetails();
		resourceDetails.setUserAuthorizationUri("http://localhost:9000/oauth/authorize");
		resourceDetails.setAccessTokenUri("http://localhost:9000/oauth/token");
		resourceDetails.setClientId("clientapp");
		resourceDetails.setClientSecret("abcd1234");
		resourceDetails.setScope(Arrays.asList("user:profile:read", "user:profile:write"));
		resourceDetails.setPreEstablishedRedirectUri("http://localhost:9002/callback");
		resourceDetails.setUseCurrentUri(false);
		resourceDetails.setClientAuthenticationScheme(AuthenticationScheme.header);
		return resourceDetails;
	}
}
