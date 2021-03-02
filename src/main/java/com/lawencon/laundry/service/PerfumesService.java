package com.lawencon.laundry.service;

import java.util.List;

import com.lawencon.laundry.model.Perfumes;

/**
 * @author Imron Rosyadi
 */

public interface PerfumesService {

	List<Perfumes> getAll() throws Exception;

	void insert(Perfumes pfm) throws Exception;

	void update(Perfumes pfm) throws Exception;

	void delete(Long id) throws Exception;

	Perfumes getByCode(String code) throws Exception;

}
