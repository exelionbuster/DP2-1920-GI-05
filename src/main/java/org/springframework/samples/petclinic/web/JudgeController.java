package org.springframework.samples.petclinic.web;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Guide;
import org.springframework.samples.petclinic.model.Judge;
import org.springframework.samples.petclinic.model.Judges;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.JudgeService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class JudgeController {

	private static final String VIEWS_JUDGE_CREATE_OR_UPDATE_FORM = "judges/createOrUpdateJudgeForm";

	private final JudgeService judgeService;

	@Autowired
	public JudgeController(JudgeService judgeService, UserService userService, AuthoritiesService authoritiesService) {
		this.judgeService = judgeService;		
	}
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(value = { "/judges" })
	public String showJugdeList(Map<String, Object> model) {
		Judges jugdes = new Judges();
		jugdes.getJugdeList().addAll(this.judgeService.findAllJudges());
		model.put("jugdes", jugdes);
		return "judges/list";
	}

	@GetMapping(value = "/judges/new")
	public String initCreationForm(Map<String, Object> model) {
		Judge judge = new Judge();
		model.put("judge", judge);
		return "judges/createOrUpdateJudgeForm";
	}

	@PostMapping(value = "/judges/new")
	public String processCreationForm(@Valid Judge judge, BindingResult result) {
		if (result.hasErrors()) {
			return "judges/createOrUpdateJudgeForm";
		} else {
			// creating owner, user and authorities
			this.judgeService.saveJudge(judge);

			return "redirect:/judges/" + judge.getId();
		}
	}

	@GetMapping(value = "/judges/{judgeId}/edit")
	public String initUpdateJudgeForm(@PathVariable("judgeId") int judgeId, Model model) {
		Judge judge =  this.judgeService.findJudgeById(judgeId);
		model.addAttribute(judge);
		return VIEWS_JUDGE_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/judges/{judgeId}/edit")
	public String processUpdateGuideForm(@Valid Judge judge, BindingResult result,
			@PathVariable("judgeId") int judgeId) {
		if (result.hasErrors()) {
			return VIEWS_JUDGE_CREATE_OR_UPDATE_FORM;
		} else {
			judge.setId(judgeId);
			this.judgeService.saveJudge(judge);
			return "welcome";
		}
	}

	@GetMapping("/judges/{judgeId}")
	public ModelAndView showJudge(@PathVariable("judgeId") int judgeId) {
		ModelAndView mav = new ModelAndView("judges/judgeDetails");
		mav.addObject(this.judgeService.findJudgeById(judgeId));
		return mav;
	}

}
