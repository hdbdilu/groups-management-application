package com.groups.groupsmanager.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.groups.groupsmanager.exceptionhandling.BadRequestException;
import com.groups.groupsmanager.model.User;
import com.groups.groupsmanager.payload.AuthenticationRequest;
import com.groups.groupsmanager.payload.AuthtenticationResponse;
import com.groups.groupsmanager.payload.RegistrationRequest;
import com.groups.groupsmanager.payload.RegistrationResponse;
import com.groups.groupsmanager.repository.UserRepository;
import com.groups.groupsmanager.userDetails.CustomUserDetailsPrincipal;
import com.groups.groupsmanager.utils.JwtUtil;

@RestController

public class AuthenticationController {

	@Autowired
	UserRepository userRepo;
	@Autowired
	JwtUtil jwtUtil;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping("/auth/login")
	public ResponseEntity<?> login(@Valid @RequestBody AuthenticationRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtUtil.getJwtToken((CustomUserDetailsPrincipal) authentication.getPrincipal());
		return ResponseEntity.ok().body(new AuthtenticationResponse(token));

	}

	@PostMapping("/auth/register")
	public ResponseEntity<?> signUp(@Valid @RequestBody RegistrationRequest signupRequest) throws BadRequestException {

		if (userRepo.existsByEmail(signupRequest.getEmail())) {
			throw StringUtils.hasText(userRepo.findByEmail(signupRequest.getEmail()).get().getPassword())
					? new BadRequestException("This email id is already registered")
					: new BadRequestException("You have already registered with Google using this email Id."
							+ "Please use another emailId for signup or Login with google");

		}
		User result = createUser(signupRequest);
		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("currentUser")
				.buildAndExpand(result.getId()).toUri();
		return ResponseEntity.created(location).body(new RegistrationResponse("Successfully Registered", Boolean.TRUE));

	}

	private User createUser(RegistrationRequest signupRequest) {
		User u = new User();
		u.setEmail(signupRequest.getEmail());
		u.setName(signupRequest.getName());
		u.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
		User result = userRepo.save(u);
		return result;
	}
}
