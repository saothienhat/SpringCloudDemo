package com.saothienhat.apigatewayauth.model;

public enum RoleType {
	UNKNOWN("Unknown", "ROLE_UNKNOWN"),
	USER("User", "ROLE_USER"),
	MANAGER("Manager", "ROLE_MANAGER"),
	ADMIN("Admin", "ROLE_ADMIN");
	
	private String roleName;
	private String rolePrefix;
	
	RoleType(String roleName, String rolePrefix) {
		this.roleName = roleName;
		this.rolePrefix = rolePrefix;
	}

	public String getRoleName() {
		return roleName;
	}
	
	public String getRolePrefix() {
		return this.rolePrefix;
	}
	
	public static RoleType getRoleTypeByRolePrefix(String rolePrefix) {
		RoleType role = RoleType.UNKNOWN;
		RoleType[] roles = values();
		for (RoleType roleType : roles) {
			if(roleType.getRolePrefix().equalsIgnoreCase(rolePrefix)) {
				role = roleType;
			}
		}
		return role;
	}

}
