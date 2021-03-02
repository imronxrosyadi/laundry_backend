package com.lawencon.laundry.dao;

import java.util.List;

import com.lawencon.laundry.model.Pickups;

/**
 * @author Imron Rosyadi
 */

public interface PickUpsDao {

	List<Pickups> getPickupList() throws Exception;

	Pickups insertPickup(Pickups pickup) throws Exception;

	Pickups updateStatus(Pickups pickup) throws Exception;

	Pickups findByDetailCode(String code) throws Exception;

}
