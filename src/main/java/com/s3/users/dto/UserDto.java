package com.s3.users.dto;

import java.io.Serializable;

import com.s3.users.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {

	private static final long serialVersionUID = 17700158783547108L;

	private long id;
	private String firstName;
	private String lastName;
	private String username;
	private String password;
	private String email;
	private int mobile;
	private String encryptedPassword;

	public UserEntity getUserFromDto(){
		UserEntity user = new UserEntity();
		user.setUsername(username);
		user.setEncryptedPassword(encryptedPassword);
		user.setEmail(email);
		user.setMobile(mobile);
		user.setFirstName(firstName);
		user.setLastName(lastName);

		return user;
	}
}
