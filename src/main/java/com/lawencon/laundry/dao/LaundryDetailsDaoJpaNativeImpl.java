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
import com.lawencon.laundry.model.LaundryDetails;
import com.lawencon.laundry.model.Payments;
import com.lawencon.laundry.model.Perfumes;
import com.lawencon.laundry.model.Profiles;
import com.lawencon.laundry.model.Services;
import com.lawencon.laundry.model.Statuses;
import com.lawencon.laundry.repo.LaundryDetailsRepo;

/**
 * @author Imron Rosyadi
 */

@Repository(value = "laundrydetailNative")
public class LaundryDetailsDaoJpaNativeImpl extends BaseDao implements LaundryDetailsDao {

	@Autowired
	private LaundryDetailsRepo laundryDetailsRepo;

	@Override
	public LaundryDetails insertLaundryDtl(Laundries laundry, LaundryDetails laundryDtl) throws Exception {
		return laundryDetailsRepo.save(laundryDtl);
	}

	@Override
	public List<LaundryDetails> getListLaundryDetails() throws Exception {
		List<LaundryDetails> ldrDtlList = new ArrayList<>();
		List<Object[]> listObj = laundryDetailsRepo.getAll();
		listObj.forEach(val -> {
			Object[] objArr = (Object[]) val;
			LaundryDetails ld = new LaundryDetails();
			ld.setCodeDtl((String) objArr[0]);
			ld.setDescDtl((String) objArr[1]);
			ld.setUnitDtl((Integer) objArr[2]);
			ld.setPriceDtl((BigDecimal) objArr[3]);

			Services s = new Services();
			s.setServiceName((String) objArr[4]);
			s.setServicePrice((BigDecimal) objArr[5]);
			ld.setIdService(s);

			Perfumes p = new Perfumes();
			p.setPerfumeName((String) objArr[6]);
			ld.setIdPerfume(p);

			Statuses st = new Statuses();
			st.setStatusName((String) objArr[7]);
			ld.setIdStatus(st);

			ldrDtlList.add(ld);
		});
		return ldrDtlList;
	}

	@Override
	public BigDecimal updatePrice(LaundryDetails ldrDtl) throws Exception {
		return null;
	}

	@Override
	public LaundryDetails getLaundryDetailByCode(String code) throws Exception {
		List<LaundryDetails> ldrDtlList = new ArrayList<>();
		List<Object[]> listObj = laundryDetailsRepo.findByCode(code);
		listObj.forEach(val -> {
			Object[] objArr = (Object[]) val;
			LaundryDetails ld = new LaundryDetails();
			ld.setId(Long.valueOf(objArr[0].toString()));
			ld.setCodeDtl((String) objArr[1]);
			ld.setDescDtl((String) objArr[2]);
			ld.setUnitDtl((Integer) objArr[3]);
			ld.setPriceDtl((BigDecimal) objArr[4]);

			Services s = new Services();
			s.setServiceName((String) objArr[5]);
			s.setServicePrice((BigDecimal) objArr[6]);
			ld.setIdService(s);

			Perfumes p = new Perfumes();
			p.setPerfumeName((String) objArr[7]);
			ld.setIdPerfume(p);

			Statuses st = new Statuses();
			st.setStatusName((String) objArr[8]);
			ld.setIdStatus(st);

			ldrDtlList.add(ld);
		});
		return ldrDtlList.size() > 0 ? ldrDtlList.get(0) : null;
	}

	@Override
	public List<LaundryDetails> getListLaundryDtlByCode(String code) throws Exception {
		List<LaundryDetails> ldrDtlList = new ArrayList<>();
		List<Object[]> listObj = laundryDetailsRepo.getListLaundryDtlByCode(code);
		listObj.forEach(val -> {
			Object[] objArr = (Object[]) val;
			LaundryDetails ld = new LaundryDetails();
			ld.setCodeDtl((String) objArr[0]);
			ld.setPriceDtl((BigDecimal) objArr[1]);

			Laundries laundry = new Laundries();
			laundry.setReceiptLaundry((String) objArr[2]);

			Customers c = new Customers();
			c.setCustName((String) objArr[3]);
			laundry.setIdCustomer(c);
			Timestamp startDate = (Timestamp) objArr[4];
			laundry.setStartLaundry((LocalDateTime) startDate.toLocalDateTime());
			Timestamp doneDate = (Timestamp) objArr[5];
			laundry.setDoneLaundry((LocalDateTime) doneDate.toLocalDateTime());
			laundry.setTotalPrice((BigDecimal) objArr[6]);

			Payments pay = new Payments();
			pay.setPayName((String) objArr[7]);
			laundry.setIdPayment(pay);

			Profiles pro = new Profiles();
			pro.setProfileName((String) objArr[8]);
			laundry.setIdProfile(pro);

			Perfumes p = new Perfumes();
			p.setPerfumeName((String) objArr[9]);
			ld.setIdPerfume(p);

			Services s = new Services();
			s.setServiceName((String) objArr[10]);
			ld.setIdService(s);

			Statuses st = new Statuses();
			st.setStatusName((String) objArr[11]);
			ld.setIdStatus(st);
			ld.setIdLaundry(laundry);

			ldrDtlList.add(ld);
		});
		return ldrDtlList;
	}

}
