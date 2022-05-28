package com.s3.users.service;

import java.util.*;

import com.s3.users.entity.RoleEntity;
import com.s3.users.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.s3.users.dto.UserDto;
import com.s3.users.entity.UserEntity;
import com.s3.users.repository.UserRepository;

@Service(value = "userService")
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public UserDto createUser(UserDto userDetails) {
		
		//userDetails.setUserId(UUID.randomUUID().toString());
		//userDetails.setId(UUID.randomUUID().toString());
		userDetails.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));
		//ModelMapper mapper = new ModelMapper();
		//mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		//UserEntity userEntity = mapper.map(userDetails, UserEntity.class);
		UserEntity userEntity = userDetails.getUserFromDto();

				//For now default role user
		RoleEntity role = roleRepository.findByName("USER");
		Set<RoleEntity> roleSet = new HashSet<>();
		roleSet.add(role);
		userEntity.setRoles(roleSet);

		userRepository.save(userEntity);
		
		return userDetails;
	}

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity user = userRepository.findByUsername(username);
		if(user == null){
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getEncryptedPassword(), getAuthority(user));
	}

	private Set<SimpleGrantedAuthority> getAuthority(UserEntity user) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		user.getRoles().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
		});
		return authorities;
	}

	/*public List<User> findAll() {
		List<User> list = new ArrayList<>();
		userRepository.findAll().iterator().forEachRemaining(list::add);
		return list;
	}*/

	public UserEntity findOne(String username) {
		return userRepository.findByUsername(username);
	}
}
