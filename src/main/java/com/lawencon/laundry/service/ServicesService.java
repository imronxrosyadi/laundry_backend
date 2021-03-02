package com.lawencon.laundry.service;

import java.util.List;

import com.lawencon.laundry.model.Services;

/**
 * @author Imron Rosyadi
 */

public interface ServicesService {

	List<Services> getListServices() throws Exception;

	void insertServices(Services service) throws Exception;

	void update(Services service) throws Exception;

	void delete(Long id) throws Exception;

	Services getServiceByCode(String code) throws Exception;

}
