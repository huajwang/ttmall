package com.longmaple.oauth2.service.impl;

import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import com.longmaple.oauth2.service.RevocationService;

public class RefreshTokenRevocationService implements RevocationService {
	
	private TokenStore tokenStore;
	
	public RefreshTokenRevocationService(TokenStore tokenStore) {
		this.tokenStore = tokenStore;
	}

	@Override
	public void revoke(String token) {
		if (tokenStore instanceof JdbcTokenStore){
	        ((JdbcTokenStore) tokenStore).removeRefreshToken(token);
	    }
	}

}
