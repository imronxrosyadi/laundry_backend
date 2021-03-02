package com.lawencon.laundry.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.laundry.helper.Response;
import com.lawencon.laundry.model.LaundryDetails;
import com.lawencon.laundry.service.LaundryDetailsService;

/**
 * @author Imron Rosyadi
 */

@RestController
public class LaundryDetailsController {

	@Autowired
	private LaundryDetailsService laundryDetailsService;

	@GetMapping("/history/{code}")
	public Response<List<LaundryDetails>> getHistoryByCode(@PathVariable("code") String code) {
		try {
			List<LaundryDetails> laundryDtl = laundryDetailsService.getListLaundryDtlByCode(code);
			return new Response<>(HttpStatus.OK, laundryDtl);
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<>(HttpStatus.BAD_REQUEST, null);
		}
	}

	@GetMapping("/laundrydetail/all")
	public Response<List<LaundryDetails>> getAll() {
		try {
			List<LaundryDetails> laundryDtl = laundryDetailsService.getListLaundryDetails();
			return new Response<>(HttpStatus.OK, laundryDtl);
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<>(HttpStatus.BAD_REQUEST, null);
		}
	}

	@GetMapping("/laundrydetail/code/{code}")
	public Response<?> getByCode(@PathVariable("code") String code) {
		try {
			LaundryDetails laundryDtl = laundryDetailsService.getLaundryDetailByCode(code);
			return new Response<>(HttpStatus.OK, laundryDtl);
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<>(HttpStatus.BAD_REQUEST, null);
		}
	}

}
