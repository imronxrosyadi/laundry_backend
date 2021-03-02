package com.lawencon.laundry.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.lawencon.laundry.dao.LaundryDetailsDao;
import com.lawencon.laundry.model.Laundries;
import com.lawencon.laundry.model.LaundryDetails;
import com.lawencon.laundry.model.Perfumes;
import com.lawencon.laundry.model.Services;
import com.lawencon.laundry.model.Statuses;

/**
 * @author Imron Rosyadi
 */

@Service
public class LaundryDetailsServiceImpl extends BaseService implements LaundryDetailsService {

	private LaundryDetailsDao laundryDetailsDao;
	private ServicesService servicesService;
	private LaundriesService laundriesService;
	private PerfumesService perfumesService;
	private StatusesService statusesService;

	@Autowired
	public void setLaundriesService(LaundriesService laundriesService) {
		this.laundriesService = laundriesService;
	}

	@Autowired
	public void setPerfumesService(PerfumesService perfumesService) {
		this.perfumesService = perfumesService;
	}

	@Autowired
	public void setStatusesService(StatusesService statusesService) {
		this.statusesService = statusesService;
	}

	@Autowired
	public void setServicesService(ServicesService servicesService) {
		this.servicesService = servicesService;
	}

	@Autowired
	@Qualifier(value = "laundrydetailNative")
	public void setLaundryDetailsDao(LaundryDetailsDao laundryDetailsDao) {
		this.laundryDetailsDao = laundryDetailsDao;
	}

	@Override
	public LaundryDetails insertLaundryDtl(Laundries laundry, LaundryDetails laundryDtl) throws Exception {
		validateInsertDetailLaundry(laundryDtl);
		Laundries lndry = laundriesService.getLaundryByReceipt(laundry.getReceiptLaundry());
		Perfumes pfm = perfumesService.getByCode(laundryDtl.getIdPerfume().getPerfumeCode());
		Services service = servicesService.getServiceByCode(laundryDtl.getIdService().getServiceCode());
		Statuses stat = statusesService.getByCode("PEND");
		BigDecimal servPrice = service.getServicePrice();
		Integer unit = laundryDtl.getUnitDtl();
		BigDecimal totPrice = servPrice.multiply(new BigDecimal(unit));
		laundryDtl.setIdLaundry(lndry);
		laundryDtl.setIdPerfume(pfm);
		laundryDtl.setIdService(service);
		laundryDtl.setIdStatus(stat);
		laundryDtl.setCodeDtl(generateCode());
		laundryDtl.setPriceDtl(totPrice);
		return laundryDetailsDao.insertLaundryDtl(laundry, laundryDtl);
	}

	@Override
	public List<LaundryDetails> getListLaundryDetails() throws Exception {
		return laundryDetailsDao.getListLaundryDetails();
	}

	@Override
	public LaundryDetails getLaundryDetailByCode(String code) throws Exception {
		return laundryDetailsDao.getLaundryDetailByCode(code);
	}

	@Override
	public BigDecimal updatePrice(LaundryDetails ldrDtl) throws Exception {
		return laundryDetailsDao.updatePrice(ldrDtl);
	}

	@Override
	public String generateCode() throws Exception {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String date = LocalDateTime.now().format(formatter);
		String[] dateSplited = date.split(" ");
		String[] timeSplited = dateSplited[1].split(":");
		String b = timeSplited[2];
		Random rand = new Random();
		int randomNum = rand.nextInt((99 - 01) + 1) + 01;
		StringBuilder receipt = new StringBuilder();
		receipt.append("DC").append(b).append(randomNum);
		return receipt.toString();
	}

	@Override
	public List<LaundryDetails> getListLaundryDtlByCode(String code) throws Exception {
		return laundryDetailsDao.getListLaundryDtlByCode(code);
	}

	private void validateInsertDetailLaundry(LaundryDetails laundryDtl) throws Exception {
		if (laundryDtl.getId() != null) {
			throw new Exception("Invalid input, column id cant be empty!");
		} else if (laundryDtl.getUnitDtl() == null) {
			throw new Exception("Invalid input, column unit detail cant be empty!");
		} else if (laundryDtl.getIdService() == null) {
			throw new Exception("Invalid input, column id service cant be empty!");
		} else if (laundryDtl.getIdService().getServiceCode() == null
				|| laundryDtl.getIdService().getServiceCode().trim().equals("")) {
			throw new Exception("Invalid input, column service code cant be empty!");
		} else if (laundryDtl.getIdPerfume() == null) {
			throw new Exception("Invalid input, column id perfume cant be empty!");
		} else if (laundryDtl.getIdPerfume().getPerfumeCode() == null
				|| laundryDtl.getIdPerfume().getPerfumeCode().trim().equals("")) {
			throw new Exception("Invalid input, column perfume code cant be empty!");
		}
		Services service = servicesService.getServiceByCode(laundryDtl.getIdService().getServiceCode());
		Perfumes perfume = perfumesService.getByCode(laundryDtl.getIdPerfume().getPerfumeCode());
		if (service == null) {
			throw new Exception("Service code is doesnt exist!");
		}
		if (perfume == null) {
			throw new Exception("Perfume code is doesnt exist!");
		}
	}

}
