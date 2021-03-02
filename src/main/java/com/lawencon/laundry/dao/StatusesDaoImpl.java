package com.lawencon.laundry.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.laundry.model.Profiles;
import com.lawencon.laundry.model.Statuses;

/**
 * @author Imron Rosyadi
 */

@Repository(value = "status")
public class StatusesDaoImpl extends BaseDao implements StatusesDao {

	@Override
	public List<Statuses> getAll() throws Exception {
		String sql = bBuilder("SELECT s.stat_code, s.stat_name, p.pro_name FROM tbl_m_statuses s",
				" INNER JOIN tbl_m_profiles p ON p.id = s.id_profile ").toString();
		List<Statuses> statLists = new ArrayList<>();
		List<?> listObj = entityManager.createNativeQuery(sql).getResultList();
		listObj.forEach(val -> {
			Object[] objArr = (Object[]) val;
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
		String sql = bBuilder("SELECT s.stat_code, s.stat_name, p.pro_name FROM tbl_m_statuses s",
				" INNER JOIN tbl_m_profiles p ON p.id = s.id_profile WHERE s.stat_code = ?").toString();
		List<Statuses> statLists = new ArrayList<>();
		List<?> listObj = entityManager.createNativeQuery(sql).setParameter(1, code).getResultList();
		listObj.forEach(val -> {
			Object[] objArr = (Object[]) val;
			Statuses s = new Statuses();
			s.setStatusCode((String) objArr[0]);
			s.setStatusName((String) objArr[1]);

			Profiles p = new Profiles();
			p.setProfileName((String) objArr[2]);
			s.setIdProfile(p);

			statLists.add(s);
		});
		return statLists.size() > 0 ? statLists.get(0) : null;
	}

	@Override
	public void insert(Statuses status) throws Exception {
		String sql = bBuilder("INSERT INTO tbl_m_statuses (stat_code, stat_name, id_profile) ",
				" VALUES (?, ?, (SELECT id FROM tbl_m_profiles WHERE pro_code = ?)) ").toString();
		entityManager.createNativeQuery(sql).setParameter(1, status.getStatusCode())
				.setParameter(2, status.getStatusName()).setParameter(3, status.getIdProfile().getProfileCode())
				.executeUpdate();
	}

	@Override
	public void update(Statuses status) throws Exception {
		String sql = bBuilder("UPDATE tbl_m_statuses SET stat_code = ?, stat_name = ?, id_profile = ",
				" (SELECT id FROM tbl_m_profiles WHERE pro_code = ? ) WHERE id = ? ").toString();
		entityManager.createNativeQuery(sql).setParameter(1, status.getStatusCode())
				.setParameter(2, status.getStatusName()).setParameter(3, status.getIdProfile().getProfileCode())
				.setParameter(4, status.getId()).executeUpdate();
	}

	@Override
	public void delete(Long id) throws Exception {
		String sql = "DELETE FROM tbl_m_statuses WHERE id = ? ";
		entityManager.createNativeQuery(sql).setParameter(1, id).executeUpdate();
	}

	@Override
	public Statuses getStatusById(Long id) throws Exception {
		String sql = "SELECT * FROM tbl_m_customers WHERE id = ?1";
		entityManager.createNativeQuery(sql).setParameter(1, id).executeUpdate();
		return null;
	}

}
