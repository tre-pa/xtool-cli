package br.xtool.core.pdiagram;

import br.xtool.core.representation.plantuml.PlantClassRepresentation;
import br.xtool.core.representation.springboot.EntityRepresentation;

/**
 * 
 * @author jcruz
 *
 */
public interface ClassVisitor {

	/**
	 * 
	 * @param plantClass
	 */
	void visit(EntityRepresentation entity, PlantClassRepresentation plantClass);

}
