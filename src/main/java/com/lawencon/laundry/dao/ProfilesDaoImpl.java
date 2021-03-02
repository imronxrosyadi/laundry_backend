package com.lawencon.laundry.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.laundry.model.Profiles;
import com.lawencon.laundry.model.Roles;
import com.lawencon.laundry.model.Users;

/**
 * @author Imron Rosyadi
 */

@Repository(value = "profile")
public class ProfilesDaoImpl extends BaseDao implements ProfilesDao {

	@Override
	public Profiles loginProfile(String uname, String pass) throws Exception {
		List<Profiles> profilesList = new ArrayList<>();
		String sql = bBuilder("select p.pro_code, p.pro_name, r.role_code from tbl_m_profiles p",
				" inner join tbl_m_users u on u.id = p.id_user inner join tbl_m_roles r on r.id = u.id_role",
				" where u.uname = ? and u.pass = ?").toString();
		List<?> listObj = entityManager.createNativeQuery(sql).setParameter(1, uname).setParameter(2, pass)
				.getResultList();

		listObj.forEach(val -> {
			Object[] objArr = (Object[]) val;
			Profiles pro = new Profiles();
			pro.setProfileCode((String) objArr[0]);
			pro.setProfileName((String) objArr[1]);

			Roles role = new Roles();
			role.setRoleCode((String) objArr[2]);

			Users usr = new Users();
			usr.setIdRole(role);

			pro.setIdUser(usr);

			profilesList.add(pro);
		});

		return profilesList.size() > 0 ? profilesList.get(0) : null;
	}

	@Override
	public List<Profiles> getListProfiles() throws Exception {
		List<Profiles> profilesList = new ArrayList<>();
		String sql = bBuilder("select p.pro_code, p.pro_name, p.pro_phone, p.pro_address, u.uname",
				" from tbl_m_profiles p inner join tbl_m_users u on u.id = p.id_user").toString();
		List<?> listObj = entityManager.createNativeQuery(sql).getResultList();
		listObj.forEach(val -> {
			Object[] objArr = (Object[]) val;
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
	public Profiles findByCode(String code) throws Exception {
		String sql = bBuilder("SELECT p.pro_code, p.pro_name, p.pro_phone, p.pro_address, u.uname",
				"	FROM tbl_m_profiles p INNER JOIN tbl_m_users u ON u.id = p.id_user WHERE p.pro_code = ? ")
						.toString();
		List<Profiles> listProfiles = new ArrayList<>();
		List<?> listObj = entityManager.createNativeQuery(sql).setParameter(1, code).getResultList();
		listObj.forEach(val -> {
			Object[] objArr = (Object[]) val;
			Profiles p = new Profiles();
			p.setProfileCode((String) objArr[0]);
			p.setProfileName((String) objArr[1]);
			p.setProfilePhone((String) objArr[2]);
			p.setProfileAddress((String) objArr[3]);

			Users u = new Users();
			u.setUsername((String) objArr[4]);
			p.setIdUser(u);

			listProfiles.add(p);
		});
		return listProfiles.size() > 0 ? listProfiles.get(0) : null;
	}

	@Override
	public Profiles insertProfile(Profiles pro) throws Exception {
		String sql = bBuilder("INSERT INTO tbl_m_profiles (pro_code, pro_name, pro_phone, pro_address, id_user)",
				" VALUES (?, ?, ?, ?, (select id from tbl_m_users where uname = ?))").toString();

		entityManager.createNativeQuery(sql, Profiles.class).setParameter(1, pro.getProfileCode())
				.setParameter(2, pro.getProfileName()).setParameter(3, pro.getProfilePhone())
				.setParameter(4, pro.getProfileAddress()).setParameter(5, pro.getIdUser().getUsername())
				.executeUpdate();

		return pro;
	}

	@Override
	public void update(Profiles pro) throws Exception {
		String sql = bBuilder(
				"UPDATE tbl_m_profiles SET pro_code = ?, pro_name = ?, pro_phone = ?, pro_address = ?, id_user = ",
				" (SELECT id FROM tbl_m_users WHERE uname = ?) WHERE id = ? ").toString();
		entityManager.createNativeQuery(sql, Profiles.class).setParameter(1, pro.getProfileCode())
				.setParameter(2, pro.getProfileName()).setParameter(3, pro.getProfilePhone())
				.setParameter(4, pro.getProfileAddress()).setParameter(5, pro.getIdUser().getUsername())
				.setParameter(6, pro.getId()).executeUpdate();
	}

	@Override
	public void delete(Long id) throws Exception {
		String sql = "DELETE FROM tbl_m_profiles WHERE id = ?";
		entityManager.createNativeQuery(sql, Profiles.class).setParameter(1, id).executeUpdate();
	}

	@Override
	public Profiles getProfileById(Long id) throws Exception {
		String sql = "SELECT * FROM tbl_m_profiles WHERE id = ?1";
		entityManager.createNativeQuery(sql, Profiles.class).setParameter(1, id).executeUpdate();
		return null;
	}

}
