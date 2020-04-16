package com.groups.groupmanager.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import com.groups.groupsmanager.controller.GroupController;
import com.groups.groupsmanager.exceptionhandling.BadRequestException;
import com.groups.groupsmanager.exceptionhandling.UserNotAuthorizedException;
import com.groups.groupsmanager.model.Group;
import com.groups.groupsmanager.repository.GroupRepository;
import com.groups.groupsmanager.userDetails.CustomUserDetailsPrincipal;

@ExtendWith(MockitoExtension.class)
public class GroupControllerTest {
	@InjectMocks
	GroupController groupController;

	@Mock
	GroupRepository groupRepo;

	@Mock
	CustomUserDetailsPrincipal userPrincipal;

	@Test
	public void getAllGroups() throws BadRequestException, UserNotAuthorizedException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		Group g = new Group(new Long(1), "India", new Long(1), "Nitika Goel", new Date());
		List<Group> groups = new ArrayList<Group>();
		groups.add(g);
		Mockito.when(userPrincipal.getEmail()).thenReturn("Nitika Goel");
		Mockito.when(groupRepo.findAll()).thenReturn(groups);
		ResponseEntity<?> responseEntity = groupController.allGroups(userPrincipal);
		Assert.assertEquals(responseEntity.getStatusCodeValue(), 200);

	}

}
