package com.lawencon.laundry.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lawencon.laundry.model.Customers;
import com.lawencon.laundry.model.Profiles;
import com.lawencon.laundry.repo.CustomersRepo;

/**
 * @author Imron Rosyadi
 */

@Repository(value = "custNative")
public class CustomersDaoJpaNativeImpl extends BaseDao implements CustomersDao {

	@Autowired
	private CustomersRepo customersRepo;

	@Override
	public Customers insertCustomer(Customers cust) throws Exception {
		return customersRepo.save(cust);
	}

	@Override
	public List<Customers> getListCustomers() throws Exception {
		List<Customers> listCustomers = new ArrayList<>();
		List<Object[]> listObj = customersRepo.getAll();
		listObj.forEach(objArr -> {
			Customers c = new Customers();
			c.setCustCode((String) objArr[0]);
			c.setCustName((String) objArr[1]);
			c.setCustPhone((String) objArr[2]);
			c.setCustAddress((String) objArr[3]);

			Profiles p = new Profiles();
			p.setProfileName((String) objArr[4]);
			c.setIdProfile(p);

			listCustomers.add(c);
		});

		return listCustomers;
	}

	@Override
	public Customers findByCode(String code) throws Exception {
		return customersRepo.findByCode(code);
	}

	@Override
	public void update(Customers cust) throws Exception {
		customersRepo.update(cust.getCustCode(), cust.getCustName(), cust.getCustPhone(), cust.getCustAddress(),
				cust.getIdProfile().getProfileCode(), cust.getId());
	}

	@Override
	public void delete(Long id) throws Exception {
		customersRepo.delete(id);
	}

	@Override
	public Customers getCustomerById(Long id) throws Exception {
		return customersRepo.getCustomerById(id);
	}
}
