package com.lawencon.laundry.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lawencon.laundry.model.Profiles;
import com.lawencon.laundry.model.Users;
import com.lawencon.laundry.repo.ProfilesRepo;

/**
 * @author Imron Rosyadi
 */

@Repository(value = "profileNative")
public class ProfilesDaoJpaNativeImpl extends BaseDao implements ProfilesDao {

	@Autowired
	private ProfilesRepo profilesRepo;

	@Override
	public Profiles loginProfile(String uname, String pass) throws Exception {
		return null;
	}

	@Override
	public List<Profiles> getListProfiles() throws Exception {
		List<Profiles> profilesList = new ArrayList<>();
		List<Object[]> listObj = profilesRepo.getAll();
		listObj.forEach(objArr -> {
			Profiles p = new Profiles();
			p.setProfileCode((String) objArr[0]);
			p.setProfileName((String) objArr[1]);
			p.setProfilePhone((String) objArr[2]);
			p.setProfileAddress((String) objArr[3]);
			Users u = new Users();
			u.setUsername((String) objArr[4]);
			p.setIdUser(u);

			profilesList.add(p);
		});

		return profilesList;
	}

	@Override
	public Profiles insertProfile(Profiles pro) throws Exception {
		return profilesRepo.save(pro);
	}

	@Override
	public Profiles findByCode(String code) throws Exception {
		return profilesRepo.findByCode(code);
	}

	@Override
	public void update(Profiles pro) throws Exception {
		profilesRepo.update(pro.getProfileCode(), pro.getProfileName(), pro.getProfilePhone(), pro.getProfileAddress(),
				pro.getIdUser().getUsername(), pro.getId());
	}

	@Override
	public void delete(Long id) throws Exception {
		profilesRepo.delete(id);
	}

	@Override
	public Profiles getProfileById(Long id) throws Exception {
		return profilesRepo.getProfileById(id);
	}

}
