package com.lawencon.laundry.dao;

import java.util.List;

import com.lawencon.laundry.model.Customers;

/**
 * @author Imron Rosyadi
 */

public interface CustomersDao {

	List<Customers> getListCustomers() throws Exception;

	Customers insertCustomer(Customers cust) throws Exception;

	Customers findByCode(String code) throws Exception;

	void update(Customers cust) throws Exception;

	void delete(Long id) throws Exception;

	Customers getCustomerById(Long id) throws Exception;

}
