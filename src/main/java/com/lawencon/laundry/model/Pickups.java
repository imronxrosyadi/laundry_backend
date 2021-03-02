package com.lawencon.laundry.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author Imron Rosyadi
 */

@Entity
@JsonInclude(Include.NON_NULL)
@Table(name = "tbl_r_pickups")
public class Pickups {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	@Column(name = "pickup_date")
	private LocalDateTime pickupDate;

	@ManyToOne
	@JoinColumn(name = "id_dtl_laundry")
	private LaundryDetails idLaundryDtl;

	@ManyToOne
	@JoinColumn(name = "id_profile")
	private Profiles idProfile;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getPickupDate() {
		return pickupDate;
	}

	public void setPickupDate(LocalDateTime pickupDate) {
		this.pickupDate = pickupDate;
	}

	public LaundryDetails getIdLaundryDtl() {
		return idLaundryDtl;
	}

	public void setIdLaundryDtl(LaundryDetails idLaundryDtl) {
		this.idLaundryDtl = idLaundryDtl;
	}

	public Profiles getIdProfile() {
		return idProfile;
	}

	public void setIdProfile(Profiles idProfile) {
		this.idProfile = idProfile;
	}

}
