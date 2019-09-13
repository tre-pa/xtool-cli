package br.xtool.pdiagram;

import br.xtool.representation.plantuml.PlantClassRepresentation;
import br.xtool.representation.springboot.JpaEntityRepresentation;

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
