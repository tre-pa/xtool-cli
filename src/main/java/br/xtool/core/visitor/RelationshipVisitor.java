package br.xtool.core.visitor;

import br.xtool.core.representation.plantuml.PlantRelationshipRepresentation;
import br.xtool.core.representation.springboot.EntityAttributeRepresentation;

/**
 * 
 * @author jcruz
 *
 */
public interface RelationshipVisitor {

	/**
	 * 
	 * @param plantRelationship
	 */
	void visit(EntityAttributeRepresentation attribute, PlantRelationshipRepresentation plantRelationship);

}
