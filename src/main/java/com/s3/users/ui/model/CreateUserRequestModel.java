package com.s3.users.ui.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequestModel {

	@NotNull(message = "First name cannot be empty")
	@Size(min = 2, message="First name must not be less than 2 char")
	private String firstName;
	
	@NotNull(message = "Last name cannot be empty")
	@Size(min = 2, message="Last name must not be less than 2 char")
	private String lastName;
	
	@NotNull(message = "password cannot be empty")
	@Size(min = 8, max=16, message = "Password must be min 8 characters long and less then 16 chars")
	private String password;
	
	@NotNull(message = "email cannot be empty")
	@Email
	private String email;

	@NotNull(message = "username cannot be empty")
	//@Size(min = 2, message="username name must not be less than 2 char")
	private String username;

	@NotNull(message = "username cannot be empty")
	private int mobile;
}
