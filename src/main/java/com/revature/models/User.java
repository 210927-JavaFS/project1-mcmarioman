package com.revature.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NaturalId;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
@Table(name = "Users")
public class User {
	public User() {
		super();
	}
	public User(int userId, String userName, String userPassword, String firstName, String last_name, String email,
			UserRol rol) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.userPassword = userPassword;
		this.firstName = firstName;
		this.last_name = last_name;
		this.email = email;
		this.rol = rol;
	}
	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;
	@NaturalId
	@Column(name = "user_name", length = 25, unique = true, nullable = false)
	private String userName;
	@Column(name = "user_password", length = 64, nullable = false)
	@JsonIgnore
	private String userPassword;
	@Column(name = "first_name", length = 100, nullable = false)
	private String firstName;
	@Column(name = "last_name", length = 100, nullable = false)
	private String last_name;
	@Column(length = 150, nullable = false)
	private String email;
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name="role_id", nullable=false)
	private UserRol rol;
	public int getUserId() {
		return userId;
	}
	public String getUserName() {
		return userName;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLast_name() {
		return last_name;
	}
	public String getEmail() {
		return email;
	}
	public UserRol getRol() {
		return rol;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setRol(UserRol rol) {
		this.rol = rol;
	}
}
