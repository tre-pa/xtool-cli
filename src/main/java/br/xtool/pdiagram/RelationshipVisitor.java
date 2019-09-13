package br.xtool.pdiagram;

import br.xtool.representation.plantuml.PlantRelationshipRepresentation;
import br.xtool.representation.springboot.JpaEntityAttributeRepresentation;

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
