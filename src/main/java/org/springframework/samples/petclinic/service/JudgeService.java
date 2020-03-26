package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Guide;
import org.springframework.samples.petclinic.model.Judge;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.repository.JudgeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JudgeService {
	
	private JudgeRepository judgeRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthoritiesService authoritiesService;
	
	@Autowired
	public JudgeService(JudgeRepository jugdeRepository) {
		this.judgeRepository = jugdeRepository;
	}
	
	@Transactional(readOnly = true)
	public Collection<Judge> findAllJudges() throws DataAccessException {
		return judgeRepository.findAllJudges();
	}
	
	@Transactional
	public void saveJudge(Judge judge) throws DataAccessException {
		//creating owner
		judgeRepository.save(judge);		
		//creating user
		userService.saveUser(judge.getUser());
		//creating authorities
		authoritiesService.saveAuthorities(judge.getUser().getUsername(), "judge");
	}

	public Judge findJudgeById(int judgeId) {
		// TODO Auto-generated method stub
		return this.findJudgeById(judgeId);
	}	

}