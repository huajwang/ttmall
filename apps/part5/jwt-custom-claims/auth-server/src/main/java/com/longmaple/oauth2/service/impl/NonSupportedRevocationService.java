package com.longmaple.oauth2.service.impl;

import com.longmaple.oauth2.service.RevocationService;

public class NonSupportedRevocationService implements RevocationService {

	@Override
	public void revoke(String token) {
		// do nothing
	}
}
