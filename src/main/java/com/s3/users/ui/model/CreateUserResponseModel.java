package com.s3.users.ui.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserResponseModel {

	private String firstName;
	private String lastName;
	private String email;
	private String username;
}
