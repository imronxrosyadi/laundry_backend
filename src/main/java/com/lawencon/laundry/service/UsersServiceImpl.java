package com.lawencon.laundry.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawencon.laundry.dao.UsersDao;
import com.lawencon.laundry.model.Roles;
import com.lawencon.laundry.model.Users;

/**
 * @author Imron Rosyadi
 */

@Service
@Transactional
public class UsersServiceImpl extends BaseService implements UsersService {

	private UsersDao usersDao;
	private RolesService rolesService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	public void setRolesService(RolesService rolesService) {
		this.rolesService = rolesService;
	}

	@Autowired
	@Qualifier(value = "userNative")
	public void setUsersDao(UsersDao usersDao) {
		this.usersDao = usersDao;
	}

	@Override
	public List<Users> getListUsers() throws Exception {
		return usersDao.getListUsers();
	}

	@Override
	public Users insertUser(Users user) throws Exception {
		validateInsertUser(user);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		Roles role = rolesService.findByCode(user.getIdRole().getRoleCode());
		user.setIdRole(role);
		return usersDao.insertUser(user);
	}

	@Override
	public Users findByUsername(String uname) throws Exception {
		return usersDao.findByUsername(uname);
	}

	@Override
	public void updateUser(Users user) throws Exception {
		validateUpdateUser(user);
		usersDao.updateUser(user);
	}

	@Override
	public void deleteUserById(Long id) throws Exception {
		Users usr = usersDao.getUserById(id);
		if (usr == null) {
			throw new Exception("Id is doesnt exist");
		}
		Long usedId = usersDao.usedConstraint(id);
		if (usedId == null) {
			usersDao.deleteUserById(id);
		} else {
			throw new Exception("The data you want delete is used in another table");
		}

	}

	void validateInsertUser(Users user) throws Exception {
		if (user.getId() != null) {
			throw new Exception("Invalid input, column id must be empty!");
		}
		if (user.getUsername() == null || user.getUsername().trim().equals("")) {
			throw new Exception("Invalid input, column username cant be empty!");
		}
		if (user.getPassword() == null || user.getPassword().trim().equals("")) {
			throw new Exception("Invalid input, column password cant be empty!");
		}
		if (user.getIdRole() == null) {
			throw new Exception("Invalid input, column id role cant be empty!");
		}
		if (user.getIdRole().getRoleCode() == null || user.getIdRole().getRoleCode().trim().equals("")) {
			throw new Exception("Invalid input, column role code cant be empty!");
		}
		Roles role = rolesService.findByCode(user.getIdRole().getRoleCode());
		if (role == null) {
			throw new Exception("Role code is doesnt exist!");
		}
		Users userNew = findByUsername(user.getUsername());
		if (userNew.getUsername().equals(user.getUsername())) {
			throw new Exception("Username already exist!");
		}
	}

	void validateUpdateUser(Users user) throws Exception {
		if (user.getId() == null) {
			throw new Exception("Invalid input, column id cant be empty!");
		} else if (user.getUsername() == null || user.getUsername().trim().equals("")) {
			throw new Exception("Invalid input, column username cant be empty!");
		} else if (user.getPassword() == null || user.getPassword().trim().equals("")) {
			throw new Exception("Invalid input, column password cant be empty!");
		} else if (user.getIdRole().getRoleCode() == null || user.getIdRole().getRoleCode().trim().equals("")) {
			throw new Exception("Invalid input, column role code cant be empty!");
		}
		Roles role = rolesService.findByCode(user.getIdRole().getRoleCode());
		if (role == null) {
			throw new Exception("Role code is doesnt exist!");
		}
	}

}
