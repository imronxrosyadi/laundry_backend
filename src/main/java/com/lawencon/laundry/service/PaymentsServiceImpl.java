package com.lawencon.laundry.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawencon.laundry.dao.PaymentsDao;
import com.lawencon.laundry.model.Payments;
import com.lawencon.laundry.model.Profiles;

/**
 * @author Imron Rosyadi
 */

@Service
@Transactional
public class PaymentsServiceImpl implements PaymentsService {

	private PaymentsDao paymentsDao;
	private ProfilesService profilesService;

	@Autowired
	public void setProfilesService(ProfilesService profilesService) {
		this.profilesService = profilesService;
	}

	@Autowired
	@Qualifier(value = "paymentNative")
	public void setPaymentsDao(PaymentsDao paymentsDao) {
		this.paymentsDao = paymentsDao;
	}

	@Override
	public List<Payments> getAll() throws Exception {
		return paymentsDao.getAll();
	}

	@Override
	public void insert(Payments pay) throws Exception {
		validateInsertPayments(pay);
		Profiles pro = profilesService.findByCode(pay.getIdProfile().getProfileCode());
		pay.setIdProfile(pro);
		paymentsDao.insert(pay);
	}

	@Override
	public void update(Payments pay) throws Exception {
		validateUpdatePayments(pay);
		paymentsDao.update(pay);
	}

	@Override
	public void delete(Long id) throws Exception {
		Payments pay = paymentsDao.getPaymentById(id);
		if (pay == null) {
			throw new Exception("Id is doesnt exist!");
		}
		paymentsDao.delete(id);
	}

	@Override
	public Payments getByCode(String code) throws Exception {
		return paymentsDao.getByCode(code);
	}

	void validateInsertPayments(Payments pay) throws Exception {
		if (pay.getId() != null) {
			throw new Exception("Invalid input, column id must be empty!");
		} else if (pay.getPayCode() == null || pay.getPayCode().trim().equals("")) {
			throw new Exception("Invalid input, column payment code cant be empty!");
		} else if (pay.getPayName() == null || pay.getPayName().trim().equals("")) {
			throw new Exception("Invalid input, column payment name cant be empty!");
		} else if (pay.getIdProfile().getProfileCode() == null
				|| pay.getIdProfile().getProfileCode().trim().equals("")) {
			throw new Exception("Invalid input, column profile code cant be empty!");
		}
		Profiles pro = profilesService.findByCode(pay.getIdProfile().getProfileCode());
		if (pro == null) {
			throw new Exception("Profile code is doesnt exist!");
		}
	}

	void validateUpdatePayments(Payments pay) throws Exception {
		if (pay.getId() == null) {
			throw new Exception("Invalid input, column id cant be empty!");
		} else if (pay.getPayCode() == null || pay.getPayCode().trim().equals("")) {
			throw new Exception("Invalid input, column payment code cant be empty!");
		} else if (pay.getPayName() == null || pay.getPayName().trim().equals("")) {
			throw new Exception("Invalid input, column payment name cant be empty!");
		} else if (pay.getIdProfile().getProfileCode() == null
				|| pay.getIdProfile().getProfileCode().trim().equals("")) {
			throw new Exception("Invalid input, column profile code cant be empty!");
		}
		Profiles pro = profilesService.findByCode(pay.getIdProfile().getProfileCode());
		if (pro == null) {
			throw new Exception("Profile code is doesnt exist!");
		}
	}

}
