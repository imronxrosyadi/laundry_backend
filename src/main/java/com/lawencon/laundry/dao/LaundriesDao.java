package com.lawencon.laundry.dao;

import java.util.List;

import com.lawencon.laundry.model.Laundries;

/**
 * @author Imron Rosyadi
 */

public interface LaundriesDao {

	List<Laundries> getListLaundries() throws Exception;

	Laundries getLaundryByReceipt(String receipt) throws Exception;

	Laundries insertLaundry(Laundries laundry) throws Exception;

	Long updateTotalPrice(Laundries laundry) throws Exception;

}
