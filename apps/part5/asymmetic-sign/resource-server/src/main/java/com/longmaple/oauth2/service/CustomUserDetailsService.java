package com.longmaple.oauth2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.longmaple.oauth2.data.EMallUser;
import com.longmaple.oauth2.data.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		EMallUser user = userRepo.findByUsername(username);
		if (user == null) throw new UsernameNotFoundException("EMallUser Not Found!");
		return user;
	}

}
