package com.lawencon.laundry.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.laundry.model.Customers;
import com.lawencon.laundry.model.Laundries;
import com.lawencon.laundry.model.Payments;
import com.lawencon.laundry.model.Profiles;

/**
 * @author Imron Rosyadi
 */

@Repository(value = "laundry")
public class LaundriesDaoImpl extends BaseDao implements LaundriesDao {

	@Override
	public Laundries insertLaundry(Laundries laundry) throws Exception {
		String sql = bBuilder(
				"INSERT INTO tbl_r_hdr_laundries (id_customer, receipt_laundry, start_laundry, done_laundry, total_price, id_payment, id_profile)",
				" VALUES((SELECT id FROM tbl_m_customers WHERE cust_code = ?), ?, ?,",
				" ?, ?, (SELECT id FROM tbl_m_payments WHERE pay_code = ?),",
				" (SELECT id FROM tbl_m_profiles WHERE pro_code = ?)) returning id").toString();

		List<?> listResult = entityManager.createNativeQuery(sql).setParameter(1, laundry.getIdCustomer().getCustCode())
				.setParameter(2, laundry.getReceiptLaundry()).setParameter(3, laundry.getStartLaundry())
				.setParameter(4, laundry.getDoneLaundry()).setParameter(5, laundry.getTotalPrice())
				.setParameter(6, laundry.getIdPayment().getPayCode())
				.setParameter(7, laundry.getIdProfile().getProfileCode()).getResultList();
		Long idLaundry = listResult.size() > 0 ? Long.valueOf(listResult.get(0).toString()) : null;
		laundry.setId(idLaundry);
		return laundry;
	}

	@Override
	public List<Laundries> getListLaundries() throws Exception {
		List<Laundries> listLaundries = new ArrayList<>();
		String sql = bBuilder(
				"SELECT hl.receipt_laundry, c.cust_name, hl.start_laundry, hl.done_laundry, hl.total_price, p.pay_name, pf.pro_name",
				" FROM tbl_r_hdr_laundries hl INNER JOIN tbl_m_customers c on c.id = hl.id_customer",
				" INNER JOIN tbl_m_payments p on p.id = hl.id_payment",
				" INNER JOIN tbl_m_profiles pf on pf.id = hl.id_profile").toString();
		List<?> listObj = entityManager.createNativeQuery(sql).getResultList();
		listObj.forEach(val -> {
			Object[] objArr = (Object[]) val;
			Laundries l = new Laundries();
			l.setReceiptLaundry((String) objArr[0]);

			Customers c = new Customers();
			c.setCustName((String) objArr[1]);
			l.setIdCustomer(c);
			Timestamp startL = (Timestamp) objArr[2];
			Timestamp doneL = (Timestamp) objArr[3];
			l.setStartLaundry((LocalDateTime) startL.toLocalDateTime());
			l.setDoneLaundry((LocalDateTime) doneL.toLocalDateTime());
			l.setTotalPrice((BigDecimal) objArr[4]);
			Payments p = new Payments();
			p.setPayName((String) objArr[5]);
			l.setIdPayment(p);
			Profiles pf = new Profiles();
			pf.setProfileName((String) objArr[6]);
			l.setIdProfile(pf);

			listLaundries.add(l);
		});

		return listLaundries;
	}

	@Override
	public Long updateTotalPrice(Laundries laundry) throws Exception {
		String sql = bBuilder("UPDATE tbl_r_hdr_laundries SET total_price = (SELECT SUM(dl.price_dtl)",
				" FROM tbl_r_dtl_laundries dl INNER JOIN tbl_r_hdr_laundries hl ON hl.id = dl.id_laundry ",
				" WHERE hl.id = ?) WHERE id = ? RETURNING total_price").toString();
		List<?> listResult = entityManager.createNativeQuery(sql).setParameter(1, laundry.getId())
				.setParameter(2, laundry.getId()).getResultList();

		BigDecimal totPrice = listResult.size() > 0 ? new BigDecimal(listResult.get(0).toString()) : null;
		laundry.setTotalPrice(totPrice);
		return laundry.getId();
	}

	@Override
	public Laundries getLaundryByReceipt(String receipt) throws Exception {
		List<Laundries> listLaundries = new ArrayList<>();
		String sql = bBuilder(
				"SELECT hl.receipt_laundry, c.cust_name, hl.start_laundry, hl.done_laundry, hl.total_price, p.pay_name, pf.pro_name",
				" FROM tbl_r_hdr_laundries hl INNER JOIN tbl_m_customers c on c.id = hl.id_customer",
				" INNER JOIN tbl_m_payments p on p.id = hl.id_payment",
				" INNER JOIN tbl_m_profiles pf on pf.id = hl.id_profile WHERE hl.receipt_laundry = ? ").toString();
		List<?> listObj = entityManager.createNativeQuery(sql).setParameter(1, receipt).getResultList();
		listObj.forEach(val -> {
			Object[] objArr = (Object[]) val;
			Laundries l = new Laundries();
			l.setReceiptLaundry((String) objArr[0]);

			Customers c = new Customers();
			c.setCustName((String) objArr[1]);
			l.setIdCustomer(c);
			Timestamp startL = (Timestamp) objArr[2];
			Timestamp doneL = (Timestamp) objArr[3];
			l.setStartLaundry((LocalDateTime) startL.toLocalDateTime());
			l.setDoneLaundry((LocalDateTime) doneL.toLocalDateTime());
			l.setTotalPrice((BigDecimal) objArr[4]);
			Payments p = new Payments();
			p.setPayName((String) objArr[5]);
			l.setIdPayment(p);
			Profiles pf = new Profiles();
			pf.setProfileName((String) objArr[6]);
			l.setIdProfile(pf);

			listLaundries.add(l);
		});
		return listLaundries.size() > 0 ? listLaundries.get(0) : null;
	}

}
