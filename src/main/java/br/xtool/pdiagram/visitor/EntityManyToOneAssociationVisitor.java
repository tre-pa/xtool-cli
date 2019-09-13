package br.xtool.pdiagram.visitor;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import br.xtool.pdiagram.RelationshipVisitor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.stereotype.Component;

import br.xtool.representation.plantuml.PlantRelationshipRepresentation;
import br.xtool.representation.springboot.JpaEntityAttributeRepresentation;
import lombok.val;

/**
 * Visitor de relacionamentos de Associação ManyToOne
 * 
 * @author jcruz
 *
 */
@Component
public class EntityManyToOneAssociationVisitor implements RelationshipVisitor {

	@Override
	public void visit(JpaEntityAttributeRepresentation attr, PlantRelationshipRepresentation plantRelationship) {
		if (plantRelationship.isAssociation() && plantRelationship.isManyToOne()) {
			addManyToOneAnnotation(attr, plantRelationship);
			addFetchAnnotation(attr);
		}
	}

	private void addFetchAnnotation(JpaEntityAttributeRepresentation attr) {
		attr.addAnnotation(Fetch.class).getRoasterAnnotation().setEnumValue(FetchMode.JOIN);
	}

	private void addManyToOneAnnotation(JpaEntityAttributeRepresentation attr, PlantRelationshipRepresentation plantRelationship) {
		val ann = attr.addAnnotation(ManyToOne.class);
		ann.getRoasterAnnotation().setEnumValue("fetch", FetchType.LAZY);
		if (!plantRelationship.getSourceMultiplicity().isOptional()) ann.getRoasterAnnotation().setLiteralValue("optional", "false");
	}

}
