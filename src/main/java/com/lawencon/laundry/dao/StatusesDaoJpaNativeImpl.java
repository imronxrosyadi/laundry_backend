package com.lawencon.laundry.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lawencon.laundry.model.Profiles;
import com.lawencon.laundry.model.Statuses;
import com.lawencon.laundry.repo.StatusesRepo;

/**
 * @author Imron Rosyadi
 */

@Repository(value = "statusNative")
public class StatusesDaoJpaNativeImpl extends BaseDao implements StatusesDao {

	@Autowired
	private StatusesRepo statusesRepo;

	@Override
	public List<Statuses> getAll() throws Exception {
		List<Statuses> statLists = new ArrayList<>();
		List<Object[]> listObj = statusesRepo.getAll();
		listObj.forEach(objArr -> {
			Statuses s = new Statuses();
			s.setStatusCode((String) objArr[0]);
			s.setStatusName((String) objArr[1]);

			Profiles p = new Profiles();
			p.setProfileName((String) objArr[2]);
			s.setIdProfile(p);

			statLists.add(s);
		});
		return statLists;
	}

	@Override
	public Statuses getByCode(String code) throws Exception {
		return statusesRepo.findByCode(code);
	}

	@Override
	public void insert(Statuses status) throws Exception {
		statusesRepo.save(status);
	}

	@Override
	public void update(Statuses status) throws Exception {
		statusesRepo.update(status.getStatusCode(), status.getStatusName(), status.getIdProfile().getProfileCode(),
				status.getId());
	}

	@Override
	public void delete(Long id) throws Exception {
		statusesRepo.delete(id);
	}

	@Override
	public Statuses getStatusById(Long id) throws Exception {
		return statusesRepo.getStatusById(id);
	}

}
