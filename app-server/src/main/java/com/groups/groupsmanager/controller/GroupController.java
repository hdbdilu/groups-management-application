package com.groups.groupsmanager.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.groups.groupsmanager.exceptionhandling.BadRequestException;
import com.groups.groupsmanager.exceptionhandling.GroupAlreadyExistsException;
import com.groups.groupsmanager.exceptionhandling.ResourceNotFoundException;
import com.groups.groupsmanager.exceptionhandling.UserNotAuthorizedException;
import com.groups.groupsmanager.metaannotations.CurrentUser;
import com.groups.groupsmanager.model.Group;
import com.groups.groupsmanager.model.GroupMembers;
import com.groups.groupsmanager.payload.GroupMembersRequest;
import com.groups.groupsmanager.payload.GroupRequest;
import com.groups.groupsmanager.payload.GroupResponse;
import com.groups.groupsmanager.payload.GroupUpdateRequest;
import com.groups.groupsmanager.payload.Member;
import com.groups.groupsmanager.repository.GroupDetailsRepository;
import com.groups.groupsmanager.repository.GroupRepository;
import com.groups.groupsmanager.repository.UserRepository;
import com.groups.groupsmanager.userDetails.CustomUserDetailsPrincipal;

@RestController

public class GroupController {

	@Autowired
	GroupRepository groupRepo;

	@Autowired
	UserRepository userRepo;

	@Autowired
	GroupDetailsRepository groupDetailsRepo;

	@PostMapping("/create")
	public ResponseEntity<?> createGroup(@Valid @RequestBody GroupRequest newGroup,
			@CurrentUser CustomUserDetailsPrincipal userPrincipal) throws BadRequestException {
		if (groupRepo.existsByGroupName(newGroup.getGroupName())) {
			throw new GroupAlreadyExistsException("Group name already taken, please given another name");
		}
		// create new group
		Group result = createNewGroup(newGroup, userPrincipal);

		// store admin as member of the group
		addMembertoGroup(result, userPrincipal);
		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("groups")
				.buildAndExpand(result.getId()).toUri();
		return ResponseEntity.created(location).body(result);

	}

	@PutMapping("/join")
	public ResponseEntity<?> joinGroup(@Valid @RequestBody GroupRequest joinGroup,
			@CurrentUser CustomUserDetailsPrincipal userPrincipal) throws BadRequestException {
		verifyGroup(joinGroup.getGroupName());
		Optional<GroupMembers> optioanlGroupDetails = groupDetailsRepo.findByGroupIdAndMemberId(
				groupRepo.findByGroupName(joinGroup.getGroupName()).get().getId(), userPrincipal.getId());
		if (optioanlGroupDetails.isPresent()) {
			throw new BadRequestException("You are already Member of this group");
		}
		GroupMembers result = addMembertoGroup(groupRepo.findByGroupName(joinGroup.getGroupName()).get(),
				userPrincipal);
		return ResponseEntity.ok().body(joinGroup);

	}

	@PostMapping("/search")
	public ResponseEntity<?> searchGroup(@Valid @RequestBody GroupRequest searchGroup,
			@CurrentUser CustomUserDetailsPrincipal userPrincipal) throws BadRequestException {
		verifyGroup(searchGroup.getGroupName());

		GroupResponse resp = new GroupResponse();
		if (groupDetailsRepo
				.findByGroupIdAndMemberId(groupRepo.findByGroupName(searchGroup.getGroupName()).get().getId(),
						userPrincipal.getId())
				.isPresent()) {
			resp.setIsJoined(true);
		}
		resp.setGroupName(searchGroup.getGroupName());
		resp.setAdminName(groupRepo.findByGroupName(searchGroup.getGroupName()).get().getAdminName());
		return ResponseEntity.ok().body(resp);

	}

	@GetMapping("/currentUserAdminGroups")
	public ResponseEntity<?> getCurrentUserGroups(@CurrentUser CustomUserDetailsPrincipal userPrincipal) {
		if (groupRepo.findByAdminId(userPrincipal.getId()).isPresent()) {
			List<GroupMembersRequest> currentUserGroups = new ArrayList<GroupMembersRequest>();
			for (Group g : groupRepo.findByAdminId(userPrincipal.getId()).get()) {
				List<GroupMembers> groupDetails = groupDetailsRepo.findByGroupId(g.getId()).get();
				GroupMembersRequest groupMembersInfo = new GroupMembersRequest();
				groupMembersInfo.setMembersCount(groupDetails.size());
				groupMembersInfo.setGroupName(g.getGroupName());
				groupMembersInfo.setGroupCreationTime(g.getCreationTime());
				groupMembersInfo.setAdminId(g.getAdminName());
				currentUserGroups.add(groupMembersInfo);
			}
			return ResponseEntity.ok().body(currentUserGroups);
		}

		return ResponseEntity.ok().body(new String("No groups created yet"));

	}

	@PutMapping("/changeGroupName")
	public ResponseEntity<?> changeGroupName(@CurrentUser CustomUserDetailsPrincipal userPrincipal,
			@Valid @RequestBody GroupUpdateRequest groupupdateRequest)
			throws BadRequestException, UserNotAuthorizedException {
		verifyGroup(groupupdateRequest.getCurrentName());
		if (groupRepo.findByGroupName(groupupdateRequest.getNewName()).isPresent()) {
			throw new BadRequestException("Group " + groupupdateRequest.getNewName() + " already  exists");
		}
		verifyCurrentUserAuthority(userPrincipal, groupupdateRequest, "change group name");
		Group g = groupRepo.findByGroupName(groupupdateRequest.getCurrentName()).get();
		g.setGroupName(groupupdateRequest.getNewName());
		groupRepo.save(g);
		return ResponseEntity.ok().body(groupupdateRequest);

	}

	@DeleteMapping("/removeMember")
	public ResponseEntity<?> removeMember(@CurrentUser CustomUserDetailsPrincipal userPrincipal,
			@Valid @RequestBody GroupUpdateRequest groupupdateRequest)
			throws BadRequestException, UserNotAuthorizedException {
		verifyGroup(groupupdateRequest.getCurrentName());
		if (!userRepo.existsByEmail(groupupdateRequest.getMemberEmail())) {
			throw new ResourceNotFoundException(
					"Member " + groupupdateRequest.getMemberEmail() + " does not exits in our records.");
		}
		verifyCurrentUserAuthority(userPrincipal, groupupdateRequest, "remove member");
		if (userPrincipal.getEmail().equalsIgnoreCase(groupupdateRequest.getMemberEmail())) {
			throw new BadRequestException("Admin of the group can not be removed from group.");
		}
		Optional<GroupMembers> groupMembers = groupDetailsRepo.findByGroupIdAndMemberId(
				groupRepo.findByGroupName(groupupdateRequest.getCurrentName()).get().getId(),
				userRepo.findByEmail(groupupdateRequest.getMemberEmail()).get().getId());
		if (!groupMembers.isPresent()) {
			throw new ResourceNotFoundException(
					"This user is not present in group " + groupupdateRequest.getCurrentName());
		}
		groupDetailsRepo.delete(groupMembers.get());
		return ResponseEntity.ok().body(groupupdateRequest);
	}

	@PostMapping("/members")
	public ResponseEntity<?> getMembers(@CurrentUser CustomUserDetailsPrincipal userPrincipal,
			@Valid @RequestBody GroupRequest group) throws BadRequestException, UserNotAuthorizedException {
		verifyGroup(group.getGroupName());
		List<GroupMembers> groupMembers = groupDetailsRepo
				.findByGroupId(groupRepo.findByGroupName(group.getGroupName()).get().getId()).get();
		List<Member> members = groupMembers.stream()
				.map(groupMember -> userRepo.findById(groupMember.getMemberId()).get())
				.map(user -> new Member(user.getName(), user.getEmail())).collect(Collectors.toList());
		return ResponseEntity.ok().body(members);

	}

	@GetMapping("/allGroups")
	public ResponseEntity<?> allGroups(@CurrentUser CustomUserDetailsPrincipal userPrincipal)
			throws BadRequestException, UserNotAuthorizedException {
		List<Group> allGroups = groupRepo.findAll();
		return ResponseEntity.ok().body(allGroups);

	}

	@GetMapping("/joinedGroups")
	public ResponseEntity<?> joinedGroups(@CurrentUser CustomUserDetailsPrincipal userPrincipal)
			throws BadRequestException, UserNotAuthorizedException {
		if (groupDetailsRepo.findByMemberId(userPrincipal.getId()).isPresent()) {
			List<GroupMembers> groups = groupDetailsRepo.findByMemberId(userPrincipal.getId()).get();
			List<GroupResponse> groupResponse = groups.stream()
					.map(group -> groupRepo.findById(group.getGroupId()).get())
					.map(groupentity -> new GroupResponse(groupentity.getGroupName(), groupentity.getAdminName(),
							Boolean.TRUE))
					.collect(Collectors.toList());
			return ResponseEntity.ok().body(groupResponse);
		}
		throw new ResourceNotFoundException("You have not joined any groups");

	}

	private Group createNewGroup(GroupRequest newGroup, CustomUserDetailsPrincipal userPrincipal) {
		Group group = new Group();
		group.setGroupName(newGroup.getGroupName());
		group.setAdminName(userPrincipal.getEmail());
		group.setAdminId(userPrincipal.getId());
		group.setCreationTime(new Date());
		Group result = groupRepo.save(group);
		return result;
	}

	private GroupMembers addMembertoGroup(Group group, CustomUserDetailsPrincipal userPrincipal) {
		GroupMembers groupDetails = new GroupMembers();
		groupDetails.setGroupId(group.getId());
		groupDetails.setMemberId(userPrincipal.getId());
		return groupDetailsRepo.save(groupDetails);
	}

	private void verifyGroup(String groupName) {
		if (!groupRepo.findByGroupName(groupName).isPresent()) {
			throw new ResourceNotFoundException("Group " + groupName + " does not exist.");
		}
	}

	private void verifyCurrentUserAuthority(CustomUserDetailsPrincipal userPrincipal,
			GroupUpdateRequest groupupdateRequest, String action) throws UserNotAuthorizedException {
		if (!groupRepo.findByGroupName(groupupdateRequest.getCurrentName()).get().getAdminName()
				.equalsIgnoreCase(userPrincipal.getEmail())) {
			throw new UserNotAuthorizedException("Only admin of the group can perform " + action + " action");
		}
	}

}
