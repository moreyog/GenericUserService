package com.s3.users.controllers;

import javax.validation.Valid;

import com.s3.users.config.TokenProvider;
import com.s3.users.ui.model.AuthToken;
import com.s3.users.ui.model.LoginRequestModel;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.s3.users.dto.UserDto;
import com.s3.users.entity.UserEntity;
import com.s3.users.service.UserService;
import com.s3.users.ui.model.CreateUserRequestModel;
import com.s3.users.ui.model.CreateUserResponseModel;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	Environment environment;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private TokenProvider jwtTokenUtil;

	@Autowired
	UserService userService;
	
	@GetMapping("/status/check")
	public String status() {
		return "success " + environment.getProperty("local.server.port");
	}

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> generateToken(@RequestBody LoginRequestModel loginUser) throws AuthenticationException {

		final Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						loginUser.getUsername(),
						loginUser.getPassword()
				)
		);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		final String token = jwtTokenUtil.generateToken(authentication);
		return ResponseEntity.ok(new AuthToken(token));
	}

	@PostMapping("/signup")
	public ResponseEntity<CreateUserResponseModel> createUser(@Valid @RequestBody CreateUserRequestModel createUserRequestModel) {
		System.out.println("createUserRequestModel : "+createUserRequestModel);
		
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserDto userDto = mapper.map(createUserRequestModel, UserDto.class);
		
		UserDto createdUser = userService.createUser(userDto);
		CreateUserResponseModel createUserResponseModel = mapper.map(createdUser, CreateUserResponseModel.class);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(createUserResponseModel);
	}
	
	
	@PutMapping
	public String updateUser() {
		return "update success " + environment.getProperty("local.server.port");
	}
	
	@DeleteMapping
	public String deleteUser() {
		return "delete success " + environment.getProperty("local.server.port");
	}

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value="/adminping", method = RequestMethod.GET)
	public String adminPing(){
		return "Only Admins Can Read This";
	}

	@PreAuthorize("hasRole('USER')")
	@RequestMapping(value="/userping", method = RequestMethod.GET)
	public String userPing(){
		return "Any User Can Read This";
	}
	
}
