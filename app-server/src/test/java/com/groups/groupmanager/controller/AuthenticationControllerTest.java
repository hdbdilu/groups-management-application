package com.groups.groupmanager.controller;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.groups.groupsmanager.controller.AuthenticationController;
import com.groups.groupsmanager.exceptionhandling.BadRequestException;
import com.groups.groupsmanager.exceptionhandling.UserNotAuthorizedException;
import com.groups.groupsmanager.model.User;
import com.groups.groupsmanager.payload.RegistrationRequest;
import com.groups.groupsmanager.repository.UserRepository;
import com.groups.groupsmanager.userDetails.CustomUserDetailsPrincipal;

@ExtendWith(MockitoExtension.class)
public class AuthenticationControllerTest {
	@InjectMocks
	AuthenticationController authenticationController;

	@Mock
	UserRepository userRepo;

	@Mock
	CustomUserDetailsPrincipal userPrincipal;
	@Mock
	RegistrationRequest registrationRequest;

	@Mock
	PasswordEncoder passwordEncoder;
	@Mock
	ServletUriComponentsBuilder builder;
	@Mock
	User user;

	@Test
	public void signUp() throws BadRequestException, UserNotAuthorizedException {

		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		Mockito.when(registrationRequest.getEmail()).thenReturn("testemail@email.com");
		Mockito.when(userRepo.existsByEmail("testemail@email.com")).thenReturn(true);
		User u = new User(1L, "Nitika", "testmail@gmail.com", null);
		Mockito.when(userRepo.findByEmail("testemail@email.com")).thenReturn(Optional.of(u));
		try {
			authenticationController.signUp(registrationRequest);
		} catch (BadRequestException ex) {
			// Assert.assertEquals(responseEntity.getStatusCodeValue(), 200);
			Assert.notNull(ex.getMessage());
		}

	}

}
