package com.lawencon.laundry.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawencon.laundry.dao.LaundriesDao;
import com.lawencon.laundry.model.Customers;
import com.lawencon.laundry.model.Laundries;
import com.lawencon.laundry.model.LaundryDetails;
import com.lawencon.laundry.model.Payments;
import com.lawencon.laundry.model.Profiles;

/**
 * @author Imron Rosyadi
 */

@Service
@Transactional
public class LaundriesServiceImpl extends BaseService implements LaundriesService {

	private LaundriesDao laundriesDao;
	private LaundryDetailsService laundryDetailsService;
	private CustomersService customersService;
	private PaymentsService paymentsService;
	private ProfilesService profilesService;

	@Autowired
	public void setProfilesService(ProfilesService profilesService) {
		this.profilesService = profilesService;
	}

	@Autowired
	public void setCustomersService(CustomersService customersService) {
		this.customersService = customersService;
	}

	@Autowired
	public void setPaymentsService(PaymentsService paymentsService) {
		this.paymentsService = paymentsService;
	}

	@Autowired
	@Qualifier(value = "laundryNative")
	public void setLaundriesDao(LaundriesDao laundriesDao) {
		this.laundriesDao = laundriesDao;
	}

	@Autowired
	public void setLaundryDetailsService(LaundryDetailsService laundryDetailsService) {
		this.laundryDetailsService = laundryDetailsService;
	}

	@Override
	public Laundries insertLaundry(Laundries laundry, List<LaundryDetails> ldrDtlList) throws Exception {
		validateInsertLaundry(laundry);
		Customers cust = customersService.findByCode(laundry.getIdCustomer().getCustCode());
		Payments pay = paymentsService.getByCode(laundry.getIdPayment().getPayCode());
		Profiles pro = profilesService.findByCode(laundry.getIdProfile().getProfileCode());
		laundry.setIdCustomer(cust);
		laundry.setIdPayment(pay);
		laundry.setIdProfile(pro);
		laundry.setReceiptLaundry(generateReceipt());
		Laundries newLaundries = laundriesDao.insertLaundry(laundry);
		for (LaundryDetails laundryDetails : ldrDtlList) {
			laundryDetailsService.insertLaundryDtl(newLaundries, laundryDetails);
		}
		updateTotalPrice(laundry);
		return laundry;
	}

	@Override
	public List<Laundries> getListLaundries() throws Exception {
		return laundriesDao.getListLaundries();
	}

	@Override
	public Laundries getLaundryByReceipt(String receipt) throws Exception {
		return laundriesDao.getLaundryByReceipt(receipt);
	}

	@Override
	public Long updateTotalPrice(Laundries laundry) throws Exception {
		return laundriesDao.updateTotalPrice(laundry);
	}

	@Override
	public String generateReceipt() throws Exception {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		String date = LocalDateTime.now().format(formatter);
		String[] dateSplited = date.split(" ");
		String[] timeSplited = dateSplited[1].split(":");
		String a = timeSplited[0];
		String b = timeSplited[1];
		Random rand = new Random();
		int randomNum = rand.nextInt((999 - 001) + 1) + 001;
		StringBuilder receipt = new StringBuilder();
		receipt.append("LNDRY-").append(a).append(b).append("-").append(randomNum);
		return receipt.toString();
	}

	private void validateInsertLaundry(Laundries laundry) throws Exception {
		if (laundry.getId() != null) {
			throw new Exception("Invalid input, column id must be empty!");
		} else if (laundry.getStartLaundry() == null) {
			throw new Exception("Invalid input, column start laundry cant be empty!");
		} else if (laundry.getDoneLaundry() == null) {
			throw new Exception("Invalid input, column done laundry cant be empty!");
		} else if (laundry.getIdCustomer() == null) {
			throw new Exception("Invalid input, column id customer cant be empty!");
		} else if (laundry.getIdCustomer().getCustCode() == null) {
			throw new Exception("Invalid input, column customer code cant be empty!");
		} else if (laundry.getIdPayment() == null) {
			throw new Exception("Invalid input, column id payment cant be empty!");
		} else if (laundry.getIdPayment().getPayCode() == null) {
			throw new Exception("Invalid input, column payment code cant be empty!");
		} else if (laundry.getIdProfile() == null) {
			throw new Exception("Invalid input, column id profile cant be empty!");
		} else if (laundry.getIdProfile().getProfileCode() == null) {
			throw new Exception("Invalid input, column profile code cant be empty!");
		}
		Customers cust = customersService.findByCode(laundry.getIdCustomer().getCustCode());
		Payments pay = paymentsService.getByCode(laundry.getIdPayment().getPayCode());
		Profiles pro = profilesService.findByCode(laundry.getIdProfile().getProfileCode());
		if (cust == null) {
			throw new Exception("Customer code is doesnt exist!");
		} else if (pay == null) {
			throw new Exception("Payment code is doesnt exist!");
		} else if (pro == null) {
			throw new Exception("Profile code is doesnt exist!");
		}
	}

}
