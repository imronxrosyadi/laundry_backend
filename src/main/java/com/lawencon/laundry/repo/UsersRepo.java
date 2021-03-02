package com.lawencon.laundry.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lawencon.laundry.model.Users;

/**
 * @author Imron Rosyadi
 */

@Repository
public interface UsersRepo extends JpaRepository<Users, Long> {

	@Query(value = "SELECT u.uname, u.pass, r.role_name FROM tbl_m_users u INNER JOIN tbl_m_roles r ON r.id = u.id_role", nativeQuery = true)
	List<Object[]> getAllUsers() throws Exception;

	@Query(value = "SELECT * FROM tbl_m_users WHERE uname = ? ", nativeQuery = true)
	Users findByUsername(String uname) throws Exception;

	@Modifying
	@Query(value = "DELETE FROM tbl_m_users WHERE id = ? ", nativeQuery = true)
	void delete(Long id) throws Exception;

	@Modifying
	@Query(value = "UPDATE tbl_m_users SET uname = ?, pass = ?, id_role = (SELECT id FROM tbl_m_roles WHERE role_code = ?) WHERE id = ? ", nativeQuery = true)
	void updateUser(String uname, String pass, String roleCode, Long id) throws Exception;

	@Query(value = "SELECT id FROM tbl_m_users WHERE id = ?1 AND id IN (SELECT id_user FROM tbl_m_profiles)", nativeQuery = true)
	Long usedConstraint(Long id) throws Exception;

	@Query(value = "SELECT * FROM tbl_m_users WHERE id = ?1 ", nativeQuery = true)
	Users getUserById(Long id) throws Exception;
}
