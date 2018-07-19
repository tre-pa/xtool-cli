package br.xtool.core.representation;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Representa a multiplicidade UML.
 * 
 * @author jcruz
 *
 */
public interface EUmlMultiplicity {

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

}
