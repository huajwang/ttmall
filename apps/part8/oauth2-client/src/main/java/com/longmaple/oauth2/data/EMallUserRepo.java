package com.longmaple.oauth2.data;

import org.springframework.data.repository.CrudRepository;

public interface EMallUserRepo extends CrudRepository<EMallUser, Long> {

	EMallUser findByUsername(String username);

}
