package com.lawencon.laundry.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lawencon.laundry.model.Customers;
import com.lawencon.laundry.model.Laundries;
import com.lawencon.laundry.model.Payments;
import com.lawencon.laundry.model.Profiles;
import com.lawencon.laundry.repo.LaundriesRepo;

/**
 * @author Imron Rosyadi
 */

@Repository(value = "laundryNative")
public class LaundriesDaoJpaNativeImpl extends BaseDao implements LaundriesDao {

	@Autowired
	private LaundriesRepo laundriesRepo;

	@Override
	public Laundries insertLaundry(Laundries laundry) throws Exception {
		return laundriesRepo.save(laundry);
	}

	@Override
	public List<Laundries> getListLaundries() throws Exception {
		List<Laundries> listLaundries = new ArrayList<>();
		List<Object[]> listObj = laundriesRepo.getAll();
		listObj.forEach(objArr -> {
			Laundries l = new Laundries();
			l.setReceiptLaundry((String) objArr[0]);

			Customers c = new Customers();
			c.setCustName((String) objArr[1]);
			l.setIdCustomer(c);
			Timestamp startL = (Timestamp) objArr[2];
			Timestamp doneL = (Timestamp) objArr[3];
			l.setStartLaundry((LocalDateTime) startL.toLocalDateTime());
			l.setDoneLaundry((LocalDateTime) doneL.toLocalDateTime());
			l.setTotalPrice((BigDecimal) objArr[4]);
			Payments p = new Payments();
			p.setPayName((String) objArr[5]);
			l.setIdPayment(p);
			Profiles pf = new Profiles();
			pf.setProfileName((String) objArr[6]);
			l.setIdProfile(pf);

			listLaundries.add(l);
		});

		return listLaundries;
	}

	@Override
	public Long updateTotalPrice(Laundries laundry) throws Exception {
		laundriesRepo.updateTotalPrice(laundry.getId());
		return laundry.getId();
	}

	@Override
	public Laundries getLaundryByReceipt(String receipt) throws Exception {
		List<Laundries> listLaundries = new ArrayList<>();
		List<Object[]> listObj = laundriesRepo.findByReceipt(receipt);
		listObj.forEach(objArr -> {
			Laundries l = new Laundries();
			l.setId(Long.valueOf(objArr[0].toString()));
			l.setReceiptLaundry((String) objArr[1]);

			Customers c = new Customers();
			c.setCustName((String) objArr[2]);
			l.setIdCustomer(c);
			Timestamp startL = (Timestamp) objArr[3];
			Timestamp doneL = (Timestamp) objArr[4];
			l.setStartLaundry((LocalDateTime) startL.toLocalDateTime());
			l.setDoneLaundry((LocalDateTime) doneL.toLocalDateTime());
			l.setTotalPrice((BigDecimal) objArr[5]);
			Payments p = new Payments();
			p.setPayName((String) objArr[6]);
			l.setIdPayment(p);
			Profiles pf = new Profiles();
			pf.setProfileName((String) objArr[7]);
			l.setIdProfile(pf);

			listLaundries.add(l);
		});
		return listLaundries.size() > 0 ? listLaundries.get(0) : null;
	}

}
