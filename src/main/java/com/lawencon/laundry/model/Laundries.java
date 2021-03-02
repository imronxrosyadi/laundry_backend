package com.lawencon.laundry.model;

import java.math.BigDecimal;
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
@Table(name = "tbl_r_hdr_laundries")
public class Laundries {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "receipt_laundry", length = 25, unique = true, nullable = false)
	private String receiptLaundry;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	@Column(name = "start_laundry")
	private LocalDateTime startLaundry;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	@Column(name = "done_laundry")
	private LocalDateTime doneLaundry;

	@Column(name = "total_price")
	private BigDecimal totalPrice;

	@ManyToOne
	@JoinColumn(name = "id_customer")
	private Customers idCustomer;

	@ManyToOne
	@JoinColumn(name = "id_payment")
	private Payments idPayment;

	@ManyToOne
	@JoinColumn(name = "id_profile")
	private Profiles idProfile;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getReceiptLaundry() {
		return receiptLaundry;
	}

	public void setReceiptLaundry(String receiptLaundry) {
		this.receiptLaundry = receiptLaundry;
	}

	public LocalDateTime getStartLaundry() {
		return startLaundry;
	}

	public void setStartLaundry(LocalDateTime startLaundry) {
		this.startLaundry = startLaundry;
	}

	public LocalDateTime getDoneLaundry() {
		return doneLaundry;
	}

	public void setDoneLaundry(LocalDateTime doneLaundry) {
		this.doneLaundry = doneLaundry;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Customers getIdCustomer() {
		return idCustomer;
	}

	public void setIdCustomer(Customers idCustomer) {
		this.idCustomer = idCustomer;
	}

	public Payments getIdPayment() {
		return idPayment;
	}

	public void setIdPayment(Payments idPayment) {
		this.idPayment = idPayment;
	}

	public Profiles getIdProfile() {
		return idProfile;
	}

	public void setIdProfile(Profiles idProfile) {
		this.idProfile = idProfile;
	}

}
