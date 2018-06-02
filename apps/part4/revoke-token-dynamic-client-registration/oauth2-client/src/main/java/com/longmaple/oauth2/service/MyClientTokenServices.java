package com.longmaple.oauth2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.ClientTokenServices;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.DefaultOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;

import com.longmaple.oauth2.data.ClientUser;
import com.longmaple.oauth2.data.ClientUserRepo;

@Service
public class MyClientTokenServices implements ClientTokenServices {

	@Autowired
	private ClientUserRepo clientUserRepo;
	
	@Override
	public OAuth2AccessToken getAccessToken(OAuth2ProtectedResourceDetails resource, Authentication authentication) {
		String username = ((ClientUser) authentication.getPrincipal()).getUsername();
		ClientUser clientUser = clientUserRepo.findByUsername(username);
		String accessTokenStr = clientUser.getAccessToken();
		if (accessTokenStr == null) return null;
		DefaultOAuth2AccessToken accessToken = new DefaultOAuth2AccessToken(accessTokenStr);
		accessToken.setValue(accessTokenStr);
		accessToken.setExpiration(clientUser.getAccessTokenValidity());
		accessToken.setRefreshToken(new DefaultOAuth2RefreshToken(clientUser.getRefreshToken()));
		return accessToken;
	}

	@Override
	public void saveAccessToken(OAuth2ProtectedResourceDetails resource, Authentication authentication,
			OAuth2AccessToken accessToken) {
		ClientUser clientUser = (ClientUser) authentication.getPrincipal();
		clientUser.setAccessToken(accessToken.getValue());
		clientUser.setAccessTokenValidity(accessToken.getExpiration());
		clientUser.setRefreshToken(accessToken.getRefreshToken().getValue());
		clientUserRepo.save(clientUser);
	}

	@Override
	public void removeAccessToken(OAuth2ProtectedResourceDetails resource, Authentication authentication) {
		ClientUser clientUser = (ClientUser) authentication.getPrincipal();
		clientUser.setAccessToken(null);
		clientUser.setRefreshToken(null);
		clientUser.setAccessTokenValidity(null);
		clientUserRepo.save(clientUser);
	}

}
