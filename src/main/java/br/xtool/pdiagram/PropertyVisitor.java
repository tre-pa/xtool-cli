package br.xtool.pdiagram;

import br.xtool.representation.plantuml.PlantClassFieldPropertyRepresentation;
import br.xtool.representation.springboot.JpaEntityAttributeRepresentation;

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
