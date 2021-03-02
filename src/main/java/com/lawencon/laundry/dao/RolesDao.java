package com.lawencon.laundry.dao;

import java.util.List;

import com.lawencon.laundry.model.Roles;

/**
 * @author Imron Rosyadi
 */

public interface RolesDao {

	List<Roles> getListRoles() throws Exception;

	void insertRole(Roles role) throws Exception;

	Roles findByCode(String code) throws Exception;

	void updateRole(Roles role) throws Exception;

	void deleteRoleById(Long id) throws Exception;

	Long usedConstraint(Long id) throws Exception;

	Roles getRoleById(Long id) throws Exception;

}
