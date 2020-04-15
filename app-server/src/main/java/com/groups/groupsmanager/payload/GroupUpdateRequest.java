package com.groups.groupsmanager.payload;

public class GroupUpdateRequest {

	private String currentName;
	private String newName;
	private String memberEmail;

	public String getCurrentName() {
		return currentName;
	}

	public String getMemberEmail() {
		return memberEmail;
	}

	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}

	public void setCurrentName(String currentName) {
		this.currentName = currentName;
	}

	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}

}
