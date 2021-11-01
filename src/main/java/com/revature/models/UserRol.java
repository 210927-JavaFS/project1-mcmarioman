package com.revature.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_roles")
public class UserRol {
	public UserRol() {
		super();
	}
	public UserRol(int roleId, String roleName) {
		super();
		this.roleId = roleId;
		this.roleName = roleName;
	}
	@Id
	@Column(name = "role_id")
	private int roleId;
	@Column(nullable = false, name = "role_name", length = 25)
	private String roleName;
	public int getRoleId() {
		return roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
