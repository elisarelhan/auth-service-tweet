package com.tweetapp.auth.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tweetapp.auth.config.JwtTokenUtil;
import com.tweetapp.auth.entity.JwtRequest;
import com.tweetapp.auth.entity.JwtResponse;
import com.tweetapp.auth.entity.User;
import com.tweetapp.auth.exception.UserNotFoundException;
import com.tweetapp.auth.exception.UsernamePresentException;
import com.tweetapp.auth.service.AuthService;
import com.tweetapp.auth.service.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin
@Slf4j
@SecurityRequirement(name = "auth-service")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private AuthService authService;
	@Autowired
	private UserService userService;

	@PostMapping(value = "/login")
	public ResponseEntity<?> createAuthenticationToken( @RequestBody JwtRequest authenticationRequest) throws Exception {
		try {
			authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());

			final UserDetails userDetails = authService.loadUserByUsername(authenticationRequest.getEmail());

			final String token = jwtTokenUtil.generateToken(userDetails);
			final long expiresIn = JwtTokenUtil.JWT_VALIDITY;

			return ResponseEntity.ok(new JwtResponse(token, expiresIn,userDetails.getUsername()));
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	@PostMapping(value = "/register")
	public ResponseEntity<?> saveUser(@Valid @RequestBody User user)throws Exception {

		try {
			return ResponseEntity.ok(authService.save(user));
		} catch (UsernamePresentException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PatchMapping(value = "/forgotPassword")
	public ResponseEntity<?> saveUser(@Valid @RequestBody JwtRequest request) {

		try {
			return ResponseEntity.ok(authService.forgotPassword(request.getEmail(), request.getPassword()));
		} catch (UserNotFoundException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}

	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}

}
