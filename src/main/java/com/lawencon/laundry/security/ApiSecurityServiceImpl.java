package com.lawencon.laundry.security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lawencon.laundry.model.Users;
import com.lawencon.laundry.service.UsersService;

/**
 * @author Imron Rosyadi
 */

@Service
public class ApiSecurityServiceImpl implements UserDetailsService {

	@Autowired
	private UsersService usersService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			Users userDb = usersService.findByUsername(username);
			if (userDb != null) {
				return new User(userDb.getUsername(), userDb.getPassword(), new ArrayList<>());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
