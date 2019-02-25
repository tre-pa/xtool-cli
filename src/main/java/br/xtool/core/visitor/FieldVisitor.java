package br.xtool.core.visitor;

import br.xtool.core.representation.plantuml.PlantClassFieldRepresentation;
import br.xtool.core.representation.springboot.EntityAttributeRepresentation;

/**
 * 
 * @author jcruz
 *
 */
public interface FieldVisitor {

	/**
	 * 
	 * @param plantField
	 */
	void visit(EntityAttributeRepresentation attribute, PlantClassFieldRepresentation plantField);

}
