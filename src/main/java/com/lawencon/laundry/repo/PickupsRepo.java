package com.lawencon.laundry.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lawencon.laundry.model.Pickups;

/**
 * @author Imron Rosyadi
 */

@Repository
public interface PickupsRepo extends JpaRepository<Pickups, Long> {

	@Query(value = "SELECT hl.receipt_laundry, dl.code_dtl, p.pickup_date, pf.pro_name"
			+ " FROM tbl_r_pickups p INNER JOIN tbl_m_profiles pf ON pf.id = p.id_profile "
			+ " INNER JOIN tbl_r_dtl_laundries dl ON dl.id = p.id_dtl_laundry "
			+ " INNER JOIN tbl_r_hdr_laundries hl ON hl.id = dl.id_laundry", nativeQuery = true)
	List<Object[]> getAll() throws Exception;

	@Query(value = "SELECT hl.receipt_laundry, dl.code_dtl, p.pickup_date, pf.pro_name"
			+ " FROM tbl_r_pickups p INNER JOIN tbl_m_profiles pf ON pf.id = p.id_profile"
			+ " INNER JOIN tbl_r_dtl_laundries dl ON dl.id = p.id_dtl_laundry"
			+ " INNER JOIN tbl_r_hdr_laundries hl ON hl.id = dl.id_laundry"
			+ " WHERE dl.code_dtl = ? AND dl.id_status = (SELECT id FROM tbl_m_statuses WHERE stat_code = 'DONE')", nativeQuery = true)
	List<Object[]> findByCode(String code) throws Exception;

	@Modifying
	@Query(value = "UPDATE tbl_r_dtl_laundries SET id_status = (SELECT id FROM tbl_m_statuses WHERE stat_code = 'DONE') "
			+ " WHERE id = (SELECT id FROM tbl_r_dtl_laundries WHERE code_dtl = ?1)", nativeQuery = true)
	void updateStatus(String codeDtl) throws Exception;

}
