package com.lawencon.laundry.dao;

import java.util.List;

import com.lawencon.laundry.model.Profiles;

/**
 * @author Imron Rosyadi
 */

public interface ProfilesDao {

	List<Profiles> getListProfiles() throws Exception;

	Profiles insertProfile(Profiles pro) throws Exception;

	Profiles loginProfile(String uname, String pass) throws Exception;

	Profiles findByCode(String code) throws Exception;

	void update(Profiles pro) throws Exception;

	void delete(Long id) throws Exception;

	Profiles getProfileById(Long id) throws Exception;

}
