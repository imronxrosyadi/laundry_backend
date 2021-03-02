package com.lawencon.laundry.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawencon.laundry.dao.PickUpsDao;
import com.lawencon.laundry.model.LaundryDetails;
import com.lawencon.laundry.model.Pickups;
import com.lawencon.laundry.model.Profiles;

/**
 * @author Imron Rosyadi
 */

@Service
@Transactional
public class PickUpsServiceImpl extends BaseService implements PickUpsService {

	private PickUpsDao pickUpsDao;
	private LaundryDetailsService laundryDetailsService;
	private ProfilesService profilesService;

	@Autowired
	public void setProfilesService(ProfilesService profilesService) {
		this.profilesService = profilesService;
	}

	@Autowired
	public void setLaundryDetailsService(LaundryDetailsService laundryDetailsService) {
		this.laundryDetailsService = laundryDetailsService;
	}

	@Autowired
	@Qualifier(value = "pickupNative")
	public void setPickUpsDao(PickUpsDao pickUpsDao) {
		this.pickUpsDao = pickUpsDao;
	}

	@Override
	public List<Pickups> getPickupList() throws Exception {
		return pickUpsDao.getPickupList();
	}

	@Override
	public Pickups insertPickup(Pickups pickup) throws Exception {
		validateInsertPickup(pickup);
		LaundryDetails ld = laundryDetailsService.getLaundryDetailByCode(pickup.getIdLaundryDtl().getCodeDtl());
		Profiles pro = profilesService.findByCode(pickup.getIdProfile().getProfileCode());
		pickup.setIdLaundryDtl(ld);
		pickup.setIdProfile(pro);
		pickUpsDao.insertPickup(pickup);
		updateStatus(pickup);
		return pickup;
	}

	@Override
	public Pickups updateStatus(Pickups pickup) throws Exception {
		return pickUpsDao.updateStatus(pickup);
	}

	@Override
	public Pickups findByDetailCode(String code) throws Exception {
		return pickUpsDao.findByDetailCode(code);
	}

	private void validateInsertPickup(Pickups pickup) throws Exception {
		if (pickup.getId() != null) {
			throw new Exception("Invalid input, column id must be empty!");
		} else if (pickup.getPickupDate() == null) {
			throw new Exception("Invalid input, column pickup date cant be empty!");
		} else if (pickup.getIdLaundryDtl().getCodeDtl() == null
				|| pickup.getIdLaundryDtl().getCodeDtl().trim().equals("")) {
			throw new Exception("Invalid input, column code detail cant be empty!");
		} else if (pickup.getIdProfile().getProfileCode() == null
				|| pickup.getIdProfile().getProfileCode().trim().equals("")) {
			throw new Exception("Invalid input, column profile code cant be empty!");
		}
		LaundryDetails ld = laundryDetailsService.getLaundryDetailByCode(pickup.getIdLaundryDtl().getCodeDtl());
		Profiles pro = profilesService.findByCode(pickup.getIdProfile().getProfileCode());
		if (ld == null) {
			throw new Exception("Laundry Detail code is doesnt exist!");
		} else if (pro == null) {
			throw new Exception("Profile code is doesnt exist!");
		}
	}

}
