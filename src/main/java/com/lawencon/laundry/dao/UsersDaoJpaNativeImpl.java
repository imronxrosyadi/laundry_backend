package com.lawencon.laundry.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lawencon.laundry.model.Roles;
import com.lawencon.laundry.model.Users;
import com.lawencon.laundry.repo.UsersRepo;

/**
 * @author Imron Rosyadi
 */

@Repository(value = "userNative")
public class UsersDaoJpaNativeImpl extends BaseDao implements UsersDao {

	@Autowired
	private UsersRepo usersRepo;

	@Override
	public List<Users> getListUsers() throws Exception {
		List<Users> userList = new ArrayList<>();
		List<Object[]> listObj = usersRepo.getAllUsers();
		listObj.forEach(objArr -> {
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
	public Users insertUser(Users user) throws Exception {
		return usersRepo.save(user);
	}

	@Override
	public Users findByUsername(String uname) throws Exception {
		return usersRepo.findByUsername(uname);
	}

	@Override
	public void updateUser(Users user) throws Exception {
		usersRepo.updateUser(user.getUsername(), user.getPassword(), user.getIdRole().getRoleCode(), user.getId());
	}

	@Override
	public void deleteUserById(Long id) throws Exception {
		usersRepo.delete(id);
	}

	@Override
	public Long usedConstraint(Long id) throws Exception {
		return usersRepo.usedConstraint(id);
	}

	@Override
	public Users getUserById(Long id) throws Exception {
		return usersRepo.getUserById(id);
	}

}
