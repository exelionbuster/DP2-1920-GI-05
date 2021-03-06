package org.springframework.samples.petclinic.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Application;

public interface ApplicationRepository {

	Application findById(int id) throws DataAccessException;

	void save(Application application) throws DataAccessException;

	Collection<Application> findApplicationsByOwnerId(int ownerId) throws DataAccessException;
	
	List<Application> findAllApplications() throws DataAccessException;
}
