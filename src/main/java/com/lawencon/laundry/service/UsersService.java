package com.lawencon.laundry.service;

import java.util.List;

import com.lawencon.laundry.model.Users;

/**
 * @author Imron Rosyadi
 */

public interface UsersService {

	List<Users> getListUsers() throws Exception;

	Users insertUser(Users user) throws Exception;

	Users findByUsername(String uname) throws Exception;

	void updateUser(Users user) throws Exception;

	void deleteUserById(Long id) throws Exception;
}
