package com.lawencon.laundry.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author Imron Rosyadi
 */

@Entity
@JsonInclude(Include.NON_NULL)
@Table(name = "tbl_m_users")
public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "uname", length = 25, unique = true, nullable = false)
	private String username;

	@JsonIgnoreProperties
	@Column(name = "pass", nullable = false)
	private String password;

	@ManyToOne
	@JoinColumn(name = "id_role")
	private Roles idRole;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Roles getIdRole() {
		return idRole;
	}

	public void setIdRole(Roles idRole) {
		this.idRole = idRole;
	}
}
