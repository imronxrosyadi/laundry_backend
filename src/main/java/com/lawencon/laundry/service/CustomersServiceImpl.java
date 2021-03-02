package com.lawencon.laundry.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawencon.laundry.dao.CustomersDao;
import com.lawencon.laundry.model.Customers;
import com.lawencon.laundry.model.Profiles;

/**
 * @author Imron Rosyadi
 */

@Service
@Transactional
public class CustomersServiceImpl extends BaseService implements CustomersService {

	private CustomersDao customersDao;
	private ProfilesService profilesService;

	@Autowired
	public void setProfilesService(ProfilesService profilesService) {
		this.profilesService = profilesService;
	}

	@Autowired
	@Qualifier(value = "custNative")
	public void setCustomersDao(CustomersDao customersDao) {
		this.customersDao = customersDao;
	}

	@Override
	public Customers insertCustomer(Customers cust) throws Exception {
		validateInsertCustomer(cust);
		Profiles pro = profilesService.findByCode(cust.getIdProfile().getProfileCode());
		cust.setIdProfile(pro);
		return customersDao.insertCustomer(cust);
	}

	@Override
	public List<Customers> getListCustomers() throws Exception {
		return customersDao.getListCustomers();
	}

	@Override
	public Customers findByCode(String code) throws Exception {
		return customersDao.findByCode(code);
	}

	@Override
	public void update(Customers cust) throws Exception {
		validateUpdateCustomer(cust);
		customersDao.update(cust);
	}

	@Override
	public void delete(Long id) throws Exception {
		Customers cust = customersDao.getCustomerById(id);
		if (cust == null) {
			throw new Exception("Id is doesnt exist");
		}
		customersDao.delete(id);
	}

	private void validateInsertCustomer(Customers cust) throws Exception {
		if (cust.getId() != null) {
			throw new Exception("Invalid input, column id must be empty!");
		} else if (cust.getCustCode() == null || cust.getCustCode().trim().equals("")) {
			throw new Exception("Invalid input, column customer code cant be empty!");
		} else if (cust.getCustName() == null || cust.getCustName().trim().equals("")) {
			throw new Exception("Invalid input, column customer name cant be empty!");
		} else if (cust.getCustPhone() == null || cust.getCustPhone().trim().equals("")) {
			throw new Exception("Invalid input, column customer phone cant be empty!");
		} else if (cust.getCustAddress() == null || cust.getCustAddress().trim().equals("")) {
			throw new Exception("Invalid input, column customer address cant be empty!");
		} else if (cust.getIdProfile().getProfileCode() == null
				|| cust.getIdProfile().getProfileCode().trim().equals("")) {
			throw new Exception("Invalid input, column profile code cant be empty!");
		}
		Profiles pro = profilesService.findByCode(cust.getIdProfile().getProfileCode());
		if (pro == null) {
			throw new Exception("Profile code is doesnt exist!");
		}
	}

	private void validateUpdateCustomer(Customers cust) throws Exception {
		if (cust.getId() == null) {
			throw new Exception("Invalid input, column id cant be empty!");
		} else if (cust.getCustCode() == null || cust.getCustCode().trim().equals("")) {
			throw new Exception("Invalid input, column customer code cant be empty!");
		} else if (cust.getCustName() == null || cust.getCustName().trim().equals("")) {
			throw new Exception("Invalid input, column customer name cant be empty!");
		} else if (cust.getCustPhone() == null || cust.getCustPhone().trim().equals("")) {
			throw new Exception("Invalid input, column customer phone cant be empty!");
		} else if (cust.getCustAddress() == null || cust.getCustAddress().trim().equals("")) {
			throw new Exception("Invalid input, column customer address cant be empty!");
		} else if (cust.getIdProfile().getProfileCode() == null
				|| cust.getIdProfile().getProfileCode().trim().equals("")) {
			throw new Exception("Invalid input, column profile code cant be empty!");
		}
		Profiles pro = profilesService.findByCode(cust.getIdProfile().getProfileCode());
		if (pro == null) {
			throw new Exception("Profile code is doesnt exist!");
		}
	}

}
