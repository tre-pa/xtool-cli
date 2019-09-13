package br.xtool.pdiagram;

import br.xtool.representation.plantuml.PlantClassFieldRepresentation;
import br.xtool.representation.springboot.JpaEntityAttributeRepresentation;

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
	void visit(JpaEntityAttributeRepresentation attribute, PlantClassFieldRepresentation plantField);
	


}
