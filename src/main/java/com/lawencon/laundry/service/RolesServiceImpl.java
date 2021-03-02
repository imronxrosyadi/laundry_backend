package com.lawencon.laundry.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawencon.laundry.dao.RolesDao;
import com.lawencon.laundry.model.Roles;

/**
 * @author Imron Rosyadi
 */

@Service
@Transactional
public class RolesServiceImpl implements RolesService {

	private RolesDao rolesDao;

	@Autowired
	@Qualifier(value = "roleNative")
	public void setRolesDao(RolesDao rolesDao) {
		this.rolesDao = rolesDao;
	}

	@Override
	public List<Roles> getListRoles() throws Exception {
		return rolesDao.getListRoles();
	}

	@Override
	public Roles findByCode(String code) throws Exception {
		return rolesDao.findByCode(code);
	}

	@Override
	public void insertRole(Roles role) throws Exception {
		validateInsertRole(role);
		rolesDao.insertRole(role);
	}

	@Override
	public void updateRole(Roles role) throws Exception {
		validateUpdateRole(role);
		rolesDao.updateRole(role);
	}

	@Override
	public void deleteRoleById(Long id) throws Exception {
		Roles roles = rolesDao.getRoleById(id);
		if (roles == null) {
			throw new Exception("Id is doesnt exist");
		}
		Long usedId = rolesDao.usedConstraint(id);
		if (usedId == null) {
			rolesDao.deleteRoleById(id);
		} else {
			throw new Exception("The data you want delete is used in another table");
		}
	}

	void validateInsertRole(Roles role) throws Exception {
		if (role.getId() != null) {
			throw new Exception("Invalid input, column id must be empty!");
		} else if (role.getRoleCode() == null || role.getRoleCode().trim().equals("")) {
			throw new Exception("Invalid input, column role code cant be empty!");
		} else if (role.getRoleName() == null || role.getRoleName().trim().equals("")) {
			throw new Exception("Invalid input, column role name cant be empty!");
		}
	}

	void validateUpdateRole(Roles role) throws Exception {
		if (role.getId() == null) {
			throw new Exception("Invalid input, column id cant be empty!");
		} else if (role.getRoleCode() == null || role.getRoleCode().trim().equals("")) {
			throw new Exception("Invalid input, column role code cant be empty!");
		} else if (role.getRoleName() == null || role.getRoleName().trim().equals("")) {
			throw new Exception("Invalid input, column role name cant be empty!");
		}
	}

}
