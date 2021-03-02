package com.lawencon.laundry.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lawencon.laundry.model.Profiles;

/**
 * @author Imron Rosyadi
 */

@Repository
public interface ProfilesRepo extends JpaRepository<Profiles, Long> {

	@Query(value = "SELECT p.pro_code, p.pro_name, p.pro_phone, p.pro_address, u.uname"
			+ "	FROM tbl_m_profiles as p INNER JOIN tbl_m_users as u ON u.id = p.id_user", nativeQuery = true)
	List<Object[]> getAll() throws Exception;

	@Query(value = "SELECT * FROM tbl_m_profiles WHERE pro_code = ?1 ", nativeQuery = true)
	Profiles findByCode(String code) throws Exception;

	@Modifying
	@Query(value = "DELETE FROM tbl_m_profiles WHERE id = ?1 ", nativeQuery = true)
	void delete(Long id) throws Exception;

	@Modifying
	@Query(value = "UPDATE tbl_m_profiles SET pro_code = ?1, pro_name = ?2, pro_phone = ?3, pro_address = ?4, id_user = (SELECT id FROM tbl_m_users WHERE uname = ?5 ) WHERE id = ?6", nativeQuery = true)
	void update(String proCode, String proName, String proPhone, String proAddress, String username, Long id)
			throws Exception;

	@Query(value = "SELECT * FROM tbl_m_profiles WHERE id = ?1 ", nativeQuery = true)
	Profiles getProfileById(Long id) throws Exception;
}
