package com.lawencon.laundry.dao;

import java.util.List;

import com.lawencon.laundry.model.Payments;

/**
 * @author Imron Rosyadi
 */

public interface PaymentsDao {

	List<Payments> getAll() throws Exception;

	void insert(Payments pay) throws Exception;

	void update(Payments pay) throws Exception;

	void delete(Long id) throws Exception;

	Payments getByCode(String code) throws Exception;

	Payments getPaymentById(Long id) throws Exception;

}
