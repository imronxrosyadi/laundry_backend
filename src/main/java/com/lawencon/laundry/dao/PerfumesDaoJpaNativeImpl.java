package com.lawencon.laundry.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lawencon.laundry.model.Perfumes;
import com.lawencon.laundry.model.Profiles;
import com.lawencon.laundry.repo.PerfumesRepo;

/**
 * @author Imron Rosyadi
 */

@Repository(value = "perfumeNative")
public class PerfumesDaoJpaNativeImpl extends BaseDao implements PerfumesDao {

	@Autowired
	private PerfumesRepo perfumesRepo;

	@Override
	public List<Perfumes> getAll() throws Exception {
		List<Perfumes> pfmLists = new ArrayList<>();
		List<Object[]> listObj = perfumesRepo.getAll();
		listObj.forEach(objArr -> {
			Perfumes pfm = new Perfumes();
			pfm.setPerfumeCode((String) objArr[0]);
			pfm.setPerfumeName((String) objArr[1]);
			Profiles p = new Profiles();
			p.setProfileName((String) objArr[2]);
			pfm.setIdProfile(p);

			pfmLists.add(pfm);
		});
		return pfmLists;
	}

	@Override
	public Perfumes getByCode(String code) throws Exception {
		return perfumesRepo.findByCode(code);
	}

	@Override
	public void insert(Perfumes pfm) throws Exception {
		perfumesRepo.save(pfm);
	}

	@Override
	public void update(Perfumes pfm) throws Exception {
		perfumesRepo.update(pfm.getPerfumeCode(), pfm.getPerfumeName(), pfm.getIdProfile().getProfileCode(),
				pfm.getId());
	}

	@Override
	public void delete(Long id) throws Exception {
		perfumesRepo.delete(id);
	}

	@Override
	public Perfumes getPerfumeById(Long id) throws Exception {
		return perfumesRepo.getPerfumeById(id);
	}

}
