package com.lawencon.laundry.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author Imron Rosyadi
 */

@Entity
@JsonInclude(Include.NON_NULL)
@Table(name = "tbl_m_profiles")
public class Profiles {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "pro_code", length = 6, unique = true, nullable = false)
	private String profileCode;

	@Column(name = "pro_name", length = 35)
	private String profileName;

	@Column(name = "pro_phone", length = 13)
	private String profilePhone;

	@Column(name = "pro_address", length = 50)
	private String profileAddress;

	@OneToOne
	@JoinColumn(name = "id_user")
	private Users idUser;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProfileCode() {
		return profileCode;
	}

	public void setProfileCode(String profileCode) {
		this.profileCode = profileCode;
	}

	public String getProfileName() {
		return profileName;
	}

	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	public String getProfilePhone() {
		return profilePhone;
	}

	public void setProfilePhone(String profilePhone) {
		this.profilePhone = profilePhone;
	}

	public String getProfileAddress() {
		return profileAddress;
	}

	public void setProfileAddress(String profileAddress) {
		this.profileAddress = profileAddress;
	}

	public Users getIdUser() {
		return idUser;
	}

	public void setIdUser(Users idUser) {
		this.idUser = idUser;
	}
}
