package com.longmaple.oauth2.data;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface GoogleUserRepo extends CrudRepository<GoogleUser, Long> {

	Optional<GoogleUser> findOneBySub(String sub);
}
