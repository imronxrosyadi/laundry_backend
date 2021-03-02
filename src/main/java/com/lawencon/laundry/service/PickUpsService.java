package com.lawencon.laundry.service;

import java.util.List;

import com.lawencon.laundry.model.Pickups;

/**
 * @author Imron Rosyadi
 */

public interface PickUpsService {

	List<Pickups> getPickupList() throws Exception;

	Pickups insertPickup(Pickups pickup) throws Exception;

	Pickups updateStatus(Pickups pickup) throws Exception;

	Pickups findByDetailCode(String code) throws Exception;

}
