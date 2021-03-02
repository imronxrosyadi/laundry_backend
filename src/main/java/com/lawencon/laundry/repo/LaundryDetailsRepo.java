package com.lawencon.laundry.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lawencon.laundry.model.LaundryDetails;

/**
 * @author Imron Rosyadi
 */

@Repository
public interface LaundryDetailsRepo extends JpaRepository<LaundryDetails, Long> {

	@Query(value = "SELECT dl.code_dtl, dl.desc_dtl, dl.unit_dtl, dl.price_dtl, s.service_name, s.service_price, p.perfume_name, st.stat_name "
			+ " FROM tbl_r_dtl_laundries dl INNER JOIN tbl_m_services s ON s.id = dl.id_service "
			+ " INNER JOIN tbl_m_perfumes p ON p.id = dl.id_perfume "
			+ " INNER JOIN tbl_m_statuses st ON st.id = dl.id_status", nativeQuery = true)
	List<Object[]> getAll() throws Exception;

	@Query(value = "SELECT dl.id, dl.code_dtl, dl.desc_dtl, dl.unit_dtl, dl.price_dtl, s.service_name, s.service_price, p.perfume_name, st.stat_name "
			+ " FROM tbl_r_dtl_laundries dl INNER JOIN tbl_m_services s ON s.id = dl.id_service "
			+ " INNER JOIN tbl_m_perfumes p ON p.id = dl.id_perfume "
			+ " INNER JOIN tbl_m_statuses st ON st.id = dl.id_status WHERE dl.code_dtl = ?1 ", nativeQuery = true)
	List<Object[]> findByCode(String code) throws Exception;

	@Query(value = "SELECT dl.code_dtl, dl.price_dtl, hl.receipt_laundry, c.cust_name, hl.start_laundry, hl.done_laundry, hl.total_price, "
			+ " p.pay_name, pf.pro_name, pfm.perfume_name, s.service_name, st.stat_name FROM tbl_r_hdr_laundries hl "
			+ " INNER JOIN tbl_m_customers c on c.id = hl.id_customer INNER JOIN tbl_m_payments p on p.id = hl.id_payment "
			+ " INNER JOIN tbl_m_profiles pf on pf.id = hl.id_profile INNER JOIN tbl_r_dtl_laundries dl on dl.id_laundry = hl.id "
			+ " INNER JOIN tbl_m_perfumes pfm on pfm.id = dl.id_perfume INNER JOIN tbl_m_services s on s.id = dl.id_service "
			+ " INNER JOIN tbl_m_statuses st on st.id = dl.id_status WHERE pf.pro_code = ?1 ", nativeQuery = true)
	List<Object[]> getListLaundryDtlByCode(String code) throws Exception;

}
