package com.lawencon.laundry.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.lawencon.laundry.helper.Response;
import com.lawencon.laundry.model.Pickups;
import com.lawencon.laundry.service.PickUpsService;

/**
 * @author Imron Rosyadi
 */

@RestController
@RequestMapping("/pickup")
public class PickupsController {

	@Autowired
	private PickUpsService pickUpsService;

	@GetMapping("/all")
	public Response<List<Pickups>> getAll() {
		try {
			List<Pickups> pickUp = pickUpsService.getPickupList();
			return new Response<>(HttpStatus.OK, pickUp);
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR, null);
		}
	}

	@GetMapping("/code/{code}")
	public Response<?> getByCode(@PathVariable("code") String code) {
		try {
			Pickups pickUp = pickUpsService.findByDetailCode(code);
			return new Response<>(HttpStatus.OK, pickUp);
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR, null);
		}
	}

	@PostMapping
	public Response<?> add(@RequestBody String body) {
		try {
			ObjectMapper obj = new ObjectMapper();
			obj.registerModule(new JavaTimeModule());
			Pickups pickUp = obj.readValue(body, Pickups.class);
			pickUpsService.insertPickup(pickUp);
			return new Response<>(HttpStatus.CREATED, pickUp);
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getMessage().contains("Invalid")) {
				return new Response<>(HttpStatus.BAD_REQUEST, e.getMessage());
			} else if (e.getMessage().contains("Customer")) {
				return new Response<>(HttpStatus.BAD_REQUEST, e.getMessage());
			} else if (e.getMessage().contains("Payment")) {
				return new Response<>(HttpStatus.BAD_REQUEST, e.getMessage());
			} else if (e.getMessage().contains("Profile")) {
				return new Response<>(HttpStatus.BAD_REQUEST, e.getMessage());
			} else {
				return new Response<>(HttpStatus.BAD_REQUEST, null);
			}
		}
	}

}
