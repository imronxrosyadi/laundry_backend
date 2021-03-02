package com.lawencon.laundry.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lawencon.laundry.model.Customers;

/**
 * @author Imron Rosyadi
 */

@Repository
public interface CustomersRepo extends JpaRepository<Customers, Long> {

	@Query(value = "SELECT c.cust_code, c.cust_name, c.cust_phone, c.cust_address, p.pro_name"
			+ "	FROM tbl_m_customers as c INNER JOIN tbl_m_profiles as p ON p.id = c.id_profile", nativeQuery = true)
	List<Object[]> getAll() throws Exception;

	@Query(value = "SELECT * FROM tbl_m_customers WHERE cust_code = ? ", nativeQuery = true)
	Customers findByCode(String code) throws Exception;

	@Modifying
	@Query(value = "DELETE FROM tbl_m_customers WHERE id = ? ", nativeQuery = true)
	void delete(Long id) throws Exception;

	@Modifying
	@Query(value = "UPDATE tbl_m_customers SET cust_code = ?1, cust_name = ?2, cust_phone = ?3, cust_address = ?4, "
			+ " id_profile = (SELECT id FROM tbl_m_profiles WHERE pro_code = ?5) WHERE id = ?6 ", nativeQuery = true)
	void update(String custCode, String custName, String custPhone, String custAddress, String profileCode, Long id)
			throws Exception;

	@Query(value = "SELECT * FROM tbl_m_customers WHERE id = ?1 ", nativeQuery = true)
	Customers getCustomerById(Long id) throws Exception;
}
