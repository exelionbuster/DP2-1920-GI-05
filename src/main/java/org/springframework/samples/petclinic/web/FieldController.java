/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.web;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Field;
import org.springframework.samples.petclinic.service.FieldService;
import org.springframework.samples.petclinic.service.exceptions.DuplicateFieldNameException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
//@RequestMapping("/fields/{fieldId}")
public class FieldController {

	private static final String VIEWS_FIELDS_CREATE_OR_UPDATE_FORM = "fields/createOrUpdateFieldForm";

	private final FieldService fieldService;

	@Autowired
	public FieldController(FieldService fieldService) {
		this.fieldService = fieldService;
	}
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	@InitBinder("field")
	public void initFieldBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new FieldValidator());
	}

	@GetMapping(value = "/fields/all")
	public String showFields(Map<String, Object> model) {
		model.put("fields", this.fieldService.findAllFields());
		return "fields/list";
	}

	@GetMapping(value = "/fields/new")
	public String initCreationForm(ModelMap model) {
		Field field = new Field();
		model.put("field", field);
		return VIEWS_FIELDS_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/fields/new")
	public String processCreationForm(@Valid Field field, BindingResult result, ModelMap model)
			throws DataAccessException,  DuplicateFieldNameException {
		if (result.hasErrors()) {
			model.put("field", field);
			return VIEWS_FIELDS_CREATE_OR_UPDATE_FORM;
		} else {
			try {

				this.fieldService.saveField(field);
			} catch (DuplicateFieldNameException ex) {
				result.rejectValue("name", "duplicate", "already exists");
				return VIEWS_FIELDS_CREATE_OR_UPDATE_FORM;
			}

			return "redirect:/fields/all";
		}
	}

}
