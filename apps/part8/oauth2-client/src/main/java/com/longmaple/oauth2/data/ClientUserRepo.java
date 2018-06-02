package com.longmaple.oauth2.data;

import org.springframework.data.repository.CrudRepository;

public interface ClientUserRepo extends CrudRepository<ClientUser, Long> {

	ClientUser findByUsername(String username);
}
