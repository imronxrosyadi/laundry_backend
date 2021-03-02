package com.lawencon.laundry.dao;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lawencon.laundry.model.Laundries;
import com.lawencon.laundry.model.LaundryDetails;
import com.lawencon.laundry.model.Pickups;
import com.lawencon.laundry.model.Profiles;
import com.lawencon.laundry.repo.PickupsRepo;

/**
 * @author Imron Rosyadi
 */

@Repository(value = "pickupNative")
public class PickUpsDaoJpaNativeImpl extends BaseDao implements PickUpsDao {

	@Autowired
	private PickupsRepo pickupsRepo;

	@Override
	public List<Pickups> getPickupList() throws Exception {
		List<Pickups> pickupList = new ArrayList<>();
		List<Object[]> listObj = pickupsRepo.getAll();
		listObj.forEach(objArr -> {
			Laundries l = new Laundries();
			l.setReceiptLaundry((String) objArr[0]);
			LaundryDetails ld = new LaundryDetails();
			ld.setIdLaundry(l);
			ld.setCodeDtl((String) objArr[1]);
			Pickups p = new Pickups();
			p.setIdLaundryDtl(ld);
			Timestamp pickUp = (Timestamp) objArr[2];
			p.setPickupDate((LocalDateTime) pickUp.toLocalDateTime());
			Profiles pf = new Profiles();
			pf.setProfileName((String) objArr[3]);
			p.setIdProfile(pf);

			pickupList.add(p);
		});

		return pickupList;
	}

	@Override
	public Pickups insertPickup(Pickups pickup) throws Exception {
		return pickupsRepo.save(pickup);
	}

	@Override
	public Pickups updateStatus(Pickups pickup) throws Exception {
		pickupsRepo.updateStatus(pickup.getIdLaundryDtl().getCodeDtl());
		return pickup;
	}

	@Override
	public Pickups findByDetailCode(String code) throws Exception {
		List<Pickups> pickupList = new ArrayList<>();
		List<Object[]> listObj = pickupsRepo.findByCode(code);
		listObj.forEach(objArr -> {
			Laundries l = new Laundries();
			l.setReceiptLaundry((String) objArr[0]);
			LaundryDetails ld = new LaundryDetails();
			ld.setIdLaundry(l);
			ld.setCodeDtl((String) objArr[1]);
			Pickups p = new Pickups();
			p.setIdLaundryDtl(ld);
			Timestamp pickUp = (Timestamp) objArr[2];
			p.setPickupDate((LocalDateTime) pickUp.toLocalDateTime());
			Profiles pf = new Profiles();
			pf.setProfileName((String) objArr[3]);
			p.setIdProfile(pf);

			pickupList.add(p);
		});
		return pickupList.size() > 0 ? pickupList.get(0) : null;
	}

}
