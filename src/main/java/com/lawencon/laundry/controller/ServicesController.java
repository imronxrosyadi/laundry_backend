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
import com.lawencon.laundry.model.Services;
import com.lawencon.laundry.service.ServicesService;

/**
 * @author Imron Rosyadi
 */

@RestController
@RequestMapping("/service")
public class ServicesController {

	@Autowired
	private ServicesService servicesService;

	@GetMapping("/all")
	public Response<List<Services>> getAll() {
		try {
			List<Services> service = servicesService.getListServices();
			return new Response<>(HttpStatus.OK, service);
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<>(HttpStatus.BAD_REQUEST, null);
		}
	}

	@GetMapping("/code/{code}")
	public Response<?> getByCode(@PathVariable("code") String code) {
		try {
			Services service = servicesService.getServiceByCode(code);
			return new Response<>(HttpStatus.OK, service);
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<>(HttpStatus.BAD_REQUEST, null);
		}
	}

	@PostMapping
	public Response<?> add(@RequestBody String body) {
		try {
			Services service = new ObjectMapper().readValue(body, Services.class);
			servicesService.insertServices(service);
			return new Response<>(HttpStatus.CREATED, service);
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getMessage().contains("Invalid")) {
				return new Response<>(HttpStatus.BAD_REQUEST, e.getMessage());
			} else if (e.getMessage().contains("Profile")) {
				return new Response<>(HttpStatus.BAD_REQUEST, e.getMessage());
			} else {
				return new Response<>(HttpStatus.BAD_REQUEST, null);
			}
		}
	}

	@PutMapping
	public Response<?> update(@RequestBody String body) {
		try {
			Services service = new ObjectMapper().readValue(body, Services.class);
			servicesService.update(service);
			return new Response<>(HttpStatus.CREATED, service);
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getMessage().contains("Invalid")) {
				return new Response<>(HttpStatus.BAD_REQUEST, e.getMessage());
			} else if (e.getMessage().contains("Profile")) {
				return new Response<>(HttpStatus.BAD_REQUEST, e.getMessage());
			} else {
				return new Response<>(HttpStatus.BAD_REQUEST, null);
			}
		}
	}

	@DeleteMapping("/{id}")
	public Response<?> deleteById(@PathVariable("id") Long id) {
		try {
			servicesService.delete(id);
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
