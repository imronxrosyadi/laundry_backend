package com.lawencon.laundry.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.laundry.model.Profiles;
import com.lawencon.laundry.model.Services;

/**
 * @author Imron Rosyadi
 */

@Repository(value = "service")
public class ServicesDaoImpl extends BaseDao implements ServicesDao {

	@Override
	public List<Services> getListServices() throws Exception {
		String sql = bBuilder(
				"SELECT s.service_code, s.service_name, s.service_price, p.pro_name FROM tbl_m_services s",
				" INNER JOIN tbl_m_profiles p ON p.id = s.id_profile ").toString();
		List<Services> servicesList = new ArrayList<>();
		List<?> listObj = entityManager.createNativeQuery(sql).getResultList();
		listObj.forEach(val -> {
			Object[] objArr = (Object[]) val;
			Services s = new Services();
			s.setServiceCode((String) objArr[0]);
			s.setServiceName((String) objArr[1]);
			s.setServicePrice((BigDecimal) objArr[2]);

			Profiles p = new Profiles();
			p.setProfileName((String) objArr[3]);
			s.setIdProfile(p);

			servicesList.add(s);
		});
		return servicesList;
	}

	@Override
	public Services getServiceByCode(String code) throws Exception {
		String sql = bBuilder(
				"SELECT s.service_code, s.service_name, s.service_price, p.pro_name FROM tbl_m_services s",
				" INNER JOIN tbl_m_profiles p ON p.id = s.id_profile WHERE s.service_code = ?").toString();
		List<Services> servicesList = new ArrayList<>();
		List<?> listObj = entityManager.createNativeQuery(sql).setParameter(1, code).getResultList();
		listObj.forEach(val -> {
			Object[] objArr = (Object[]) val;
			Services s = new Services();
			s.setServiceCode((String) objArr[0]);
			s.setServiceName((String) objArr[1]);
			s.setServicePrice((BigDecimal) objArr[2]);

			Profiles p = new Profiles();
			p.setProfileName((String) objArr[3]);
			s.setIdProfile(p);

			servicesList.add(s);
		});
		return servicesList.size() > 0 ? servicesList.get(0) : null;
	}

	@Override
	public void insertServices(Services service) throws Exception {
		String sql = bBuilder("INSERT INTO tbl_m_services (service_code, service_name, service_price, id_profile) ",
				" VALUES (?, ?, ?, (SELECT id FROM tbl_m_profiles WHERE pro_code = ?)) ").toString();
		entityManager.createNativeQuery(sql).setParameter(1, service.getServiceCode())
				.setParameter(2, service.getServiceName()).setParameter(3, service.getServicePrice())
				.setParameter(4, service.getIdProfile().getProfileCode()).executeUpdate();
	}

	@Override
	public void update(Services service) throws Exception {
		String sql = bBuilder(
				"UPDATE tbl_m_services SET service_code = ?, service_name = ?, service_price = ?, id_profile = ",
				" (SELECT id FROM tbl_m_profiles WHERE pro_code = ?) WHERE id = ? ").toString();
		entityManager.createNativeQuery(sql).setParameter(1, service.getServiceCode())
				.setParameter(2, service.getServiceName()).setParameter(3, service.getServicePrice())
				.setParameter(4, service.getIdProfile().getProfileCode()).setParameter(5, service.getId())
				.executeUpdate();
	}

	@Override
	public void delete(Long id) throws Exception {
		String sql = "DELETE FROM tbl_m_services WHERE id = ?";
		entityManager.createNativeQuery(sql).setParameter(1, id).executeUpdate();
	}

	@Override
	public Services getServiceById(Long id) throws Exception {
		String sql = "SELECT * FROM tbl_m_services WHERE id = ?1";
		entityManager.createNativeQuery(sql).setParameter(1, id).executeUpdate();
		return null;
	}

}
