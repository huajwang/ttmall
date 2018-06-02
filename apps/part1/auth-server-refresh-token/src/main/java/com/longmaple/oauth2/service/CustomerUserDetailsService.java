package com.longmaple.oauth2.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.longmaple.oauth2.data.ClientUser;

@Service
public class CustomerUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		ClientUser user = null;
		if (username.equals("joe")) {
			user = new ClientUser("joe", "password1");
		} else if (username.equals("don")) {
			user = new ClientUser("don", "password2");
		}
		return user;
	}

}
