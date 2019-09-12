package br.xtool.core.pdiagram;

import br.xtool.core.representation.plantuml.PlantClassRepresentation;
import br.xtool.core.representation.springboot.JpaEntityRepresentation;

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
	void visit(JpaEntityRepresentation entity, PlantClassRepresentation plantClass);

}
