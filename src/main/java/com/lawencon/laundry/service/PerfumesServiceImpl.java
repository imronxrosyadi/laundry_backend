package com.lawencon.laundry.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawencon.laundry.dao.PerfumesDao;
import com.lawencon.laundry.model.Perfumes;
import com.lawencon.laundry.model.Profiles;

/**
 * @author Imron Rosyadi
 */

@Service
@Transactional
public class PerfumesServiceImpl implements PerfumesService {

	private PerfumesDao perfumesDao;
	private ProfilesService profilesService;

	@Autowired
	public void setProfilesService(ProfilesService profilesService) {
		this.profilesService = profilesService;
	}

	@Autowired
	@Qualifier("perfumeNative")
	public void setPerfumesDao(PerfumesDao perfumesDao) {
		this.perfumesDao = perfumesDao;
	}

	@Override
	public List<Perfumes> getAll() throws Exception {
		return perfumesDao.getAll();
	}

	@Override
	public void insert(Perfumes pfm) throws Exception {
		validateInsertPerfumes(pfm);
		Profiles pro = profilesService.findByCode(pfm.getIdProfile().getProfileCode());
		pfm.setIdProfile(pro);
		perfumesDao.insert(pfm);
	}

	@Override
	public void update(Perfumes pfm) throws Exception {
		validateUpdatePerfumes(pfm);
		perfumesDao.update(pfm);
	}

	@Override
	public void delete(Long id) throws Exception {
		Perfumes pfm = perfumesDao.getPerfumeById(id);
		if (pfm == null) {
			throw new Exception("Id is doesnt exist!");
		}
		perfumesDao.delete(id);
	}

	@Override
	public Perfumes getByCode(String code) throws Exception {
		return perfumesDao.getByCode(code);
	}

	void validateInsertPerfumes(Perfumes pfm) throws Exception {
		if (pfm.getId() != null) {
			throw new Exception("Invalid input, column id must be empty!");
		} else if (pfm.getPerfumeCode() == null || pfm.getPerfumeCode().trim().equals("")) {
			throw new Exception("Invalid input, column perfume code cant be empty!");
		} else if (pfm.getPerfumeName() == null || pfm.getPerfumeName().trim().equals("")) {
			throw new Exception("Invalid input, column perfume name cant be empty!");
		} else if (pfm.getIdProfile().getProfileCode() == null
				|| pfm.getIdProfile().getProfileCode().trim().equals("")) {
			throw new Exception("Invalid input, column profile code cant be empty!");
		}
		Profiles pro = profilesService.findByCode(pfm.getIdProfile().getProfileCode());
		if (pro == null) {
			throw new Exception("Profile code is doesnt exist!");
		}
	}

	void validateUpdatePerfumes(Perfumes pfm) throws Exception {
		if (pfm.getId() == null) {
			throw new Exception("Invalid input, column id cant be empty!");
		} else if (pfm.getPerfumeCode() == null || pfm.getPerfumeCode().trim().equals("")) {
			throw new Exception("Invalid input, column perfume code cant be empty!");
		} else if (pfm.getPerfumeName() == null || pfm.getPerfumeName().trim().equals("")) {
			throw new Exception("Invalid input, column perfume name cant be empty!");
		} else if (pfm.getIdProfile().getProfileCode() == null
				|| pfm.getIdProfile().getProfileCode().trim().equals("")) {
			throw new Exception("Invalid input, column profile code cant be empty!");
		}
		Profiles pro = profilesService.findByCode(pfm.getIdProfile().getProfileCode());
		if (pro == null) {
			throw new Exception("Profile code is doesnt exist!");
		}
	}

}
