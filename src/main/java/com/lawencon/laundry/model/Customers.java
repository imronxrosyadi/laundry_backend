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
//@JsonIgnoreProperties(allowSetters = true, value = { "hibernateLazyInitializer", "profileId" })
@Table(name = "tbl_m_customers")
public class Customers {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "cust_code", length = 6, unique = true, nullable = false)
	private String custCode;

	@Column(name = "cust_name", length = 35)
	private String custName;

	@Column(name = "cust_phone", length = 13)
	private String custPhone;

	@Column(name = "cust_address", length = 50)
	private String custAddress;

	@ManyToOne
	@JoinColumn(name = "id_profile")
	private Profiles idProfile;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCustCode() {
		return custCode;
	}

	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getCustPhone() {
		return custPhone;
	}

	public void setCustPhone(String custPhone) {
		this.custPhone = custPhone;
	}

	public String getCustAddress() {
		return custAddress;
	}

	public void setCustAddress(String custAddress) {
		this.custAddress = custAddress;
	}

	public Profiles getIdProfile() {
		return idProfile;
	}

	public void setIdProfile(Profiles idProfile) {
		this.idProfile = idProfile;
	}

}
