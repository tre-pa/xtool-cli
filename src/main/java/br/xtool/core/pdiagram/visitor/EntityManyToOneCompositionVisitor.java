package br.xtool.core.pdiagram.visitor;

import javax.persistence.ManyToOne;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.stereotype.Component;

import br.xtool.core.pdiagram.RelationshipVisitor;
import br.xtool.core.representation.plantuml.PlantRelationshipRepresentation;
import br.xtool.core.representation.springboot.JpaEntityAttributeRepresentation;
import lombok.val;

/**
 * Visitor de relacionamentos de Composição ManyToOne
 * 
 * @author jcruz
 *
 */
@Component
public class EntityManyToOneCompositionVisitor implements RelationshipVisitor {

	@Override
	public void visit(JpaEntityAttributeRepresentation attr, PlantRelationshipRepresentation plantRelationship) {
		if (plantRelationship.isComposition() && plantRelationship.isManyToOne()) {
			addManyToOneAnnotation(attr, plantRelationship);
			addFetchAnnotation(attr);
		}
	}

	private void addManyToOneAnnotation(JpaEntityAttributeRepresentation attr, PlantRelationshipRepresentation plantRelationship) {
		val ann = attr.addAnnotation(ManyToOne.class);
		if (!plantRelationship.getSourceMultiplicity().isOptional()) ann.getRoasterAnnotation().setLiteralValue("optional", "false");
	}

	private void addFetchAnnotation(JpaEntityAttributeRepresentation attr) {
		attr.addAnnotation(Fetch.class).getRoasterAnnotation().setEnumValue(FetchMode.JOIN);
	}

}
