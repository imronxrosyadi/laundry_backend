package com.lawencon.laundry.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lawencon.laundry.model.Profiles;
import com.lawencon.laundry.model.Services;
import com.lawencon.laundry.repo.ServicesRepo;

/**
 * @author Imron Rosyadi
 */

@Repository(value = "serviceNative")
public class ServicesDaoJpaNativeImpl extends BaseDao implements ServicesDao {

	@Autowired
	private ServicesRepo servicesRepo;

	@Override
	public List<Services> getListServices() throws Exception {
		List<Services> servicesList = new ArrayList<>();
		List<Object[]> listObj = servicesRepo.getAll();
		listObj.forEach(objArr -> {
			Services s = new Services();
			s.setServiceCode((String) objArr[0]);
			s.setServiceName((String) objArr[1]);
			s.setServicePrice((BigDecimal) objArr[2]);

			Profiles p = new Profiles();
			p.setProfileName((String) objArr[3]);
			s.setIdProfile(p);

			servicesList.add(s);
		});
		return servicesList;
	}

	@Override
	public Services getServiceByCode(String code) throws Exception {
		return servicesRepo.findByCode(code);
	}

	@Override
	public void insertServices(Services service) throws Exception {
		servicesRepo.save(service);
	}

	@Override
	public void update(Services service) throws Exception {
		servicesRepo.update(service.getServiceCode(), service.getServiceName(), service.getServicePrice(),
				service.getIdProfile().getProfileCode(), service.getId());
	}

	@Override
	public void delete(Long id) throws Exception {
		servicesRepo.delete(id);
	}

	@Override
	public Services getServiceById(Long id) throws Exception {
		return servicesRepo.getServiceById(id);
	}

}
