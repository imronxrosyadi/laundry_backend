package com.lawencon.laundry.service;

import java.math.BigDecimal;
import java.util.List;

import com.lawencon.laundry.model.Laundries;
import com.lawencon.laundry.model.LaundryDetails;

/**
 * @author Imron Rosyadi
 */

public interface LaundryDetailsService {

	List<LaundryDetails> getListLaundryDetails() throws Exception;

	List<LaundryDetails> getListLaundryDtlByCode(String code) throws Exception;

	LaundryDetails getLaundryDetailByCode(String code) throws Exception;

	LaundryDetails insertLaundryDtl(Laundries laundry, LaundryDetails laundryDtl) throws Exception;

	BigDecimal updatePrice(LaundryDetails ldrDtl) throws Exception;

	String generateCode() throws Exception;

}
