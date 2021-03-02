package com.lawencon.laundry.repo;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lawencon.laundry.model.Services;

/**
 * @author Imron Rosyadi
 */

@Repository
public interface ServicesRepo extends JpaRepository<Services, Long> {

	@Query(value = "SELECT s.service_code, s.service_name, s.service_price, pf.pro_name"
			+ "	FROM tbl_m_services as s INNER JOIN tbl_m_profiles as pf ON pf.id = s.id_profile", nativeQuery = true)
	List<Object[]> getAll() throws Exception;

	@Query(value = "SELECT * FROM tbl_m_services WHERE service_code = ? ", nativeQuery = true)
	Services findByCode(String code) throws Exception;

	@Modifying
	@Query(value = "DELETE FROM tbl_m_services WHERE id = ? ", nativeQuery = true)
	void delete(Long id) throws Exception;

	@Modifying
	@Query(value = "UPDATE tbl_m_services SET service_code = ?1, service_name = ?2, service_price = ?3, id_profile = (SELECT id FROM tbl_m_profiles WHERE pro_code = ?4) WHERE id = ?5 ", nativeQuery = true)
	void update(String serviceCode, String serviceName, BigDecimal servicePrice, String profileCode, Long id)
			throws Exception;

	@Query(value = "SELECT * FROM tbl_m_services WHERE id = ?1 ", nativeQuery = true)
	Services getServiceById(Long id) throws Exception;

}
