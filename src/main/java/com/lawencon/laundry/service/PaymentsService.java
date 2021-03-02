package com.lawencon.laundry.service;

import java.util.List;

import com.lawencon.laundry.model.Payments;

/**
 * @author Imron Rosyadi
 */

public interface PaymentsService {

	List<Payments> getAll() throws Exception;

	void insert(Payments pay) throws Exception;

	void update(Payments pay) throws Exception;

	void delete(Long id) throws Exception;

	Payments getByCode(String code) throws Exception;

}
