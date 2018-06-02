package com.longmaple.oauth2.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.longmaple.oauth2.service.RevocationService;
import com.longmaple.oauth2.service.impl.AccessTokenRevocationService;
import com.longmaple.oauth2.service.impl.NonSupportedRevocationService;
import com.longmaple.oauth2.service.impl.RefreshTokenRevocationService;

@RestController
public class TokenController {
	
	@Autowired
	private DefaultTokenServices tokenServices;
	@Autowired
	private TokenStore tokenStore;

	@PostMapping("/oauth/revoke")
	public ResponseEntity<String> revoke(@RequestParam Map<String, String> params) {   
		System.out.println("aaaaaaaaaaaaaa");
		RevocationService revocationService = null;
		String token_type = params.get("token_type");
		System.out.println("token_type = " + token_type);
		if (token_type.equals("access_token")) {
			revocationService = new AccessTokenRevocationService(tokenServices);
		} else if (token_type.equals("refresh_token")) {
			revocationService = new RefreshTokenRevocationService(tokenStore);
		} else {
			revocationService = new NonSupportedRevocationService();
		}
		String token = params.get("token");
		System.out.println("token = " + token);
		revocationService.revoke(token);
		return ResponseEntity.ok(token + " is revoked");    
	}
	
	@GetMapping("/tokens/{clientId}")
	public ResponseEntity<List<String>> getTokens(@PathVariable String clientId) {
	    List<String> tokenValues = new ArrayList<String>();
	    Collection<OAuth2AccessToken> tokens = tokenStore.findTokensByClientId(clientId); 
	    if (tokens != null){
	        for (OAuth2AccessToken token: tokens){
	            tokenValues.add(token.getValue());
	        }
	    }
	    return ResponseEntity.ok(tokenValues);
	}	
}
