package br.xtool.core.representation.plantuml;

import lombok.AllArgsConstructor;
import lombok.Getter;

public interface PlantClassFieldPropertyRepresentation {

	FieldPropertyType getFieldProperty();

	/**
	 * Retorna o field associado a propriedade.
	 * 
	 * @return
	 */
	PlantClassFieldRepresentation getField();

	/**
	 * Verifica se o atribudo é Id.
	 * 
	 * @return
	 */
	boolean isId();

	/**
	 * Verifica se o atributo é não nulo.
	 * 
	 * @return
	 */
	boolean isNotNull();

	/**
	 * Verifica se o atributo é único.
	 * 
	 * @return
	 */
	boolean isUnique();

	@AllArgsConstructor
	@Getter
	// @formatter:off
	enum FieldPropertyType {
		ID("id"),
		NOTNULL("notnull"), 
		UNIQUE("unique");
		private String property;
		// @formatter:on

	}
}
