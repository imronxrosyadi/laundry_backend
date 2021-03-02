package com.lawencon.laundry.service;

import java.util.List;

import com.lawencon.laundry.model.Laundries;
import com.lawencon.laundry.model.LaundryDetails;

/**
 * @author Imron Rosyadi
 */

public interface LaundriesService {

	List<Laundries> getListLaundries() throws Exception;

	Laundries getLaundryByReceipt(String receipt) throws Exception;

	Laundries insertLaundry(Laundries laundry, List<LaundryDetails> ldrDtlList) throws Exception;

	Long updateTotalPrice(Laundries laundry) throws Exception;

	String generateReceipt() throws Exception;

}
