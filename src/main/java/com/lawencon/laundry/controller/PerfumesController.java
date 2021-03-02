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
import com.lawencon.laundry.model.Perfumes;
import com.lawencon.laundry.service.PerfumesService;

/**
 * @author Imron Rosyadi
 */

@RestController
@RequestMapping("/perfume")
public class PerfumesController {

	@Autowired
	private PerfumesService perfumesService;

	@GetMapping("/all")
	public Response<List<Perfumes>> getAll() {
		try {
			List<Perfumes> pfm = perfumesService.getAll();
			return new Response<>(HttpStatus.OK, pfm);
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<>(HttpStatus.BAD_REQUEST, null);
		}
	}

	@GetMapping("/code/{code}")
	public Response<?> getByCode(@PathVariable("code") String code) {
		try {
			Perfumes pfm = perfumesService.getByCode(code);
			return new Response<>(HttpStatus.OK, pfm);
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<>(HttpStatus.BAD_REQUEST, null);
		}
	}

	@PostMapping
	public Response<?> add(@RequestBody String body) {
		try {
			Perfumes pfm = new ObjectMapper().readValue(body, Perfumes.class);
			perfumesService.insert(pfm);
			return new Response<>(HttpStatus.CREATED, pfm);
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
			Perfumes pfm = new ObjectMapper().readValue(body, Perfumes.class);
			perfumesService.update(pfm);
			return new Response<>(HttpStatus.CREATED, pfm);
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
			perfumesService.delete(id);
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
