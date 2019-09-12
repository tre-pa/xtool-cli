package br.xtool.core.pdiagram.visitor;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.stereotype.Component;

import br.xtool.core.pdiagram.RelationshipVisitor;
import br.xtool.core.representation.plantuml.PlantRelationshipRepresentation;
import br.xtool.core.representation.springboot.JpaEntityAttributeRepresentation;
import lombok.val;
import strman.Strman;

/**
 * Visitor de relacionamento de Composição OneToMany
 * 
 * @author jcruz
 *
 */
@Component
public class EntityOneToManyCompositionVisitor implements RelationshipVisitor {

	@Override
	public void visit(JpaEntityAttributeRepresentation attr, PlantRelationshipRepresentation plantRelationship) {
		if (plantRelationship.isComposition() && plantRelationship.isOneToMany()) {
			addBatchSizeAnnotation(attr);
			addLazyCollectionAnnotation(attr);
			addOneToManyAnnotation(attr, plantRelationship);
		}
	}

	private void addOneToManyAnnotation(JpaEntityAttributeRepresentation attr, PlantRelationshipRepresentation plantRelationship) {
		// @formatter:off
		val annOneToMany = attr.addAnnotation(OneToMany.class)
			.getRoasterAnnotation()
			.setEnumValue("cascade", CascadeType.ALL)
			.setLiteralValue("orphanRemoval", "true");
		// @formatter:on
		if (plantRelationship.getNavigability().isBidirectional()) {
			annOneToMany.setStringValue("mappedBy", plantRelationship.getTargetRole());
		} else {
			// @formatter:off
			String fkName = StringUtils.abbreviate(
					StringUtils.upperCase(
					Strman.toSnakeCase(plantRelationship.getTargetClass().getName())), "", 30) + "_ID";
			attr.addAnnotation(JoinColumn.class)
				.getRoasterAnnotation()
				.setStringValue("name", fkName);
			// @formatter:on
		}
	}

	private void addLazyCollectionAnnotation(JpaEntityAttributeRepresentation attr) {
		attr.addAnnotation(LazyCollection.class).getRoasterAnnotation().setEnumValue(LazyCollectionOption.EXTRA);
	}

	private void addBatchSizeAnnotation(JpaEntityAttributeRepresentation attr) {
		attr.addAnnotation(BatchSize.class).getRoasterAnnotation().setLiteralValue("size", String.valueOf(10));
	}

}
