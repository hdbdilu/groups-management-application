package com.groups.groupsmanager.payload;

public class RegistrationResponse {

	private String successMessage;
	private Boolean isRegistered;

	public String getSuccessMessage() {
		return successMessage;
	}

	public void setSuccessMessage(String successMessage) {
		this.successMessage = successMessage;
	}

	public Boolean getIsRegistered() {
		return isRegistered;
	}

	public void setIsRegistered(Boolean isRegistered) {
		this.isRegistered = isRegistered;
	}

	public RegistrationResponse(String successMessage, Boolean isRegistered) {
		super();
		this.successMessage = successMessage;
		this.isRegistered = isRegistered;
	}

}
