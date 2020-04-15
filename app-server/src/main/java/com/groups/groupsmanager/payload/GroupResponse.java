package com.groups.groupsmanager.payload;

public class GroupResponse {

	private String groupName;
	private String adminName;
	private Boolean isJoined = false;

	public GroupResponse(String groupName, String adminName, Boolean isJoined) {
		super();
		this.groupName = groupName;
		this.adminName = adminName;
		this.isJoined = isJoined;
	}

	public GroupResponse() {

	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public Boolean getIsJoined() {
		return isJoined;
	}

	public void setIsJoined(Boolean isJoined) {
		this.isJoined = isJoined;
	}

}
