package com.lawencon.laundry.service;

import java.util.List;

import com.lawencon.laundry.model.Roles;

/**
 * @author Imron Rosyadi
 */

public interface RolesService {

	List<Roles> getListRoles() throws Exception;

	Roles findByCode(String code) throws Exception;

	void insertRole(Roles role) throws Exception;

	void updateRole(Roles role) throws Exception;

	void deleteRoleById(Long id) throws Exception;

}