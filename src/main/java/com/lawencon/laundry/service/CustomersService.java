package com.lawencon.laundry.service;

import java.util.List;

import com.lawencon.laundry.model.Customers;

/**
 * @author Imron Rosyadi
 */

public interface CustomersService {

	Customers insertCustomer(Customers cust) throws Exception;

	List<Customers> getListCustomers() throws Exception;

	Customers findByCode(String code) throws Exception;

	void update(Customers cust) throws Exception;

	void delete(Long id) throws Exception;
}
