package com.lawencon.laundry.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawencon.laundry.dao.ServicesDao;
import com.lawencon.laundry.model.Profiles;
import com.lawencon.laundry.model.Services;

/**
 * @author Imron Rosyadi
 */
@Service
@Transactional
public class ServicesServiceImpl implements ServicesService {

	private ServicesDao servicesDao;
	private ProfilesService profilesService;

	@Autowired
	public void setProfilesService(ProfilesService profilesService) {
		this.profilesService = profilesService;
	}

	@Autowired
	@Qualifier(value = "serviceNative")
	public void setServicesDao(ServicesDao servicesDao) {
		this.servicesDao = servicesDao;
	}

	@Override
	public Services getServiceByCode(String code) throws Exception {
		return servicesDao.getServiceByCode(code);
	}

	@Override
	public List<Services> getListServices() throws Exception {
		return servicesDao.getListServices();
	}

	@Override
	public void insertServices(Services service) throws Exception {
		validateInsertService(service);
		Profiles pro = profilesService.findByCode(service.getIdProfile().getProfileCode());
		service.setIdProfile(pro);
		servicesDao.insertServices(service);
	}

	@Override
	public void update(Services service) throws Exception {
		validateUpdateService(service);
		servicesDao.update(service);
	}

	@Override
	public void delete(Long id) throws Exception {
		Services serv = servicesDao.getServiceById(id);
		if (serv == null) {
			throw new Exception("Id is doesnt exist!");
		}
		servicesDao.delete(id);
	}

	void validateInsertService(Services service) throws Exception {
		if (service.getId() != null) {
			throw new Exception("Invalid input, column id must be empty!");
		} else if (service.getServiceCode() == null || service.getServiceCode().trim().equals("")) {
			throw new Exception("Invalid input, column service code cant be empty!");
		} else if (service.getServiceName() == null || service.getServiceName().trim().equals("")) {
			throw new Exception("Invalid input, column service name cant be empty!");
		} else if (service.getServicePrice() == null) {
			throw new Exception("Invalid input, column service price cant be empty!");
		} else if (service.getIdProfile().getProfileCode() == null
				|| service.getIdProfile().getProfileCode().trim().equals("")) {
			throw new Exception("Invalid input, column profile code cant be empty!");
		}
		Profiles pro = profilesService.findByCode(service.getIdProfile().getProfileCode());
		if (pro == null) {
			throw new Exception("Profile code is doesnt exist!");
		}
	}

	void validateUpdateService(Services service) throws Exception {
		if (service.getId() == null) {
			throw new Exception("Invalid input, column id cant be empty!");
		} else if (service.getServiceCode() == null || service.getServiceCode().trim().equals("")) {
			throw new Exception("Invalid input, column service code cant be empty!");
		} else if (service.getServiceName() == null || service.getServiceName().trim().equals("")) {
			throw new Exception("Invalid input, column service name cant be empty!");
		} else if (service.getServicePrice() == null) {
			throw new Exception("Invalid input, column service price cant be empty!");
		} else if (service.getIdProfile().getProfileCode() == null
				|| service.getIdProfile().getProfileCode().trim().equals("")) {
			throw new Exception("Invalid input, column profile code cant be empty!");
		}
		Profiles pro = profilesService.findByCode(service.getIdProfile().getProfileCode());
		if (pro == null) {
			throw new Exception("Profile code is doesnt exist!");
		}
	}

}
