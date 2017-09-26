package com.entry.webapp.entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@SuppressWarnings("serial")
@javax.persistence.Entity
public class User implements Entity, UserDetails {
	@Id
	@GeneratedValue
	private Long id;

	@Column(length = 40, nullable = false)
	private String firstName;

	@Column(length = 40, nullable = false)
	private String lastName;

	@Column(length = 80, nullable = false)
	private String password;

	@Column(unique = true, length = 40, nullable = false)
	private String email;

	@Column(length = 15, nullable = false)
	private String phone;

	@Column(length = 40, nullable = false)
	private String country;

	@Column(unique = true, length = 40, nullable = false)
	private String nickName;

	@Column(name="avatar", nullable = true)
	private String avatar;

	@ElementCollection(fetch = FetchType.EAGER)
	private Set<Role> roles = new HashSet<Role>();

	protected User() {
	}

	public User(String firstName, String lastName, String passwordHash, String email, String phone, String country,
			String nickName, String avatar) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = passwordHash;
		this.email = email;
		this.phone = phone;
		this.country = country;
		this.nickName = nickName;
		this.avatar = avatar;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Set<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public void addRole(Role role) {
		this.roles.add(role);
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.getRoles();
	}

	@Override
	public String getUsername() {
		return this.nickName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
