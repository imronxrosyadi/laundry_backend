package com.lawencon.laundry.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.laundry.model.Roles;

/**
 * @author Imron Rosyadi
 */

@Repository(value = "role")
public class RolesDaoImpl extends BaseDao implements RolesDao {

	@Override
	public List<Roles> getListRoles() throws Exception {
		List<Roles> listRoles = new ArrayList<>();
		String sql = "SELECT role_code, role_name FROM tbl_m_roles";
		List<?> listObj = entityManager.createNativeQuery(sql).getResultList();
		listObj.forEach(val -> {
			Object[] objArr = (Object[]) val;
			Roles role = new Roles();
			role.setRoleCode((String) objArr[0]);
			role.setRoleName((String) objArr[1]);
			listRoles.add(role);
		});

		return listRoles;
	}

	@Override
	public Roles findByCode(String code) throws Exception {
		List<Roles> listRoles = new ArrayList<>();
		String sql = "SELECT role_code, role_name FROM tbl_m_roles WHERE role_code = ?";
		List<?> listObj = entityManager.createNativeQuery(sql).setParameter(1, code).getResultList();
		listObj.forEach(val -> {
			Object[] objArr = (Object[]) val;
			Roles role = new Roles();
			role.setRoleCode((String) objArr[0]);
			role.setRoleName((String) objArr[1]);
			listRoles.add(role);
		});

		return listRoles.size() > 0 ? listRoles.get(0) : null;
	}

	@Override
	public void insertRole(Roles role) throws Exception {
		String sql = "INSERT INTO tbl_m_roles(role_code, role_name) VALUES (?, ?)";
		entityManager.createNativeQuery(sql, Roles.class).setParameter(1, role.getRoleCode())
				.setParameter(2, role.getRoleName()).executeUpdate();
	}

	@Override
	public void updateRole(Roles role) throws Exception {
		String sql = "UPDATE tbl_m_roles SET role_code = ?, role_name = ? WHERE id = ?";
		entityManager.createNativeQuery(sql, Roles.class).setParameter(1, role.getRoleCode())
				.setParameter(2, role.getRoleName()).setParameter(3, role.getId()).executeUpdate();
	}

	@Override
	public void deleteRoleById(Long id) throws Exception {
		String sql = "DELETE FROM tbl_m_roles WHERE id = ?";
		entityManager.createNativeQuery(sql, Roles.class).setParameter(1, id).executeUpdate();
	}

	@Override
	public Long usedConstraint(Long id) throws Exception {
		String sql = "SELECT id FROM tbl_m_roles WHERE id = ?1 AND id IN (SELECT id_role FROM tbl_m_users)";
		entityManager.createNativeQuery(sql, Roles.class).setParameter(1, id).executeUpdate();
		return id;
	}

	@Override
	public Roles getRoleById(Long id) throws Exception {
		String sql = "SELECT * FROM tbl_m_roles WHERE id = ?1";
		entityManager.createNativeQuery(sql, Roles.class).setParameter(1, id).executeUpdate();
		return null;
	}

}
