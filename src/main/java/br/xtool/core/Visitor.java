package br.xtool.core;

import br.xtool.core.representation.plantuml.PlantClassFieldPropertyRepresentation;
import br.xtool.core.representation.plantuml.PlantClassFieldRepresentation;
import br.xtool.core.representation.plantuml.PlantClassRepresentation;
import br.xtool.core.representation.plantuml.PlantRelationshipRepresentation;
import br.xtool.core.representation.springboot.EntityAttributeRepresentation;
import br.xtool.core.representation.springboot.EntityRepresentation;

/**
 * 
 * @author jcruz
 *
 */
@Deprecated
public interface Visitor {

	/**
	 * 
	 * @param plantClass
	 */
	void visit(EntityRepresentation entity, PlantClassRepresentation plantClass);

	/**
	 * 
	 * @param plantField
	 */
	void visit(EntityAttributeRepresentation attribute, PlantClassFieldRepresentation plantField);

	/**
	 * 
	 * @param plantFieldProperty
	 */
	void visit(EntityAttributeRepresentation attribute, PlantClassFieldPropertyRepresentation plantFieldProperty);

	/**
	 * 
	 * @param umlRelationship
	 */
	void visit(EntityAttributeRepresentation attribute, PlantRelationshipRepresentation umlRelationship);

}
