package com.lawencon.laundry.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.laundry.model.Roles;
import com.lawencon.laundry.model.Users;

/**
 * @author Imron Rosyadi
 */

@Repository(value = "user")
public class UsersDaoImpl extends BaseDao implements UsersDao {

	@Override
	public List<Users> getListUsers() throws Exception {
		String sql = "SELECT u.uname, u.pass, r.role_name FROM tbl_m_users u INNER JOIN tbl_m_roles r ON r.id = u.id_role";
		List<Users> userList = new ArrayList<>();
		List<?> listObj = entityManager.createNativeQuery(sql).getResultList();
		listObj.forEach(val -> {
			Object[] objArr = (Object[]) val;
			Users u = new Users();
			u.setUsername((String) objArr[0]);
			u.setPassword((String) objArr[1]);

			Roles r = new Roles();
			r.setRoleName((String) objArr[2]);
			u.setIdRole(r);

			userList.add(u);
		});
		return userList;
	}

	@Override
	public Users findByUsername(String uname) throws Exception {
		String sql = "SELECT u.uname, u.pass, r.role_name FROM tbl_m_users u INNER JOIN tbl_m_roles r ON r.id = u.id_role WHERE u.uname = ? ";
		List<Users> userList = new ArrayList<>();
		List<?> listObj = entityManager.createNativeQuery(sql).setParameter(1, uname).getResultList();
		listObj.forEach(val -> {
			Object[] objArr = (Object[]) val;
			Users u = new Users();
			u.setUsername((String) objArr[0]);
			u.setPassword((String) objArr[1]);

			Roles r = new Roles();
			r.setRoleName((String) objArr[2]);
			u.setIdRole(r);

			userList.add(u);
		});
		return userList.size() > 0 ? userList.get(0) : null;
	}

	@Override
	public Users insertUser(Users user) throws Exception {
		String sql = "INSERT INTO tbl_m_users (uname, pass, id_role) VALUES (?1, ?2, (SELECT id FROM tbl_m_roles WHERE role_code = ?3))";
		entityManager.createNativeQuery(sql, Users.class).setParameter(1, user.getUsername())
				.setParameter(2, user.getPassword()).setParameter(3, user.getIdRole().getRoleCode()).executeUpdate();
		return user;
	}

	@Override
	public void updateUser(Users user) throws Exception {
		String sql = "UPDATE tbl_m_users SET uname = ?1, pass = ?2, id_role = (SELECT id FROM tbl_m_roles WHERE role_code = ?3) WHERE id = ?4 ";
		entityManager.createNativeQuery(sql, Users.class).setParameter(1, user.getUsername())
				.setParameter(2, user.getPassword()).setParameter(3, user.getIdRole().getRoleCode())
				.setParameter(4, user.getId()).executeUpdate();
	}

	@Override
	public void deleteUserById(Long id) throws Exception {
		String sql = "DELETE FROM tbl_m_users WHERE id = ?1";
		entityManager.createNativeQuery(sql, Users.class).setParameter(1, id).executeUpdate();
	}

	@Override
	public Long usedConstraint(Long id) throws Exception {
		String sql = "SELECT id FROM tbl_m_users WHERE id = ?1 AND id IN (SELECT id_user FROM tbl_m_profiles)";
		entityManager.createNativeQuery(sql, Users.class).setParameter(1, id).executeUpdate();
		return id;
	}

	@Override
	public Users getUserById(Long id) throws Exception {
		String sql = "SELECT * FROM tbl_m_users WHERE id = ?1 ";
		entityManager.createNativeQuery(sql, Users.class).setParameter(1, id).executeUpdate();
		return null;
	}

}
