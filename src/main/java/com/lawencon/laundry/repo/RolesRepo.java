package com.lawencon.laundry.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lawencon.laundry.model.Roles;

/**
 * @author Imron Rosyadi
 */

@Repository
public interface RolesRepo extends JpaRepository<Roles, Long> {

	@Query(value = "SELECT * FROM tbl_m_roles", nativeQuery = true)
	List<Roles> getAllRoles() throws Exception;

	@Query(value = "SELECT * FROM tbl_m_roles WHERE role_code = ?1 ", nativeQuery = true)
	Roles findByCode(String code) throws Exception;

	@Modifying
	@Query(value = "DELETE FROM tbl_m_roles WHERE id = ?1 ", nativeQuery = true)
	void delete(Long id) throws Exception;

	@Modifying
	@Query(value = "UPDATE tbl_m_roles SET role_code = ?1, role_name = ?2 WHERE id = ?3", nativeQuery = true)
	void updateRole(String roleCode, String roleName, Long id) throws Exception;

	@Query(value = "SELECT id FROM tbl_m_roles WHERE id = ?1 AND id IN (SELECT id_role FROM tbl_m_users)", nativeQuery = true)
	Long usedConstraint(Long id) throws Exception;

	@Query(value = "SELECT * FROM tbl_m_roles WHERE id = ?1 ", nativeQuery = true)
	Roles getRoleById(Long id) throws Exception;
}
