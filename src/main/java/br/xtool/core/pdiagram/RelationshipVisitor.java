package br.xtool.core.pdiagram;

import br.xtool.core.representation.plantuml.PlantRelationshipRepresentation;
import br.xtool.core.representation.springboot.JpaEntityAttributeRepresentation;

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
	void visit(JpaEntityAttributeRepresentation attribute, PlantRelationshipRepresentation plantRelationship);

}
