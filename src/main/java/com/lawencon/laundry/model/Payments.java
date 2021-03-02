package com.lawencon.laundry.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author Imron Rosyadi
 */

@Entity
@JsonInclude(Include.NON_NULL)
@Table(name = "tbl_m_payments")
public class Payments {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "pay_code", length = 6, unique = true, nullable = false)
	private String payCode;

	@Column(name = "pay_name", length = 20)
	private String payName;

	@ManyToOne
	@JoinColumn(name = "id_profile")
	private Profiles idProfile;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPayCode() {
		return payCode;
	}

	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}

	public String getPayName() {
		return payName;
	}

	public void setPayName(String payName) {
		this.payName = payName;
	}

	public Profiles getIdProfile() {
		return idProfile;
	}

	public void setIdProfile(Profiles idProfile) {
		this.idProfile = idProfile;
	}
}
