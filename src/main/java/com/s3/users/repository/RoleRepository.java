package com.s3.users.repository;

import com.s3.users.entity.RoleEntity;
import com.s3.users.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<RoleEntity, Long> {
	RoleEntity findByName(String name);;
}
