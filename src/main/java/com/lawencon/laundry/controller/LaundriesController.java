package com.lawencon.laundry.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.lawencon.laundry.helper.LaundryHelper;
import com.lawencon.laundry.helper.Response;
import com.lawencon.laundry.model.Laundries;
import com.lawencon.laundry.service.LaundriesService;

/**
 * @author Imron Rosyadi
 */

@RestController
public class LaundriesController {

	@Autowired
	private LaundriesService laundriesService;

	@GetMapping("/history")
	public Response<List<Laundries>> getHistory() {
		try {
			List<Laundries> laundry = laundriesService.getListLaundries();
			return new Response<>(HttpStatus.OK, laundry);
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<>(HttpStatus.BAD_REQUEST, null);
		}
	}

	@GetMapping("/laundry/all")
	public Response<List<Laundries>> getAll() {
		try {
			List<Laundries> laundry = laundriesService.getListLaundries();
			return new Response<>(HttpStatus.OK, laundry);
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<>(HttpStatus.BAD_REQUEST, null);
		}
	}

	@GetMapping("/laundry/receipt/{receipt}")
	public Response<?> getByCode(@PathVariable("receipt") String receipt) {
		try {
			Laundries laundry = laundriesService.getLaundryByReceipt(receipt);
			return new Response<>(HttpStatus.OK, laundry);
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<>(HttpStatus.BAD_REQUEST, null);
		}
	}

	@PostMapping("/laundry")
	public Response<?> add(@RequestBody String body) {
		try {
			ObjectMapper obj = new ObjectMapper();
			obj.registerModule(new JavaTimeModule());
			LaundryHelper helper = obj.readValue(body, LaundryHelper.class);
			laundriesService.insertLaundry(helper.getLaundry(), helper.getLaundryDetailsList());
			return new Response<>(HttpStatus.CREATED, helper);
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
			} else if (e.getMessage().contains("Service")) {
				return new Response<>(HttpStatus.BAD_REQUEST, e.getMessage());
			} else if (e.getMessage().contains("Perfume")) {
				return new Response<>(HttpStatus.BAD_REQUEST, e.getMessage());
			} else {
				return new Response<>(HttpStatus.BAD_REQUEST, null);
			}
		}
	}

}
