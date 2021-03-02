package com.lawencon.laundry.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lawencon.laundry.model.Statuses;

/**
 * @author Imron Rosyadi
 */

@Repository
public interface StatusesRepo extends JpaRepository<Statuses, Long> {

	@Query(value = "SELECT s.stat_code, s.stat_name, pf.pro_name"
			+ "	FROM tbl_m_statuses as s INNER JOIN tbl_m_profiles as pf ON pf.id = s.id_profile", nativeQuery = true)
	List<Object[]> getAll() throws Exception;

	@Query(value = "SELECT * FROM tbl_m_statuses WHERE stat_code = ? ", nativeQuery = true)
	Statuses findByCode(String code) throws Exception;

	@Modifying
	@Query(value = "DELETE FROM tbl_m_statuses WHERE id = ? ", nativeQuery = true)
	void delete(Long id) throws Exception;

	@Modifying
	@Query(value = "UPDATE tbl_m_statuses SET stat_code = ?1, stat_name = ?2, id_profile = (SELECT id FROM tbl_m_profiles WHERE pro_code = ?3) WHERE id = ?4 ", nativeQuery = true)
	void update(String statCode, String statName, String profileCode, Long id) throws Exception;

	@Query(value = "SELECT * FROM tbl_m_statuses WHERE id = ?1 ", nativeQuery = true)
	Statuses getStatusById(Long id) throws Exception;
}
