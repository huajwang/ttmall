package com.longmaple.oauth2.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.longmaple.oauth2.data.EMallUser;

@Component
public class CustomClaimsTokenEnhancer implements TokenEnhancer {
	
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		 Map<String, Object> additional = new HashMap<>();        
		 EMallUser user = (EMallUser) authentication.getPrincipal(); 
		 additional.put("email", user.getUsername() + "@longmaple.com");        
		 DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) accessToken;        
		 token.setAdditionalInformation(additional);        
		 return accessToken;
	}
}
