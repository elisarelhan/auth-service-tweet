package com.tweetapp.auth.entity;

import java.io.Serializable;
import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	@Getter private final String token;
	@Getter private final Long expiresIn;
	@Getter private final String userEmail;
		
}
