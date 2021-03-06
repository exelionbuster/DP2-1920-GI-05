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
package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Tournament;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.Category;
import org.springframework.samples.petclinic.service.exceptions.DuplicateCategoryNameException;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class CategoryServiceTests { 
	
        @Autowired
	protected CategoryService categoryService;
                

    // List all Categories Postive case
	@Test
	void shouldFindAllCategories() {
		Collection<Category> categories = this.categoryService.findAllCategories();
		assertThat(categories.size()).isEqualTo(3);
	}

    // Create new Category Postive Case
	@Test
	void shouldInsertdNewCategories() throws DataAccessException, DuplicateCategoryNameException {
		Category c = new Category();
		c.setName("Frisbee");
		this.categoryService.saveCategory(c);
		Collection<Category> categories = this.categoryService.findAllCategories();
		assertThat(categories.size()).isEqualTo(4);
	}
	
	// Create new Category Negative Case: Duplicated name
	@Test
	@Transactional
	public void shouldThrowExceptionInsertingCategoriesWithTheSameName() {		
		Category category = new Category();
		category.setName("Speed");
		try {
			categoryService.saveCategory(category);		
		} catch (DuplicateCategoryNameException e) {
		
			e.printStackTrace();
		}
		
		Category anotherCategoryWithTheSameName = new Category();	
		anotherCategoryWithTheSameName.setName("Obstacles");
		Assertions.assertThrows(DuplicateCategoryNameException.class, () ->{categoryService.saveCategory(anotherCategoryWithTheSameName);});		
	}
	
	
	
	

	


}
