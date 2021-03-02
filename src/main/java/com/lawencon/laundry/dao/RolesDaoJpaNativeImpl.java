package com.lawencon.laundry.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lawencon.laundry.model.Roles;
import com.lawencon.laundry.repo.RolesRepo;

/**
 * @author Imron Rosyadi
 */

@Repository(value = "roleNative")
public class RolesDaoJpaNativeImpl implements RolesDao {

	@Autowired
	private RolesRepo rolesRepo;

	@Override
	public List<Roles> getListRoles() throws Exception {
		return rolesRepo.getAllRoles();
	}

	@Override
	public Roles findByCode(String code) throws Exception {
		return rolesRepo.findByCode(code);
	}

	@Override
	public void insertRole(Roles role) throws Exception {
		rolesRepo.save(role);
	}

	@Override
	public void updateRole(Roles role) throws Exception {
		rolesRepo.updateRole(role.getRoleCode(), role.getRoleName(), role.getId());
	}

	@Override
	public void deleteRoleById(Long id) throws Exception {
		rolesRepo.delete(id);
	}

	@Override
	public Long usedConstraint(Long id) throws Exception {
		return rolesRepo.usedConstraint(id);
	}

	@Override
	public Roles getRoleById(Long id) throws Exception {
		return rolesRepo.getRoleById(id);
	}

}
