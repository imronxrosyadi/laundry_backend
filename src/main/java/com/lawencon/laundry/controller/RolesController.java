package com.lawencon.laundry.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawencon.laundry.helper.Response;
import com.lawencon.laundry.model.Roles;
import com.lawencon.laundry.service.RolesService;

/**
 * @author Imron Rosyadi
 */

@RestController
@RequestMapping("/role")
public class RolesController {

	@Autowired
	private RolesService rolesService;

	@GetMapping("/all")
	public Response<List<Roles>> getAll() {
		try {
			List<Roles> role = rolesService.getListRoles();
			return new Response<>(HttpStatus.OK, role);
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<>(HttpStatus.BAD_REQUEST, null);
		}
	}

	@GetMapping("/code/{code}")
	public Response<?> getByCode(@PathVariable("code") String code) {
		try {
			Roles role = rolesService.findByCode(code);
			return new Response<>(HttpStatus.OK, role);
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<>(HttpStatus.BAD_REQUEST, null);
		}
	}

	@PostMapping
	public Response<?> addRole(@RequestBody String body) {
		try {
			Roles role = new ObjectMapper().readValue(body, Roles.class);
			rolesService.insertRole(role);
			return new Response<>(HttpStatus.CREATED, role);
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getMessage().contains("Invalid")) {
				return new Response<>(HttpStatus.BAD_REQUEST, e.getMessage());
			} else {
				return new Response<>(HttpStatus.BAD_REQUEST, null);
			}
		}
	}

	@PutMapping
	public Response<?> updateRole(@RequestBody String body) {
		try {
			Roles role = new ObjectMapper().readValue(body, Roles.class);
			rolesService.updateRole(role);
			return new Response<>(HttpStatus.CREATED, role);
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getMessage().contains("Invalid")) {
				return new Response<>(HttpStatus.BAD_REQUEST, e.getMessage());
			} else {
				return new Response<>(HttpStatus.BAD_REQUEST, null);
			}
		}
	}

	@DeleteMapping("/{id}")
	public Response<?> deleteById(@PathVariable("id") Long id) {
		try {
			rolesService.deleteRoleById(id);
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
