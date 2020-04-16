package com.groups.groupmanager.controller;

import java.util.Optional;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.groups.groupsmanager.controller.UserController;
import com.groups.groupsmanager.exceptionhandling.BadRequestException;
import com.groups.groupsmanager.exceptionhandling.UserNotAuthorizedException;
import com.groups.groupsmanager.model.User;
import com.groups.groupsmanager.repository.UserRepository;
import com.groups.groupsmanager.userDetails.CustomUserDetailsPrincipal;

@ExtendWith(MockitoExtension.class)
public class UserConntrollerTest {
	@InjectMocks
	UserController userController;

	@Mock
	UserRepository userRepo;

	@Mock
	CustomUserDetailsPrincipal userPrincipal;

	@Test
	public void getCurrentUser() throws BadRequestException, UserNotAuthorizedException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		User u = new User(1L, "Nitika", "testmail@gmail.com", "###");
		Optional<User> user = Optional.of(u);
		Mockito.when(userPrincipal.getId()).thenReturn(1L);
		Mockito.when(userRepo.findById(1L)).thenReturn(user);
		ResponseEntity<?> responseEntity = userController.getCurrentUser(userPrincipal);
		Assert.assertEquals(responseEntity.getStatusCodeValue(), 200);

	}

}
