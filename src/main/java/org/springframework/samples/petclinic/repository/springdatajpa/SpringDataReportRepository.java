package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.Report;
import org.springframework.samples.petclinic.repository.ReportRepository;

public interface SpringDataReportRepository extends ReportRepository, Repository<Report, Integer> {

}
