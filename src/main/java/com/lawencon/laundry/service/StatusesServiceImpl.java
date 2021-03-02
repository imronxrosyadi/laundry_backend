package com.lawencon.laundry.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawencon.laundry.dao.StatusesDao;
import com.lawencon.laundry.model.Profiles;
import com.lawencon.laundry.model.Statuses;

/**
 * @author Imron Rosyadi
 */

@Service
@Transactional
public class StatusesServiceImpl implements StatusesService {

	private StatusesDao statusesDao;
	private ProfilesService profilesService;

	@Autowired
	public void setProfilesService(ProfilesService profilesService) {
		this.profilesService = profilesService;
	}

	@Autowired
	@Qualifier(value = "statusNative")
	public void setStatusesDao(StatusesDao statusesDao) {
		this.statusesDao = statusesDao;
	}

	@Override
	public List<Statuses> getAll() throws Exception {
		return statusesDao.getAll();
	}

	@Override
	public void insert(Statuses status) throws Exception {
		validateInsertStatus(status);
		Profiles pro = profilesService.findByCode(status.getIdProfile().getProfileCode());
		status.setIdProfile(pro);
		statusesDao.insert(status);
	}

	@Override
	public void update(Statuses status) throws Exception {
		validateUpdateStatus(status);
		statusesDao.update(status);
	}

	@Override
	public void delete(Long id) throws Exception {
		Statuses stat = statusesDao.getStatusById(id);
		if (stat == null) {
			throw new Exception("Id is doesnt exist!");
		}
		statusesDao.delete(id);
	}

	@Override
	public Statuses getByCode(String code) throws Exception {
		return statusesDao.getByCode(code);
	}

	void validateInsertStatus(Statuses stat) throws Exception {
		if (stat.getId() != null) {
			throw new Exception("Invalid input, column id must be empty!");
		} else if (stat.getStatusCode() == null || stat.getStatusCode().trim().equals("")) {
			throw new Exception("Invalid input, column status code cant be empty!");
		} else if (stat.getStatusName() == null || stat.getStatusName().trim().equals("")) {
			throw new Exception("Invalid input, column status name cant be empty!");
		} else if (stat.getIdProfile().getProfileCode() == null
				|| stat.getIdProfile().getProfileCode().trim().equals("")) {
			throw new Exception("Invalid input, column profile code cant be empty!");
		}
		Profiles pro = profilesService.findByCode(stat.getIdProfile().getProfileCode());
		if (pro == null) {
			throw new Exception("Profile code is doesnt exist!");
		}
	}

	void validateUpdateStatus(Statuses stat) throws Exception {
		if (stat.getId() == null) {
			throw new Exception("Invalid input, column id cant be empty!");
		} else if (stat.getStatusCode() == null || stat.getStatusCode().trim().equals("")) {
			throw new Exception("Invalid input, column status code cant be empty!");
		} else if (stat.getStatusName() == null || stat.getStatusName().trim().equals("")) {
			throw new Exception("Invalid input, column status name cant be empty!");
		} else if (stat.getIdProfile().getProfileCode() == null
				|| stat.getIdProfile().getProfileCode().trim().equals("")) {
			throw new Exception("Invalid input, column profile code cant be empty!");
		}
		Profiles pro = profilesService.findByCode(stat.getIdProfile().getProfileCode());
		if (pro == null) {
			throw new Exception("Profile code is doesnt exist!");
		}
	}

}
