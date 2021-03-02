package com.lawencon.laundry.helper;

import java.util.List;

import com.lawencon.laundry.model.Laundries;
import com.lawencon.laundry.model.LaundryDetails;

/**
 * @author Imron Rosyadi
 */

public class LaundryHelper {

	private Laundries laundry;
	private List<LaundryDetails> laundryDetailsList;

	public Laundries getLaundry() {
		return laundry;
	}

	public void setLaundry(Laundries laundry) {
		this.laundry = laundry;
	}

	public List<LaundryDetails> getLaundryDetailsList() {
		return laundryDetailsList;
	}

	public void setLaundryDetailsList(List<LaundryDetails> laundryDetailsList) {
		this.laundryDetailsList = laundryDetailsList;
	}
}
