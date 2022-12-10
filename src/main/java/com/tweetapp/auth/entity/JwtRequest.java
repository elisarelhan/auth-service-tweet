package com.tweetapp.auth.entity;

import java.io.Serializable;

import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class JwtRequest implements Serializable {
	
	private static final long serialVersionUID = 5926468583005150707L;
	
	@Getter @Setter private String email;
	@Pattern(regexp ="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message="Password should containe minimum 8 characters, at least one uppercase letter, one lowercase letter, one number and one special character")
	@Getter @Setter private String password;
}
