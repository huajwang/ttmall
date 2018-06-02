package com.longmaple.game.service;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.longmaple.game.data.ClientUser;

public interface UserRepository extends CrudRepository<ClientUser, Long> {

	Optional<ClientUser> findByUsername(String username);

}
