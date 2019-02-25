package br.xtool.core.implementation.visitor;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.stereotype.Component;

import br.xtool.core.representation.plantuml.PlantRelationshipRepresentation;
import br.xtool.core.representation.springboot.EntityAttributeRepresentation;
import br.xtool.core.visitor.RelationshipVisitor;

@Component
public class EntityOneToOneCompositionVisitor implements RelationshipVisitor {

	@Override
	public void visit(EntityAttributeRepresentation attr, PlantRelationshipRepresentation plantRelationship) {
		if (plantRelationship.isComposition() && plantRelationship.isOneToOne()) {
			addOneToOneAnnotation(attr, plantRelationship);
			addFetchAnnotation(attr);
		}
	}

	private void addFetchAnnotation(EntityAttributeRepresentation attr) {
		attr.addAnnotation(Fetch.class).getRoasterAnnotation().setEnumValue(FetchMode.JOIN);
	}

	private void addOneToOneAnnotation(EntityAttributeRepresentation attr, PlantRelationshipRepresentation plantRelationship) {
		// @formatter:off
		attr.addAnnotation(OneToOne.class)
			.getRoasterAnnotation()
			.setEnumValue("fetch", FetchType.LAZY)
			.setEnumValue("cascade", CascadeType.ALL)
			.setLiteralValue("orphanRemoval", "true");
		// @formatter:on
	}

}
