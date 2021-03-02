package com.lawencon.laundry.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawencon.laundry.helper.Response;
import com.lawencon.laundry.model.Users;
import com.lawencon.laundry.service.UsersService;

/**
 * @author Imron Rosyadi
 */

@RestController
@RequestMapping("/user")
public class UsersController {

	@Autowired
	private UsersService usersService;

	@GetMapping("/all")
	public Response<List<Users>> getAll() {
		try {
			List<Users> user = usersService.getListUsers();
			return new Response<>(HttpStatus.OK, user);
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<>(HttpStatus.BAD_REQUEST, null);
		}
	}

	@GetMapping("/code/{code}")
	public Response<?> getByCode(@PathVariable("code") String code) {
		try {
			Users user = usersService.findByUsername(code);
			return new Response<>(HttpStatus.OK, user);
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<>(HttpStatus.BAD_REQUEST, null);
		}
	}

	@PostMapping
	public Response<?> addUser(@RequestBody String body) {
		try {
			Users user = new ObjectMapper().readValue(body, Users.class);
			usersService.insertUser(user);
			return new Response<>(HttpStatus.CREATED, user);
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getMessage().contains("Invalid")) {
				return new Response<>(HttpStatus.BAD_REQUEST, e.getMessage());
			} else if (e.getMessage().contains("Role code")) {
				return new Response<>(HttpStatus.BAD_REQUEST, e.getMessage());
			} else if (e.getMessage().contains("Username")) {
				return new Response<>(HttpStatus.BAD_REQUEST, e.getMessage());
			} else {
				return new Response<>(HttpStatus.BAD_REQUEST, null);
			}
		}
	}

	@PatchMapping
	public Response<?> updateUser(@RequestBody String body) {
		try {
			Users user = new ObjectMapper().readValue(body, Users.class);
			usersService.updateUser(user);
			return new Response<>(HttpStatus.CREATED, user);
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getMessage().contains("Invalid")) {
				return new Response<>(HttpStatus.BAD_REQUEST, e.getMessage());
			} else if (e.getMessage().contains("Role code")) {
				return new Response<>(HttpStatus.BAD_REQUEST, e.getMessage());
			} else {
				return new Response<>(HttpStatus.BAD_REQUEST, null);
			}
		}
	}

	@DeleteMapping("/{id}")
	public Response<?> deleteById(@PathVariable("id") Long id) {
		try {
			usersService.deleteUserById(id);
			return new Response<>(HttpStatus.OK, null);
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getMessage().contains("The data")) {
				return new Response<>(HttpStatus.BAD_REQUEST, e.getMessage());
			} else if (e.getMessage().contains("Id")) {
				return new Response<>(HttpStatus.BAD_REQUEST, e.getMessage());
			} else {
				return new Response<>(HttpStatus.BAD_REQUEST, null);
			}
		}
	}

}
