package com.s3.users.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.s3.users.dto.UserDto;


public interface UserService extends UserDetailsService {
	UserDto createUser(UserDto user);
}
