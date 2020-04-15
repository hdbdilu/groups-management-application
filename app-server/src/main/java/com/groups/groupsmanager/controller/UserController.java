package com.groups.groupsmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.groups.groupsmanager.exceptionhandling.ResourceNotFoundException;
import com.groups.groupsmanager.metaannotations.CurrentUser;
import com.groups.groupsmanager.model.User;
import com.groups.groupsmanager.payload.UserProfile;
import com.groups.groupsmanager.repository.UserRepository;
import com.groups.groupsmanager.userDetails.CustomUserDetailsPrincipal;

@RestController
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/currentUser")
	public ResponseEntity<?> getCurrentUser(@CurrentUser CustomUserDetailsPrincipal userPrincipal) {
		User user = userRepository.findById(userPrincipal.getId())
				.orElseThrow(() -> new ResourceNotFoundException(userPrincipal.getId().toString()));

		return ResponseEntity.ok().body(new UserProfile(user.getName(), user.getEmail()));
	}

}
