package com.lawencon.laundry.service;

import java.util.List;

import com.lawencon.laundry.model.Profiles;

/**
 * @author Imron Rosyadi
 */

public interface ProfilesService {

	Profiles loginProfile(String uname, String pass) throws Exception;

	Profiles insertProfile(Profiles pro) throws Exception;

	List<Profiles> getListProfiles() throws Exception;

	Profiles findByCode(String code) throws Exception;

	void update(Profiles pro) throws Exception;

	void delete(Long id) throws Exception;

}
