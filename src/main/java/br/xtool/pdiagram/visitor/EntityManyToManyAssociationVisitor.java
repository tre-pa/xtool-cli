package br.xtool.pdiagram.visitor;

import javax.persistence.ManyToMany;

import br.xtool.pdiagram.RelationshipVisitor;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.stereotype.Component;

import br.xtool.core.Clog;
import br.xtool.representation.plantuml.PlantRelationshipRepresentation;
import br.xtool.representation.springboot.JpaEntityAttributeRepresentation;
import lombok.val;

/**
 * Visitor dos relacionamentos de Associação ManyToMany
 * 
 * @author jcruz
 *
 */
@Component
public class EntityManyToManyAssociationVisitor implements RelationshipVisitor {

	@Override
	public void visit(JpaEntityAttributeRepresentation attr, PlantRelationshipRepresentation plantRelationship) {
		if (plantRelationship.isAssociation() && plantRelationship.isManyToMany()) {
			Clog.printv(" [ASSOCIATION] ");
			addManyToManyAnnotation(attr, plantRelationship);
			addBatchSizeAnnotation(attr);
			addLazyCollectionAnnotation(attr);
		}
	}

	private void addLazyCollectionAnnotation(JpaEntityAttributeRepresentation attr) {
		attr.addAnnotation(LazyCollection.class).getRoasterAnnotation().setEnumValue(LazyCollectionOption.EXTRA);
	}

	private void addBatchSizeAnnotation(JpaEntityAttributeRepresentation attr) {
		attr.addAnnotation(BatchSize.class).getRoasterAnnotation().setLiteralValue("size", String.valueOf(10));
	}

	private void addManyToManyAnnotation(JpaEntityAttributeRepresentation attr, PlantRelationshipRepresentation plantRelationship) {
		val annMany = attr.addAnnotation(ManyToMany.class);
		if (!plantRelationship.isSourceClassOwner() && plantRelationship.getNavigability().isBidirectional()) {
			annMany.getRoasterAnnotation().setStringValue("mappedBy", plantRelationship.getTargetRole());
		}
	}

}
