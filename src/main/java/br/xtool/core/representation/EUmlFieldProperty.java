package br.xtool.core.representation;

import lombok.AllArgsConstructor;
import lombok.Getter;

public interface EUmlFieldProperty {

	@AllArgsConstructor
	@Getter
	enum FieldPropertyType {
		// @formatter:off
		NOTNULL("notnull"), 
		UNIQUE("unique"),
		TRANSIENT("transient");
		// @formatter:on
		private String property;

	}

	FieldPropertyType getFieldProperty();

	/**
	 * Retorna o field associado a propriedade.
	 * 
	 * @return
	 */
	EUmlField getField();

	boolean isNotNull();

	boolean isUnique();

	boolean isTransient();

}
