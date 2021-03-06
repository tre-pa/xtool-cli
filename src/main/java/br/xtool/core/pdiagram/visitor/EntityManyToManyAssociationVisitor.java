package br.xtool.core.pdiagram.visitor;

import javax.persistence.ManyToMany;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.stereotype.Component;

import br.xtool.core.Clog;
import br.xtool.core.pdiagram.RelationshipVisitor;
import br.xtool.core.representation.plantuml.PlantRelationshipRepresentation;
import br.xtool.core.representation.springboot.EntityAttributeRepresentation;
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
	public void visit(EntityAttributeRepresentation attr, PlantRelationshipRepresentation plantRelationship) {
		if (plantRelationship.isAssociation() && plantRelationship.isManyToMany()) {
			Clog.printv(" [ASSOCIATION] ");
			addManyToManyAnnotation(attr, plantRelationship);
			addBatchSizeAnnotation(attr);
			addLazyCollectionAnnotation(attr);
		}
	}

	private void addLazyCollectionAnnotation(EntityAttributeRepresentation attr) {
		attr.addAnnotation(LazyCollection.class).getRoasterAnnotation().setEnumValue(LazyCollectionOption.EXTRA);
	}

	private void addBatchSizeAnnotation(EntityAttributeRepresentation attr) {
		attr.addAnnotation(BatchSize.class).getRoasterAnnotation().setLiteralValue("size", String.valueOf(10));
	}

	private void addManyToManyAnnotation(EntityAttributeRepresentation attr, PlantRelationshipRepresentation plantRelationship) {
		val annMany = attr.addAnnotation(ManyToMany.class);
		if (!plantRelationship.isSourceClassOwner() && plantRelationship.getNavigability().isBidirectional()) {
			annMany.getRoasterAnnotation().setStringValue("mappedBy", plantRelationship.getTargetRole());
		}
	}

}
