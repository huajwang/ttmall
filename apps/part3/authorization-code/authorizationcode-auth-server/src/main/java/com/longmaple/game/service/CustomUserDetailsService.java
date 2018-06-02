package com.longmaple.game.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.longmaple.game.data.ClientUser;
import com.longmaple.game.data.ClientUserDetails;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<ClientUser> optional = userRepo.findByUsername(username);
		if (!optional.isPresent()) throw new UsernameNotFoundException("user not found");
		ClientUserDetails cud = new ClientUserDetails(optional.get());
		return cud;
	}

}
