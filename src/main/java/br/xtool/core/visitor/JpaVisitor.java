package br.xtool.core.visitor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;

import br.xtool.core.Visitor;
import br.xtool.core.representation.plantuml.PlantClassFieldPropertyRepresentation;
import br.xtool.core.representation.plantuml.PlantClassFieldRepresentation;
import br.xtool.core.representation.plantuml.PlantClassRepresentation;
import br.xtool.core.representation.plantuml.PlantRelationshipRepresentation;
import br.xtool.core.representation.plantuml.PlantStereotypeRepresentation.StereotypeType;
import br.xtool.core.representation.springboot.EntityAttributeRepresentation;
import br.xtool.core.representation.springboot.EntityRepresentation;
import lombok.val;

@Component
public class JpaVisitor implements Visitor {

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaClass, br.xtool.core.representation.EUmlClass)
	 */
	@Override
	public void visit(EntityRepresentation entity, PlantClassRepresentation plantClass) {
		entity.addAnnotation(Entity.class);
		if (plantClass.getStereotypes().stream().noneMatch(st -> st.getStereotypeType().equals(StereotypeType.READ_ONLY) || st.getStereotypeType().equals(StereotypeType.VIEW))) {
			entity.addAnnotation(DynamicInsert.class);
			entity.addAnnotation(DynamicUpdate.class);
		}
		// @formatter:off
		plantClass.getTaggedValue("Table.name").ifPresent(tagValue ->
			entity.addAnnotation(Table.class)
				.getRoasterAnnotation()
				.setStringValue("name", tagValue));
		plantClass.getTaggedValue("Table.schema").ifPresent(tagValue -> 
			entity.addAnnotation(Table.class)
				.getRoasterAnnotation()
				.setStringValue("schema", tagValue));
		entity.addAnnotation(JsonInclude.class)
			.getRoasterAnnotation()
			.setEnumArrayValue(JsonInclude.Include.NON_EMPTY);
		// @formatter:on
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaField, br.xtool.core.representation.EUmlField)
	 */
	@Override
	public void visit(EntityAttributeRepresentation attr, PlantClassFieldRepresentation plantField) {
		val annColumn = attr.addAnnotation(Column.class);
		plantField.getTaggedValue("column.name").ifPresent(tagValue -> annColumn.getRoasterAnnotation().setStringValue("name", tagValue));
		if (attr.isStringField()) {
			// @formatter:off
				annColumn.getRoasterAnnotation()
					.setLiteralValue("length", String.valueOf(plantField.getMaxArrayLength().orElse(255)));
				// @formatter:on
		} else if (attr.isLongField()) {
			if (plantField.isId()) {
				attr.addAnnotation(Id.class);
				// @formatter:off
					annColumn.getRoasterAnnotation()
						.setLiteralValue("updatable", "false")
						.setLiteralValue("nullable", "false");
					attr.addAnnotation(GeneratedValue.class).getRoasterAnnotation()
						.setEnumValue("strategy", GenerationType.SEQUENCE)
						.setStringValue("generator", attr.getEntity().asDatabaseSequenceName());
					attr.addAnnotation(SequenceGenerator.class).getRoasterAnnotation()
						.setLiteralValue("initialValue", "1")
						.setLiteralValue("allocationSize", "1")
						.setStringValue("name", attr.getEntity().asDatabaseSequenceName())
						.setStringValue("sequenceName", attr.getEntity().asDatabaseSequenceName());
					// @formatter:on
			}
		} else if (attr.isEnumField()) {
			// @formatter:off
			attr.addAnnotation(Enumerated.class)
				.getRoasterAnnotation()
				.setEnumValue(EnumType.STRING)
				.setEnumValue(EnumType.STRING);
			annColumn
				.getRoasterAnnotation()
				.setLiteralValue("nullable", "false");
			// @formatter:on
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaField, br.xtool.core.representation.EUmlFieldProperty)
	 */
	@Override
	public void visit(EntityAttributeRepresentation attr, PlantClassFieldPropertyRepresentation plantProperty) {
		if (plantProperty.isNotNull()) {
			// @formatter:off
			attr.addAnnotation(Column.class)
				.getRoasterAnnotation()
				.setLiteralValue("nullable", "false");
			// @formatter:on
		} else if (plantProperty.isUnique()) {
			// @formatter:off
			attr.addAnnotation(Column.class)
				.getRoasterAnnotation()
				.setLiteralValue("unique", "true");
			// @formatter:on
		} else if (plantProperty.isTransient()) {
			attr.addAnnotation(Transient.class);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaField, br.xtool.core.representation.EUmlRelationship)
	 */
	@Override
	public void visit(EntityAttributeRepresentation attr, PlantRelationshipRepresentation plantRelationship) {
		/*
		 * Verifica se o relacionamento é uma associação.
		 */
		if (plantRelationship.isAssociation()) {
			if (plantRelationship.isOneToOne()) {
				val annOneToOne = attr.addAnnotation(OneToOne.class);
				annOneToOne.getRoasterAnnotation().setEnumValue("fetch", FetchType.LAZY);
				attr.addAnnotation(Fetch.class).getRoasterAnnotation().setEnumValue(FetchMode.JOIN);
				if (!plantRelationship.getSourceMultiplicity().isOptional()) annOneToOne.getRoasterAnnotation().setLiteralValue("optional", "false");
				// Bidirecional
				if (!plantRelationship.isSourceClassOwner() && plantRelationship.getNavigability().isBidirectional()) {
					annOneToOne.getRoasterAnnotation().setStringValue("mappedBy", plantRelationship.getTargetRole());
				} else {

				}
			} else if (plantRelationship.isOneToMany()) {
				attr.addAnnotation(BatchSize.class).getRoasterAnnotation().setLiteralValue("size", String.valueOf(10));
				attr.addAnnotation(LazyCollection.class).getRoasterAnnotation().setEnumValue(LazyCollectionOption.EXTRA);
				val annOneToMany = attr.addAnnotation(OneToMany.class);
				// Bidirecional
				if (plantRelationship.getNavigability().isBidirectional()) {
					annOneToMany.getRoasterAnnotation().setStringValue("mappedBy", plantRelationship.getTargetRole());
				} else {
					attr.addAnnotation(JoinColumn.class).getRoasterAnnotation().setStringValue("name", EntityRepresentation.genFKName(plantRelationship.getTargetClass().getName()));
				}
			} else if (plantRelationship.isManyToOne()) {
				val ann = attr.addAnnotation(ManyToOne.class);
				ann.getRoasterAnnotation().setEnumValue("fetch", FetchType.LAZY);
				attr.addAnnotation(Fetch.class).getRoasterAnnotation().setEnumValue(FetchMode.JOIN);
				if (!plantRelationship.getSourceMultiplicity().isOptional()) ann.getRoasterAnnotation().setLiteralValue("optional", "false");
			} else if (plantRelationship.isManyToMany()) {
				val annMany = attr.addAnnotation(ManyToMany.class);
				attr.addAnnotation(BatchSize.class).getRoasterAnnotation().setLiteralValue("size", String.valueOf(10));
				attr.addAnnotation(LazyCollection.class).getRoasterAnnotation().setEnumValue(LazyCollectionOption.EXTRA);
				// Bidirecional
				if (!plantRelationship.isSourceClassOwner() && plantRelationship.getNavigability().isBidirectional()) {
					annMany.getRoasterAnnotation().setStringValue("mappedBy", plantRelationship.getTargetRole());
				}
			}
		} else if (plantRelationship.isComposition()) {
			if (plantRelationship.isOneToOne()) {
				// @formatter:off
				attr.addAnnotation(OneToOne.class)
					.getRoasterAnnotation()
					.setEnumValue("fetch", FetchType.LAZY)
					.setEnumValue("cascade", CascadeType.ALL)
					.setLiteralValue("orphanRemoval", "true");
				// @formatter:on
				attr.addAnnotation(Fetch.class).getRoasterAnnotation().setEnumValue(FetchMode.JOIN);
			} else if (plantRelationship.isOneToMany()) {
				attr.addAnnotation(BatchSize.class).getRoasterAnnotation().setLiteralValue("size", String.valueOf(10));
				attr.addAnnotation(LazyCollection.class).getRoasterAnnotation().setEnumValue(LazyCollectionOption.EXTRA);
				// @formatter:off
				val annOneToMany = attr.addAnnotation(OneToMany.class)
					.getRoasterAnnotation()
					.setEnumValue("cascade", CascadeType.ALL)
					.setLiteralValue("orphanRemoval", "true");
				// @formatter:on
				// Bidirecional
				if (plantRelationship.getNavigability().isBidirectional()) {
					annOneToMany.setStringValue("mappedBy", plantRelationship.getTargetRole());
				} else {
					// @formatter:off
					attr.addAnnotation(JoinColumn.class)
						.getRoasterAnnotation()
						.setStringValue("name", EntityRepresentation.genFKName(plantRelationship.getTargetClass().getName()));
					// @formatter:on
				}
			} else if (plantRelationship.isManyToOne()) {
				attr.addAnnotation(Fetch.class).getRoasterAnnotation().setEnumValue(FetchMode.JOIN);
				val ann = attr.addAnnotation(ManyToOne.class);
				if (!plantRelationship.getSourceMultiplicity().isOptional()) ann.getRoasterAnnotation().setLiteralValue("optional", "false");
			}
		}
	}

}
