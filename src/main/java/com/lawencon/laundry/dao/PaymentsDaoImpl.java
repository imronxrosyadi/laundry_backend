package com.lawencon.laundry.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.laundry.model.Payments;
import com.lawencon.laundry.model.Profiles;

/**
 * @author Imron Rosyadi
 */

@Repository(value = "payment")
public class PaymentsDaoImpl extends BaseDao implements PaymentsDao {

	@Override
	public List<Payments> getAll() throws Exception {
		String sql = bBuilder("SELECT p.pay_code, p.pay_name, pf.pro_name FROM tbl_m_payments p ",
				" INNER JOIN tbl_m_profiles pf ON pf.id = p.id_profile ").toString();
		List<Payments> payLists = new ArrayList<>();
		List<?> listObj = entityManager.createNativeQuery(sql).getResultList();
		listObj.forEach(val -> {
			Object[] objArr = (Object[]) val;
			Payments p = new Payments();
			p.setPayCode((String) objArr[0]);
			p.setPayName((String) objArr[1]);
			Profiles pf = new Profiles();
			pf.setProfileName((String) objArr[2]);
			p.setIdProfile(pf);

			payLists.add(p);
		});
		return payLists;
	}

	@Override
	public Payments getByCode(String code) throws Exception {
		String sql = bBuilder("SELECT p.pay_code, p.pay_name, pf.pro_name FROM tbl_m_payments p ",
				" INNER JOIN tbl_m_profiles pf ON pf.id = p.id_profile WHERE p.pay_code = ? ").toString();
		List<Payments> payLists = new ArrayList<>();
		List<?> listObj = entityManager.createNativeQuery(sql).setParameter(1, code).getResultList();
		listObj.forEach(val -> {
			Object[] objArr = (Object[]) val;
			Payments p = new Payments();
			p.setPayCode((String) objArr[0]);
			p.setPayName((String) objArr[1]);
			Profiles pf = new Profiles();
			pf.setProfileName((String) objArr[2]);
			p.setIdProfile(pf);

			payLists.add(p);
		});
		return payLists.size() > 0 ? payLists.get(0) : null;
	}

	@Override
	public void insert(Payments pay) throws Exception {
		String sql = bBuilder("INSERT INTO tbl_m_payments (pay_code, pay_name, id_profile) ",
				" VALUES (?, ?, (SELECT id FROM tbl_m_profiles WHERE pro_code = ?)) ").toString();
		entityManager.createNativeQuery(sql).setParameter(1, pay.getPayCode()).setParameter(2, pay.getPayName())
				.setParameter(3, pay.getIdProfile().getProfileCode()).executeUpdate();
	}

	@Override
	public void update(Payments pay) throws Exception {
		String sql = bBuilder("UPDATE tbl_m_payments SET pay_code = ?, pay_name = ?, id_profile = ",
				" (SELECT id FROM tbl_m_profiles WHERE pro_code = ?) WHERE id = ?").toString();
		entityManager.createNativeQuery(sql).setParameter(1, pay.getPayCode()).setParameter(2, pay.getPayName())
				.setParameter(3, pay.getIdProfile().getProfileCode()).setParameter(4, pay.getId()).executeUpdate();
	}

	@Override
	public void delete(Long id) throws Exception {
		String sql = "DELETE FROM tbl_m_payments WHERE id = ? ";
		entityManager.createNativeQuery(sql).setParameter(1, id).executeUpdate();
	}

	@Override
	public Payments getPaymentById(Long id) throws Exception {
		String sql = "SELECT * FROM tbl_m_payments WHERE id = ?1";
		entityManager.createNativeQuery(sql).setParameter(1, id);
		return null;
	}

}
