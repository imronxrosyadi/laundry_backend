package com.lawencon.laundry.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lawencon.laundry.model.Payments;
import com.lawencon.laundry.model.Profiles;
import com.lawencon.laundry.repo.PaymentsRepo;

/**
 * @author Imron Rosyadi
 */

@Repository(value = "paymentNative")
public class PaymentsDaoJpaNativeImpl extends BaseDao implements PaymentsDao {

	@Autowired
	PaymentsRepo paymentsRepo;

	@Override
	public List<Payments> getAll() throws Exception {
		List<Payments> payLists = new ArrayList<>();
		List<Object[]> listObj = paymentsRepo.getAll();
		listObj.forEach(objArr -> {
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
		return paymentsRepo.findByCode(code);
	}

	@Override
	public void insert(Payments pay) throws Exception {
		paymentsRepo.save(pay);
	}

	@Override
	public void update(Payments pay) throws Exception {
		paymentsRepo.update(pay.getPayCode(), pay.getPayName(), pay.getIdProfile().getProfileCode(), pay.getId());
	}

	@Override
	public void delete(Long id) throws Exception {
		paymentsRepo.delete(id);
	}

	@Override
	public Payments getPaymentById(Long id) throws Exception {
		return paymentsRepo.getPaymentById(id);
	}

}
