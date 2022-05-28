package com.s3.users.repository;

import org.springframework.data.repository.CrudRepository;

import com.s3.users.entity.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

	UserEntity findByUsername(String username);
}
