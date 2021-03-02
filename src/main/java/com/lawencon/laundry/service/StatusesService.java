package com.lawencon.laundry.service;

import java.util.List;

import com.lawencon.laundry.model.Statuses;

/**
 * @author Imron Rosyadi
 */

public interface StatusesService {

	List<Statuses> getAll() throws Exception;

	void insert(Statuses status) throws Exception;

	void update(Statuses status) throws Exception;

	void delete(Long id) throws Exception;

	Statuses getByCode(String code) throws Exception;

}
