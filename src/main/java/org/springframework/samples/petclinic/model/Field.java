package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.URL;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "fields")
public class Field extends NamedEntity {

	// Attributes -------------------------------------------------------------

	@NotBlank 
	@URL
	private String photoURL;

	@Digits(fraction = 0, integer = 5)
	@Min(0)
	private String lenght;

	@Digits(fraction = 0, integer = 5)
	@Min(0)
	private String breadth;

}
