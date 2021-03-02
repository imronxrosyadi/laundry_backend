package com.lawencon.laundry.dao;

import java.util.List;

import com.lawencon.laundry.model.Statuses;

/**
 * @author Imron Rosyadi
 */

public interface StatusesDao {

	List<Statuses> getAll() throws Exception;

	void insert(Statuses status) throws Exception;

	void update(Statuses status) throws Exception;

	void delete(Long id) throws Exception;

	Statuses getByCode(String code) throws Exception;

	Statuses getStatusById(Long id) throws Exception;

}
