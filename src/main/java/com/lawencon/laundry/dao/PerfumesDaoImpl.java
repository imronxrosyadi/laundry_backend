package com.lawencon.laundry.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.laundry.model.Perfumes;
import com.lawencon.laundry.model.Profiles;

/**
 * @author Imron Rosyadi
 */

@Repository(value = "perfume")
public class PerfumesDaoImpl extends BaseDao implements PerfumesDao {

	@Override
	public List<Perfumes> getAll() throws Exception {
		String sql = bBuilder("SELECT pfm.perfume_code, pfm.perfume_name, p.pro_name FROM tbl_m_perfumes pfm ",
				" INNER JOIN tbl_m_profiles p ON p.id = pfm.id_profile ").toString();
		List<Perfumes> pfmLists = new ArrayList<>();
		List<?> listObj = entityManager.createNativeQuery(sql).getResultList();
		listObj.forEach(val -> {
			Object[] objArr = (Object[]) val;
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
		String sql = bBuilder("SELECT pfm.perfume_code, pfm.perfume_name, p.pro_name FROM tbl_m_perfumes pfm",
				" INNER JOIN tbl_m_profiles p ON p.id = pfm.id_profile WHERE pfm.perfume_code = ? ").toString();
		List<Perfumes> pfmLists = new ArrayList<>();
		List<?> listObj = entityManager.createNativeQuery(sql).setParameter(1, code).getResultList();
		listObj.forEach(val -> {
			Object[] objArr = (Object[]) val;
			Perfumes pfm = new Perfumes();
			pfm.setPerfumeCode((String) objArr[0]);
			pfm.setPerfumeName((String) objArr[1]);
			Profiles p = new Profiles();
			p.setProfileName((String) objArr[2]);
			pfm.setIdProfile(p);

			pfmLists.add(pfm);
		});
		return pfmLists.size() > 0 ? pfmLists.get(0) : null;
	}

	@Override
	public void insert(Perfumes pfm) throws Exception {
		String sql = bBuilder("INSERT INTO tbl_m_perfumes (perfume_code, perfume_name, id_profile) ",
				" VALUES (?, ?, (SELECT id FROM tbl_m_profiles WHERE pro_code = ?)) ").toString();
		entityManager.createNativeQuery(sql).setParameter(1, pfm.getPerfumeCode()).setParameter(2, pfm.getPerfumeName())
				.setParameter(3, pfm.getIdProfile().getProfileCode()).executeUpdate();
	}

	@Override
	public void update(Perfumes pfm) throws Exception {
		String sql = bBuilder("UPDATE tbl_m_perfumes SET perfume_code = ?, perfume_name = ?, id_profile = ",
				"(SELECT id FROM tbl_m_profiles WHERE pro_code = ? ) WHERE id = ?").toString();
		entityManager.createNativeQuery(sql).setParameter(1, pfm.getPerfumeCode()).setParameter(2, pfm.getPerfumeName())
				.setParameter(3, pfm.getIdProfile().getProfileCode()).setParameter(4, pfm.getId()).executeUpdate();
	}

	@Override
	public void delete(Long id) throws Exception {
		String sql = "DELETE FROM tbl_m_perfumes WHERE id = ?";
		entityManager.createNativeQuery(sql).setParameter(1, id).executeUpdate();
	}

	@Override
	public Perfumes getPerfumeById(Long id) throws Exception {
		String sql = "SELECT * FROM tbl_m_perfumes WHERE id = ?1";
		entityManager.createNativeQuery(sql).setParameter(1, id).executeUpdate();
		return null;
	}

}
