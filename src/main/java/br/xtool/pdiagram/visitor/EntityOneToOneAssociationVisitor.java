package br.xtool.pdiagram.visitor;

import javax.persistence.FetchType;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.stereotype.Component;

import br.xtool.pdiagram.RelationshipVisitor;
import br.xtool.representation.plantuml.PlantRelationshipRepresentation;
import br.xtool.representation.springboot.JpaEntityAttributeRepresentation;
import lombok.val;

/**
 * Visitor de relacionamento de Associação OneToOne
 * 
 * @author jcruz
 *
 */
@Component
public class EntityOneToOneAssociationVisitor implements RelationshipVisitor {

	@Override
	public void visit(JpaEntityAttributeRepresentation attr, PlantRelationshipRepresentation plantRelationship) {
		if (plantRelationship.isAssociation() && plantRelationship.isOneToOne()) {
			addOneToOneAnnotation(attr, plantRelationship);
			addFetchAnnotation(attr);
		}
	}

	private void addFetchAnnotation(JpaEntityAttributeRepresentation attr) {
		attr.addAnnotation(Fetch.class).getRoasterAnnotation().setEnumValue(FetchMode.JOIN);
	}

	private void addOneToOneAnnotation(JpaEntityAttributeRepresentation attr, PlantRelationshipRepresentation plantRelationship) {
		val annOneToOne = attr.addAnnotation(OneToOne.class);
		annOneToOne.getRoasterAnnotation().setEnumValue("fetch", FetchType.LAZY);
		if (!plantRelationship.getSourceMultiplicity().isOptional()) {
			annOneToOne.getRoasterAnnotation().setLiteralValue("optional", "false");
		}
		if (!plantRelationship.isSourceClassOwner() && plantRelationship.getNavigability().isBidirectional()) {
			annOneToOne.getRoasterAnnotation().setStringValue("mappedBy", plantRelationship.getTargetRole());
		}
	}

}