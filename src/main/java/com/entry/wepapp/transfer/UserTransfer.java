package com.entry.wepapp.transfer;

import java.util.Map;

public class UserTransfer {

	private final Long id;
	private final String name;
	private final String avatar;

	private final Map<String, Boolean> roles;

	public UserTransfer(Long id, String userName, String avatar, Map<String, Boolean> roles) {
		this.id = id;
		this.name = userName;
		this.avatar = avatar;
		this.roles = roles;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getName() {
		return this.name;
	}

	public String getAvatar() {
		return avatar;
	}

	public Map<String, Boolean> getRoles() {
		return this.roles;
	}
}