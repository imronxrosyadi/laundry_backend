package com.lawencon.laundry.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lawencon.laundry.model.Laundries;

/**
 * @author Imron Rosyadi
 */

@Repository
public interface LaundriesRepo extends JpaRepository<Laundries, Long> {

	@Query(value = "SELECT hl.receipt_laundry, c.cust_name, hl.start_laundry, hl.done_laundry, hl.total_price, p.pay_name, pf.pro_name"
			+ " FROM tbl_r_hdr_laundries hl INNER JOIN tbl_m_customers c on c.id = hl.id_customer"
			+ " INNER JOIN tbl_m_payments p on p.id = hl.id_payment"
			+ " INNER JOIN tbl_m_profiles pf on pf.id = hl.id_profile", nativeQuery = true)
	List<Object[]> getAll() throws Exception;

	@Query(value = "SELECT hl.id, hl.receipt_laundry, c.cust_name, hl.start_laundry, hl.done_laundry, hl.total_price, p.pay_name, pf.pro_name"
			+ " FROM tbl_r_hdr_laundries hl INNER JOIN tbl_m_customers c on c.id = hl.id_customer"
			+ " INNER JOIN tbl_m_payments p on p.id = hl.id_payment"
			+ " INNER JOIN tbl_m_profiles pf on pf.id = hl.id_profile WHERE hl.receipt_laundry = ?", nativeQuery = true)
	List<Object[]> findByReceipt(String receipt) throws Exception;

	@Modifying
	@Query(value = "UPDATE tbl_r_hdr_laundries SET total_price = (SELECT SUM(dl.price_dtl) "
			+ " FROM tbl_r_dtl_laundries dl INNER JOIN tbl_r_hdr_laundries hl ON hl.id = dl.id_laundry "
			+ " WHERE hl.id = ?1 ) WHERE id = ?1 ", nativeQuery = true)
	void updateTotalPrice(Long id) throws Exception;

}
