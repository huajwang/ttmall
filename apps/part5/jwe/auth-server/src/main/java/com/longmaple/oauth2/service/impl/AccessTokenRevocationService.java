package com.longmaple.oauth2.service.impl;

import org.springframework.security.oauth2.provider.token.DefaultTokenServices;

import com.longmaple.oauth2.service.RevocationService;

public class AccessTokenRevocationService implements RevocationService {
	
	private DefaultTokenServices tokenServices;
	
	public AccessTokenRevocationService(DefaultTokenServices tokenServices) {
		this.tokenServices = tokenServices;
	}

	@Override
	public void revoke(String token) {
		tokenServices.revokeToken(token);
	}

}
