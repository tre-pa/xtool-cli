package br.xtool.core;

import br.xtool.core.representation.plantuml.PlantClassFieldPropertyRepresentation;
import br.xtool.core.representation.plantuml.PlantClassFieldRepresentation;
import br.xtool.core.representation.plantuml.PlantClassRepresentation;
import br.xtool.core.representation.plantuml.PlantRelationshipRepresentation;
import br.xtool.core.representation.plantuml.PlantRelationshipRepresentation.PlantRelationshipAssociation;
import br.xtool.core.representation.plantuml.PlantRelationshipRepresentation.PlantRelationshipComposition;
import br.xtool.core.representation.springboot.EntityAttributeRepresentation;
import br.xtool.core.representation.springboot.EntityRepresentation;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.JavaFieldManyToManyType;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.JavaFieldManyToOneType;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.JavaFieldOneToManyType;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.JavaFieldOneToOneType;

/**
 * 
 * @author jcruz
 *
 */
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

	/**
	 * 
	 * @param manyToOneField
	 * @param association
	 */
	void visit(JavaFieldManyToOneType manyToOneField, PlantRelationshipAssociation association);

	/**
	 * 
	 * @param manyToManyField
	 * @param association
	 */
	void visit(JavaFieldManyToManyType manyToManyField, PlantRelationshipAssociation association);

	/**
	 * 
	 * @param oneToOneField
	 * @param composition
	 */
	void visit(JavaFieldOneToOneType oneToOneField, PlantRelationshipComposition composition);

	/**
	 * 
	 * @param oneToManyField
	 * @param composition
	 */
	void visit(JavaFieldOneToManyType oneToManyField, PlantRelationshipComposition composition);

	/**
	 * 
	 * @param manyToOneField
	 * @param composition
	 */
	void visit(JavaFieldManyToOneType manyToOneField, PlantRelationshipComposition composition);

}
