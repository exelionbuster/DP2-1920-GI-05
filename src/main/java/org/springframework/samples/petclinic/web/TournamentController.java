package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Category;
import org.springframework.samples.petclinic.model.Field;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Tournament;
import org.springframework.samples.petclinic.service.CategoryService;
import org.springframework.samples.petclinic.service.FieldService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.TournamentService;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.samples.petclinic.service.exceptions.WrongDateException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TournamentController {

	private final TournamentService tournamentService;
	private final CategoryService categoryService;
	private final PetService petService;
	private final FieldService fieldService;

	@Autowired
	public TournamentController(TournamentService tournamentService, PetService petService,
			CategoryService categoryService, FieldService fieldService) {
		this.categoryService = categoryService;
		this.tournamentService = tournamentService;
		this.petService = petService;
		this.fieldService = fieldService;
	}

	@ModelAttribute("categories")
	public Collection<Category> populateCategories() {
		return this.categoryService.findAllCategories();
	}
	
	@ModelAttribute("fields")
	public Collection<Field> populatefields() {
		return this.fieldService.findAllFields();
	}

	@ModelAttribute("types")
	public Collection<PetType> populatePetTypes() {
		return this.petService.findPetTypes();
	}
	
	@InitBinder("tournament")
	public void initPetBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new TournamentValidator());
	}

	@GetMapping(value = "/tournaments/new")
	public String initCreationForm(ModelMap model) {
		Tournament tournament = new Tournament();
		// Collection<Category> category = this.categoryService.findAllCategories();
		model.put("tournament", tournament);
		// model.put("category", category);
		
		// Collection<Field> field = this.fieldService.findAllFields();
		// model.put("field", field);
		return "tournament/form";
	}

	@PostMapping(value = "/tournaments/new")
	public String processCreationForm(@Valid Tournament tournament, BindingResult result, ModelMap model) {

		if (result.hasErrors()) {
			model.put("tournament", tournament);
			return "tournaments/form";
		} else {
			System.out.println(tournament);

			this.tournamentService.saveTournament(tournament);

			return "welcome";
		}
	}
	
	@GetMapping(value = { "/tournaments/all" })
	public String showAllTournaments(Map<String, Object> model) {
				
		Collection<Tournament> allTournaments = this.tournamentService.findAllTournament();
		model.put("tournaments", allTournaments);
		return "tournaments/list";
	}
	
	@GetMapping(value = { "/tournaments/active" })
	public String showActiveTournaments(Map<String, Object> model) {
				
		Collection<Tournament> activeTournaments = this.tournamentService.findActiveTournaments();
		model.put("tournaments", activeTournaments);
		return "tournaments/list";
	}

}
