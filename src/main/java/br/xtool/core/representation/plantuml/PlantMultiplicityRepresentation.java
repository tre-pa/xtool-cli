package br.xtool.core.representation.plantuml;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Representa a multiplicidade UML.
 * 
 * @author jcruz
 *
 */
public interface PlantMultiplicityRepresentation {

	@AllArgsConstructor
	@Getter
	enum MultiplicityType {
		// @formatter:off
		ONE("1"),
		ZERO_TO_ONE("0..1"),
		ZERO_TO_MANY("0..*"),
		ONE_TO_MANY("1..*"),
		MANY("*");
		// @formatter:on
		String pattern;
	}

	/**
	 * Retorna o tipo de multiplicidade do relacionamento.
	 * 
	 * @return
	 */
	MultiplicityType getMutiplicityType();

	/**
	 * Retorna se a multiplicidade é: ZERO_TO_MANY, ONE_TO_MANY ou MANY
	 * 
	 * @return
	 */
	boolean isToMany();

	/**
	 * Retorna se a multiplicidade é: ZERO_TO_ONE ou ONE.
	 * 
	 * @return
	 */
	boolean isToOne();

	/**
	 * Retorna se a multiplicidade é opcional (ZERO_TO_ONE, ZERO_TO_MANY ou MANY)
	 * 
	 * @return
	 */
	boolean isOptional();

}
