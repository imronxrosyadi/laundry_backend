package com.lawencon.laundry.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lawencon.laundry.model.Perfumes;

/**
 * @author Imron Rosyadi
 */

@Repository
public interface PerfumesRepo extends JpaRepository<Perfumes, Long> {

	@Query(value = "SELECT p.perfume_code, p.perfume_name, pf.pro_name"
			+ "	FROM tbl_m_perfumes as p INNER JOIN tbl_m_profiles as pf ON pf.id = p.id_profile", nativeQuery = true)
	List<Object[]> getAll() throws Exception;

	@Query(value = "SELECT * FROM tbl_m_perfumes WHERE perfume_code = ? ", nativeQuery = true)
	Perfumes findByCode(String code) throws Exception;

	@Modifying
	@Query(value = "DELETE FROM tbl_m_perfumes WHERE id = ? ", nativeQuery = true)
	void delete(Long id) throws Exception;

	@Modifying
	@Query(value = "UPDATE tbl_m_perfumes SET perfume_code = ?1, perfume_name = ?2, id_profile = (SELECT id FROM tbl_m_profiles WHERE pro_code = ?3) WHERE id = ?4 ", nativeQuery = true)
	void update(String perfumeCode, String perfumeName, String profileCode, Long id) throws Exception;

	@Query(value = "SELECT * FROM tbl_m_perfumes WHERE id = ?1 ", nativeQuery = true)
	Perfumes getPerfumeById(Long id) throws Exception;
}
