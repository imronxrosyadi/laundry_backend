package com.lawencon.laundry.dao;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.laundry.model.Laundries;
import com.lawencon.laundry.model.LaundryDetails;
import com.lawencon.laundry.model.Pickups;
import com.lawencon.laundry.model.Profiles;

/**
 * @author Imron Rosyadi
 */

@Repository(value = "pickup")
public class PickUpsDaoImpl extends BaseDao implements PickUpsDao {

	@Override
	public List<Pickups> getPickupList() throws Exception {
		List<Pickups> pickupList = new ArrayList<>();
		String sql = bBuilder("SELECT hl.receipt_laundry, dl.code_dtl, p.pickup_date, pf.pro_name",
				" FROM tbl_r_pickups p INNER JOIN tbl_m_profiles pf ON pf.id = p.id_profile",
				" INNER JOIN tbl_r_dtl_laundries dl ON dl.id = p.id_dtl_laundry",
				" INNER JOIN tbl_r_hdr_laundries hl ON hl.id = dl.id_laundry").toString();
		List<?> listObj = entityManager.createNativeQuery(sql).getResultList();
		listObj.forEach(val -> {
			Object[] objArr = (Object[]) val;
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
		String sql = bBuilder("INSERT INTO tbl_r_pickups (id_dtl_laundry, pickup_date, id_profile)",
				"	VALUES ((SELECT id FROM tbl_r_dtl_laundries WHERE code_dtl = ?),",
				"	?, (SELECT id FROM tbl_m_profiles WHERE pro_code = ?)) RETURNING id").toString();
		List<?> listResult = entityManager.createNativeQuery(sql).setParameter(1, pickup.getIdLaundryDtl().getCodeDtl())
				.setParameter(2, pickup.getPickupDate()).setParameter(3, pickup.getIdProfile().getProfileCode())
				.getResultList();

		Long idPickup = listResult.size() > 0 ? Long.valueOf(listResult.get(0).toString()) : null;
		pickup.setId(idPickup);
		return pickup;
	}

	@Override
	public Pickups updateStatus(Pickups pickup) throws Exception {
		String sql = bBuilder("UPDATE tbl_r_dtl_laundries",
				" SET id_status = (SELECT id FROM tbl_m_statuses WHERE stat_code = 'DONE')",
				" WHERE id = (SELECT id FROM tbl_r_dtl_laundries WHERE code_dtl = ?)").toString();
		entityManager.createNativeQuery(sql).setParameter(1, pickup.getIdLaundryDtl().getCodeDtl()).executeUpdate();

		return pickup;
	}

	@Override
	public Pickups findByDetailCode(String code) throws Exception {
		List<Pickups> pickupList = new ArrayList<>();
		String sql = bBuilder("SELECT hl.receipt_laundry, dl.code_dtl, p.pickup_date, pf.pro_name",
				" FROM tbl_r_pickups p INNER JOIN tbl_m_profiles pf ON pf.id = p.id_profile",
				" INNER JOIN tbl_r_dtl_laundries dl ON dl.id = p.id_dtl_laundry",
				" INNER JOIN tbl_r_hdr_laundries hl ON hl.id = dl.id_laundry",
				" WHERE dl.code_dtl = ? AND dl.id_status = (SELECT id FROM tbl_m_statuses WHERE stat_code = 'DONE')")
						.toString();
		List<?> listObj = entityManager.createNativeQuery(sql).setParameter(1, code).getResultList();
		listObj.forEach(val -> {
			Object[] objArr = (Object[]) val;
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
