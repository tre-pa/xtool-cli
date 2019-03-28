package br.xtool.core.representation.plantuml;

import lombok.AllArgsConstructor;
import lombok.Getter;

public interface PlantClassFieldPropertyRepresentation {

	@AllArgsConstructor
	@Getter
	enum FieldPropertyType {
		// @formatter:off
		ID("id"),
		NOTNULL("notnull"), 
		UNIQUE("unique");
		// @formatter:on
		private String property;

	}

	FieldPropertyType getFieldProperty();

	/**
	 * Retorna o field associado a propriedade.
	 * 
	 * @return
	 */
	PlantClassFieldRepresentation getField();

	boolean isId();

	boolean isNotNull();

	boolean isUnique();

}
