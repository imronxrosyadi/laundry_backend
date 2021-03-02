package com.lawencon.laundry.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lawencon.laundry.model.Payments;

/**
 * @author Imron Rosyadi
 */

@Repository
public interface PaymentsRepo extends JpaRepository<Payments, Long> {

	@Query(value = "SELECT p.pay_code, p.pay_name, pf.pro_name"
			+ "	FROM tbl_m_payments as p INNER JOIN tbl_m_profiles as pf ON pf.id = p.id_profile", nativeQuery = true)
	List<Object[]> getAll() throws Exception;

	@Query(value = "SELECT * FROM tbl_m_payments WHERE pay_code = ? ", nativeQuery = true)
	Payments findByCode(String code) throws Exception;

	@Modifying
	@Query(value = "DELETE FROM tbl_m_payments WHERE id = ? ", nativeQuery = true)
	void delete(Long id) throws Exception;

	@Modifying
	@Query(value = "UPDATE tbl_m_payments SET pay_code = ?1, pay_name = ?2, id_profile = (SELECT id FROM tbl_m_profiles WHERE pro_code = ?3) WHERE id = ?4 ", nativeQuery = true)
	void update(String payCode, String payName, String profileCode, Long id) throws Exception;

	@Query(value = "SELECT * FROM tbl_m_payments WHERE id = ?1 ", nativeQuery = true)
	Payments getPaymentById(Long id) throws Exception;

}
