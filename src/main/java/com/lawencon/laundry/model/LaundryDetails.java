package com.lawencon.laundry.model;

import java.math.BigDecimal;

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
@Table(name = "tbl_r_dtl_laundries")
public class LaundryDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "code_dtl", length = 6, unique = true, nullable = false)
	private String codeDtl;

	@Column(name = "desc_dtl", length = 50)
	private String descDtl;

	@Column(name = "unit_dtl")
	private Integer unitDtl;

	@Column(name = "price_dtl")
	private BigDecimal priceDtl;

	@ManyToOne
	@JoinColumn(name = "id_laundry")
	private Laundries idLaundry;

	@ManyToOne
	@JoinColumn(name = "id_perfume")
	private Perfumes idPerfume;

	@ManyToOne
	@JoinColumn(name = "id_service")
	private Services idService;

	@ManyToOne
	@JoinColumn(name = "id_status")
	private Statuses idStatus;

	public String getCodeDtl() {
		return codeDtl;
	}

	public void setCodeDtl(String codeDtl) {
		this.codeDtl = codeDtl;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescDtl() {
		return descDtl;
	}

	public void setDescDtl(String descDtl) {
		this.descDtl = descDtl;
	}

	public Integer getUnitDtl() {
		return unitDtl;
	}

	public void setUnitDtl(Integer unitDtl) {
		this.unitDtl = unitDtl;
	}

	public BigDecimal getPriceDtl() {
		return priceDtl;
	}

	public void setPriceDtl(BigDecimal priceDtl) {
		this.priceDtl = priceDtl;
	}

	public Laundries getIdLaundry() {
		return idLaundry;
	}

	public void setIdLaundry(Laundries idLaundry) {
		this.idLaundry = idLaundry;
	}

	public Perfumes getIdPerfume() {
		return idPerfume;
	}

	public void setIdPerfume(Perfumes idPerfume) {
		this.idPerfume = idPerfume;
	}

	public Services getIdService() {
		return idService;
	}

	public void setIdService(Services idService) {
		this.idService = idService;
	}

	public Statuses getIdStatus() {
		return idStatus;
	}

	public void setIdStatus(Statuses idStatus) {
		this.idStatus = idStatus;
	}

}
