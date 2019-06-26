package br.xtool.core.pdiagram.visitor;

import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.stereotype.Component;

import br.xtool.core.pdiagram.RelationshipVisitor;
import br.xtool.core.representation.plantuml.PlantRelationshipRepresentation;
import br.xtool.core.representation.springboot.EntityAttributeRepresentation;
import lombok.val;
import strman.Strman;

/**
 * 
 * Visitor de relacionamento de Associação OneToMany
 * 
 * @author jcruz
 *
 */
@Component
public class EntityOneToManyAssociationVisitor implements RelationshipVisitor {

	@Override
	public void visit(EntityAttributeRepresentation attr, PlantRelationshipRepresentation plantRelationship) {
		if (plantRelationship.isAssociation() && plantRelationship.isOneToMany()) {
			addOneToManyAnnotation(attr, plantRelationship);
			addBatchSizeAnnotation(attr);
			addLazyCollectionAnnotation(attr);
			addJoinColumnAnnotation(attr, plantRelationship);
		}
	}

	private void addJoinColumnAnnotation(EntityAttributeRepresentation attr, PlantRelationshipRepresentation plantRelationship) {
		if (!plantRelationship.getNavigability().isBidirectional()) {
			// @formatter:off
			String fkName = StringUtils.abbreviate(
				StringUtils.upperCase(
				Strman.toSnakeCase(plantRelationship.getTargetClass().getName())), "", 30) + "_ID";
			// @formatter:on
			attr.addAnnotation(JoinColumn.class).getRoasterAnnotation().setStringValue("name", fkName);
		}
	}

	private void addOneToManyAnnotation(EntityAttributeRepresentation attr, PlantRelationshipRepresentation plantRelationship) {
		val annOneToMany = attr.addAnnotation(OneToMany.class);
		if (plantRelationship.getNavigability().isBidirectional()) {
			annOneToMany.getRoasterAnnotation().setStringValue("mappedBy", plantRelationship.getTargetRole());
		}
	}

	private void addLazyCollectionAnnotation(EntityAttributeRepresentation attr) {
		attr.addAnnotation(LazyCollection.class).getRoasterAnnotation().setEnumValue(LazyCollectionOption.EXTRA);
	}

	private void addBatchSizeAnnotation(EntityAttributeRepresentation attr) {
		attr.addAnnotation(BatchSize.class).getRoasterAnnotation().setLiteralValue("size", String.valueOf(10));
	}

}
