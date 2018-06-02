package com.longmaple.oauth2.config;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.client.resource.OAuth2AccessDeniedException;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.resource.UserRedirectRequiredException;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.implicit.ImplicitAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.implicit.ImplicitResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class CustomImplicitAccessTokenProvider extends ImplicitAccessTokenProvider {

	@Override
	public OAuth2AccessToken obtainAccessToken(OAuth2ProtectedResourceDetails details, AccessTokenRequest request)
			throws UserRedirectRequiredException, AccessDeniedException, OAuth2AccessDeniedException {

		ImplicitResourceDetails resource = (ImplicitResourceDetails) details;
		Map<String, String> requestParameters = getParametersForTokenRequest(resource, request).toSingleValueMap();
		throw new UserRedirectRequiredException(resource.getUserAuthorizationUri(), requestParameters);
	}

	private MultiValueMap<String, String> getParametersForTokenRequest(ImplicitResourceDetails resource,
			AccessTokenRequest request) {

		MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
		form.set("response_type", "token");
		form.set("client_id", resource.getClientId());
		
		if (resource.isScoped()) {

			StringBuilder builder = new StringBuilder();
			List<String> scope = resource.getScope();

			if (scope != null) {
				Iterator<String> scopeIt = scope.iterator();
				while (scopeIt.hasNext()) {
					builder.append(scopeIt.next());
					if (scopeIt.hasNext()) {
						builder.append(' ');
					}
				}
			}

			form.set("scope", builder.toString());
		}

		for (String key : request.keySet()) {
			form.put(key, request.get(key));
		}

		String redirectUri = resource.getRedirectUri(request);
		if (redirectUri == null) {
			throw new IllegalStateException("No redirect URI available in request");
		}
		form.set("redirect_uri", redirectUri);

		return form;

	}
	
}
