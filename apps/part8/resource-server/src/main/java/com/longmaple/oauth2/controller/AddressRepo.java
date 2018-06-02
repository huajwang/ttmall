package com.longmaple.oauth2.controller;

import org.springframework.data.repository.CrudRepository;

import com.longmaple.oauth2.data.Address;

public interface AddressRepo extends CrudRepository<Address, Long> {

	Address findByUserId(Long userId);

}
