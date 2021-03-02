package com.lawencon.laundry.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawencon.laundry.dao.ProfilesDao;
import com.lawencon.laundry.model.ProfileSession;
import com.lawencon.laundry.model.Profiles;
import com.lawencon.laundry.model.Users;

/**
 * @author Imron Rosyadi
 */

@Service
@Transactional
public class ProfilesServiceImpl extends BaseService implements ProfilesService {

	private ProfilesDao profilesDao;
	private ProfileSession profileSession;
	private UsersService usersService;

	@Autowired
	public void setUsersService(UsersService usersService) {
		this.usersService = usersService;
	}

	public void setProfileSession(ProfileSession profileSession) {
		this.profileSession = profileSession;
	}

	@Autowired
	@Qualifier(value = "profileNative")
	public void setProfilesDao(ProfilesDao profilesDao) {
		this.profilesDao = profilesDao;
	}

	@Override
	public Profiles loginProfile(String uname, String pass) throws Exception {
		Profiles pro = profilesDao.loginProfile(uname, pass);
		profileSession.setActiveProfile(pro);
		return profileSession.getActiveProfile();
	}

	@Override
	public Profiles insertProfile(Profiles pro) throws Exception {
		validateInsertProfile(pro);
		Users usr = usersService.findByUsername(pro.getIdUser().getUsername());
		pro.setIdUser(usr);
		return profilesDao.insertProfile(pro);
	}

	@Override
	public List<Profiles> getListProfiles() throws Exception {
		return profilesDao.getListProfiles();
	}

	@Override
	public Profiles findByCode(String code) throws Exception {
		return profilesDao.findByCode(code);
	}

	@Override
	public void update(Profiles pro) throws Exception {
		validateUpdateProfile(pro);
		profilesDao.update(pro);
	}

	@Override
	public void delete(Long id) throws Exception {
		Profiles pro = profilesDao.getProfileById(id);
		if (pro == null) {
			throw new Exception("Id is doesnt exist");
		} else {
			profilesDao.delete(id);
		}
	}

	void validateInsertProfile(Profiles pro) throws Exception {
		if (pro.getId() != null) {
			throw new Exception("Invalid input, column id must be empty!");
		} else if (pro.getProfileCode() == null || pro.getProfileCode().trim().equals("")) {
			throw new Exception("Invalid input, column profile code cant be empty!");
		} else if (pro.getProfileName() == null || pro.getProfileName().trim().equals("")) {
			throw new Exception("Invalid input, column profile name cant be empty!");
		} else if (pro.getProfilePhone() == null || pro.getProfilePhone().trim().equals("")) {
			throw new Exception("Invalid input, column profile phone cant be empty!");
		} else if (pro.getProfileAddress() == null || pro.getProfileAddress().trim().equals("")) {
			throw new Exception("Invalid input, column profile address cant be empty!");
		} else if (pro.getIdUser().getUsername() == null || pro.getIdUser().getUsername().trim().equals("")) {
			throw new Exception("Invalid input, column username cant be empty!");
		}
		Users usr = usersService.findByUsername(pro.getIdUser().getUsername());
		if (usr == null) {
			throw new Exception("Username is doesnt exist!");
		}
	}

	void validateUpdateProfile(Profiles pro) throws Exception {
		if (pro.getId() == null) {
			throw new Exception("Invalid input, column id cant be empty!");
		} else if (pro.getProfileCode() == null || pro.getProfileCode().trim().equals("")) {
			throw new Exception("Invalid input, column profile code cant be empty!");
		} else if (pro.getProfileName() == null || pro.getProfileName().trim().equals("")) {
			throw new Exception("Invalid input, column profile name cant be empty!");
		} else if (pro.getProfilePhone() == null || pro.getProfilePhone().trim().equals("")) {
			throw new Exception("Invalid input, column profile phone cant be empty!");
		} else if (pro.getProfileAddress() == null || pro.getProfileAddress().trim().equals("")) {
			throw new Exception("Invalid input, column profile address cant be empty!");
		} else if (pro.getIdUser().getUsername() == null || pro.getIdUser().getUsername().trim().equals("")) {
			throw new Exception("Invalid input, column username cant be empty!");
		}
		Users usr = usersService.findByUsername(pro.getIdUser().getUsername());
		if (usr == null) {
			throw new Exception("Username is doesnt exist!");
		}
	}

}
