package com.lawencon.laundry.dao;

import java.util.List;

import com.lawencon.laundry.model.Users;

/**
 * @author Imron Rosyadi
 */

public interface UsersDao {

	List<Users> getListUsers() throws Exception;

	Users insertUser(Users user) throws Exception;

	Users findByUsername(String uname) throws Exception;

	void updateUser(Users user) throws Exception;

	void deleteUserById(Long id) throws Exception;

	Long usedConstraint(Long id) throws Exception;

	Users getUserById(Long id) throws Exception;

}
