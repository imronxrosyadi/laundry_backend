package com.lawencon.laundry.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.laundry.model.Customers;
import com.lawencon.laundry.model.Laundries;
import com.lawencon.laundry.model.LaundryDetails;
import com.lawencon.laundry.model.Payments;
import com.lawencon.laundry.model.Perfumes;
import com.lawencon.laundry.model.Profiles;
import com.lawencon.laundry.model.Services;
import com.lawencon.laundry.model.Statuses;

/**
 * @author Imron Rosyadi
 */

@Repository(value = "laundrydetail")
public class LaundryDetailsDaoImpl extends BaseDao implements LaundryDetailsDao {

	@Override
	public LaundryDetails insertLaundryDtl(Laundries laundry, LaundryDetails laundryDtl) throws Exception {
		String sql = bBuilder(
				"INSERT INTO tbl_r_dtl_laundries (id_laundry, code_dtl, desc_dtl, unit_dtl, price_dtl, id_service, id_perfume, id_status)",
				"	VALUES(?, ?, ?, ?, ?, (SELECT id FROM tbl_m_services WHERE service_code = ?),",
				"		(SELECT id FROM tbl_m_perfumes WHERE perfume_code = ?), (SELECT id FROM tbl_m_statuses WHERE stat_code = ?)) RETURNING id")
						.toString();

		List<?> ldrDtlList = entityManager.createNativeQuery(sql).setParameter(1, laundry.getId())
				.setParameter(2, laundryDtl.getCodeDtl()).setParameter(3, laundryDtl.getDescDtl())
				.setParameter(4, laundryDtl.getUnitDtl()).setParameter(5, laundryDtl.getPriceDtl())
				.setParameter(6, laundryDtl.getIdService().getServiceCode())
				.setParameter(7, laundryDtl.getIdPerfume().getPerfumeCode())
				.setParameter(8, laundryDtl.getIdStatus().getStatusCode()).getResultList();

		Long idLaundry = ldrDtlList.size() > 0 ? Long.valueOf(ldrDtlList.get(0).toString()) : null;
		laundryDtl.setId(idLaundry);
		return laundryDtl;
	}

	@Override
	public List<LaundryDetails> getListLaundryDetails() throws Exception {
		List<LaundryDetails> ldrDtlList = new ArrayList<>();
		String sql = bBuilder(
				"SELECT dl.code_dtl, dl.desc_dtl, dl.unit_dtl, dl.price_dtl, s.service_name, s.service_price, p.perfume_name, st.stat_name",
				" FROM tbl_r_dtl_laundries dl INNER JOIN tbl_m_services s on s.id = dl.id_service",
				" INNER JOIN tbl_m_perfumes p on p.id = dl.id_perfume",
				" INNER JOIN tbl_m_statuses st on st.id = dl.id_status").toString();
		List<?> listObj = entityManager.createNativeQuery(sql).getResultList();
		listObj.forEach(val -> {
			Object[] objArr = (Object[]) val;
			LaundryDetails ld = new LaundryDetails();
			ld.setCodeDtl((String) objArr[0]);
			ld.setDescDtl((String) objArr[1]);
			ld.setUnitDtl((Integer) objArr[2]);
			ld.setPriceDtl((BigDecimal) objArr[3]);

			Services s = new Services();
			s.setServiceName((String) objArr[4]);
			s.setServicePrice((BigDecimal) objArr[5]);
			ld.setIdService(s);

			Perfumes p = new Perfumes();
			p.setPerfumeName((String) objArr[6]);
			ld.setIdPerfume(p);

			Statuses st = new Statuses();
			st.setStatusName((String) objArr[7]);
			ld.setIdStatus(st);

			ldrDtlList.add(ld);
		});
		return ldrDtlList;
	}

	@Override
	public BigDecimal updatePrice(LaundryDetails ldrDtl) throws Exception {
		String sql = bBuilder("UPDATE tbl_r_dtl_laundries",
				" SET price_dtl = (SELECT (dl.unit_dtl * s.service_price) FROM tbl_r_dtl_laundries dl",
				" INNER JOIN tbl_m_services s on s.id = dl.id_service WHERE dl.id = ?) WHERE id = ? RETURNING price_dtl")
						.toString();
		List<?> listResult = entityManager.createNativeQuery(sql).setParameter(1, ldrDtl.getId())
				.setParameter(2, ldrDtl.getId()).getResultList();

		BigDecimal price = listResult.size() > 0 ? new BigDecimal(listResult.get(0).toString()) : null;
		ldrDtl.setPriceDtl(price);
		return ldrDtl.getPriceDtl();
	}

	@Override
	public LaundryDetails getLaundryDetailByCode(String code) throws Exception {
		List<LaundryDetails> ldrDtlList = new ArrayList<>();
		String sql = bBuilder(
				"SELECT dl.code_dtl, dl.desc_dtl, dl.unit_dtl, dl.price_dtl, s.service_name, s.service_price, p.perfume_name, st.stat_name",
				" FROM tbl_r_dtl_laundries dl INNER JOIN tbl_m_services s on s.id = dl.id_service",
				" INNER JOIN tbl_m_perfumes p on p.id = dl.id_perfume",
				" INNER JOIN tbl_m_statuses st on st.id = dl.id_status WHERE dl.code_dtl = ? ").toString();
		List<?> listObj = entityManager.createNativeQuery(sql).setParameter(1, code).getResultList();
		listObj.forEach(val -> {
			Object[] objArr = (Object[]) val;
			LaundryDetails ld = new LaundryDetails();
			ld.setCodeDtl((String) objArr[0]);
			ld.setDescDtl((String) objArr[1]);
			ld.setUnitDtl((Integer) objArr[2]);
			ld.setPriceDtl((BigDecimal) objArr[3]);

			Services s = new Services();
			s.setServiceName((String) objArr[4]);
			s.setServicePrice((BigDecimal) objArr[5]);
			ld.setIdService(s);

			Perfumes p = new Perfumes();
			p.setPerfumeName((String) objArr[6]);
			ld.setIdPerfume(p);

			Statuses st = new Statuses();
			st.setStatusName((String) objArr[7]);
			ld.setIdStatus(st);

			ldrDtlList.add(ld);
		});
		return ldrDtlList.size() > 0 ? ldrDtlList.get(0) : null;
	}

	@Override
	public List<LaundryDetails> getListLaundryDtlByCode(String code) throws Exception {
		List<LaundryDetails> laundryDtlList = new ArrayList<>();
		String sql = bBuilder(
				"SELECT dl.code_dtl, dl.price_dtl, hl.receipt_laundry, c.cust_name, hl.start_laundry, hl.done_laundry, hl.total_price, ",
				" p.pay_name, pf.pro_name, pfm.perfume_name, s.service_name, st.stat_name FROM tbl_r_hdr_laundries hl ",
				" INNER JOIN tbl_m_customers c on c.id = hl.id_customer INNER JOIN tbl_m_payments p on p.id = hl.id_payment",
				" INNER JOIN tbl_m_profiles pf on pf.id = hl.id_profile INNER JOIN tbl_r_dtl_laundries dl on dl.id_laundry = hl.id",
				" INNER JOIN tbl_m_perfumes pfm on pfm.id = dl.id_perfume INNER JOIN tbl_m_services s on s.id = dl.id_service",
				" INNER JOIN tbl_m_statuses st on st.id = dl.id_status WHERE pf.pro_code = ? ").toString();
		List<?> listObj = entityManager.createNativeQuery(sql).setParameter(1, code).getResultList();
		listObj.forEach(val -> {
			Object[] objArr = (Object[]) val;
			LaundryDetails ld = new LaundryDetails();
			ld.setCodeDtl((String) objArr[0]);
			ld.setPriceDtl((BigDecimal) objArr[1]);

			Laundries l = new Laundries();
			l.setReceiptLaundry((String) objArr[2]);

			Customers c = new Customers();
			c.setCustName((String) objArr[3]);
			l.setIdCustomer(c);
			Timestamp startL = (Timestamp) objArr[4];
			Timestamp doneL = (Timestamp) objArr[5];
			l.setStartLaundry((LocalDateTime) startL.toLocalDateTime());
			l.setDoneLaundry((LocalDateTime) doneL.toLocalDateTime());
			l.setTotalPrice((BigDecimal) objArr[6]);
			Payments p = new Payments();
			p.setPayName((String) objArr[7]);
			l.setIdPayment(p);
			Profiles pf = new Profiles();
			pf.setProfileName((String) objArr[8]);
			l.setIdProfile(pf);

			Perfumes pfm = new Perfumes();
			pfm.setPerfumeName((String) objArr[9]);
			ld.setIdPerfume(pfm);

			Services serv = new Services();
			serv.setServiceName((String) objArr[10]);
			ld.setIdService(serv);

			Statuses stat = new Statuses();
			stat.setStatusName((String) objArr[11]);
			ld.setIdStatus(stat);
			ld.setIdLaundry(l);

			laundryDtlList.add(ld);
		});

		return laundryDtlList;
	}

}
