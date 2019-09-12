package br.xtool.core.pdiagram;

import br.xtool.core.representation.plantuml.PlantClassFieldPropertyRepresentation;
import br.xtool.core.representation.springboot.JpaEntityAttributeRepresentation;

/**
 * 
 * @author jcruz
 *
 */
public interface PropertyVisitor {

	/**
	 * 
	 * @param attribute
	 * @param plantProperty
	 */
	void visit(JpaEntityAttributeRepresentation attribute, PlantClassFieldPropertyRepresentation plantProperty);

}
