package com.lawencon.laundry.dao;

import java.util.List;

import com.lawencon.laundry.model.Services;

/**
 * @author Imron Rosyadi
 */

public interface ServicesDao {

	List<Services> getListServices() throws Exception;

	void insertServices(Services service) throws Exception;

	void update(Services service) throws Exception;

	void delete(Long id) throws Exception;

	Services getServiceByCode(String code) throws Exception;

	Services getServiceById(Long id) throws Exception;

}
