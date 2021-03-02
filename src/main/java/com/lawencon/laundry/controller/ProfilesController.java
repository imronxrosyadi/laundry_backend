package com.lawencon.laundry.controller;

import java.util.List;

import javax.persistence.PersistenceException;

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
import com.lawencon.laundry.model.Profiles;
import com.lawencon.laundry.service.ProfilesService;

/**
 * @author Imron Rosyadi
 */

@RestController
@RequestMapping("/profile")
public class ProfilesController {

	@Autowired
	private ProfilesService profilesService;

	@GetMapping("/all")
	public Response<List<Profiles>> getAll() {
		try {
			List<Profiles> profile = profilesService.getListProfiles();
			return new Response<>(HttpStatus.OK, profile);
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<>(HttpStatus.BAD_REQUEST, null);
		}
	}

	@GetMapping("/code/{code}")
	public Response<?> getByCode(@PathVariable("code") String code) {
		try {
			Profiles profile = profilesService.findByCode(code);
			return new Response<>(HttpStatus.OK, profile);
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<>(HttpStatus.BAD_REQUEST, null);
		}
	}

	@PostMapping
	public Response<?> addProfile(@RequestBody String body) {
		try {
			Profiles profile = new ObjectMapper().readValue(body, Profiles.class);
			profilesService.insertProfile(profile);
			return new Response<>(HttpStatus.CREATED, profile);
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getMessage().contains("Invalid")) {
				return new Response<>(HttpStatus.BAD_REQUEST, e.getMessage());
			} else if (e.getMessage().contains("Username")) {
				return new Response<>(HttpStatus.BAD_REQUEST, e.getMessage());
			} else {
				return new Response<>(HttpStatus.BAD_REQUEST, null);
			}
		}
	}

	@PutMapping
	public Response<?> updateProfile(@RequestBody String body) {
		try {
			Profiles profile = new ObjectMapper().readValue(body, Profiles.class);
			profilesService.update(profile);
			return new Response<>(HttpStatus.CREATED, profile);
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getMessage().contains("Invalid")) {
				return new Response<>(HttpStatus.BAD_REQUEST, e.getMessage());
			} else if (e.getMessage().contains("Username")) {
				return new Response<>(HttpStatus.BAD_REQUEST, e.getMessage());
			} else {
				return new Response<>(HttpStatus.BAD_REQUEST, null);
			}
		}
	}

	@DeleteMapping("/{id}")
	public Response<?> deleteById(@PathVariable("id") Long id) {
		try {
			profilesService.delete(id);
			return new Response<>(HttpStatus.OK, null);
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			return new Response<>(HttpStatus.BAD_REQUEST, "The data you want delete is used in another table");
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getMessage().contains("Id")) {
				return new Response<>(HttpStatus.BAD_REQUEST, e.getMessage());
			} else {
				return new Response<>(HttpStatus.BAD_REQUEST, null);
			}
		}
	}

}
