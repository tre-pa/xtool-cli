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

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;

import br.xtool.core.Visitor;
import br.xtool.core.representation.plantuml.PlantClassFieldPropertyRepresentation;
import br.xtool.core.representation.plantuml.PlantClassFieldRepresentation;
import br.xtool.core.representation.plantuml.PlantClassRepresentation;
import br.xtool.core.representation.plantuml.PlantRelationshipRepresentation;
import br.xtool.core.representation.plantuml.PlantRelationshipRepresentation.PlantRelationshipAssociation;
import br.xtool.core.representation.plantuml.PlantRelationshipRepresentation.PlantRelationshipComposition;
import br.xtool.core.representation.plantuml.PlantStereotypeRepresentation.StereotypeType;
import br.xtool.core.representation.springboot.EntityRepresentation;
import br.xtool.core.representation.springboot.JavaFieldRepresentation;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.JavaFieldManyToManyType;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.JavaFieldManyToOneType;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.JavaFieldNotNullType;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.JavaFieldOneToManyType;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.JavaFieldOneToOneType;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.JavaFieldTransientType;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.JavaFieldUniqueType;
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
		plantClass.getTaggedValue("Table.name").ifPresent(tagValue -> entity.addAnnotation(Table.class).setStringValue("name", tagValue));
		plantClass.getTaggedValue("Table.schema").ifPresent(tagValue -> entity.addAnnotation(Table.class).setStringValue("schema", tagValue));
		entity.addAnnotation(JsonInclude.class).setEnumArrayValue(JsonInclude.Include.NON_EMPTY);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaField, br.xtool.core.representation.EUmlField)
	 */
	@Override
	public void visit(JavaFieldRepresentation javaField, PlantClassFieldRepresentation plantField) {
		val annColumn = javaField.addAnnotation(Column.class);
		plantField.getTaggedValue("column.name").ifPresent(tagValue -> annColumn.getRoasterAnnotation().setStringValue("name", tagValue));
		if (javaField.isStringField()) {
			// @formatter:off
				annColumn.getRoasterAnnotation()
					.setLiteralValue("length", String.valueOf(plantField.getMaxArrayLength().orElse(255)));
				// @formatter:on
		} else if (javaField.isLongField()) {
			if (plantField.isId()) {
				javaField.addAnnotation(Id.class);
				// @formatter:off
					annColumn.getRoasterAnnotation()
						.setLiteralValue("updatable", "false")
						.setLiteralValue("nullable", "false");
					javaField.addAnnotation(GeneratedValue.class).getRoasterAnnotation()
						.setEnumValue("strategy", GenerationType.SEQUENCE)
						.setStringValue("generator", EntityRepresentation.genDBSequenceName(javaField.getJavaClass().getName()));
					javaField.addAnnotation(SequenceGenerator.class).getRoasterAnnotation()
						.setLiteralValue("initialValue", "1")
						.setLiteralValue("allocationSize", "1")
						.setStringValue("name", EntityRepresentation.genDBSequenceName(javaField.getJavaClass().getName()))
						.setStringValue("sequenceName", EntityRepresentation.genDBSequenceName(javaField.getJavaClass().getName()));
					// @formatter:on
			}
		} else if (javaField.isEnumField()) {
			// @formatter:off
			javaField.addAnnotation(Enumerated.class).getRoasterAnnotation()
				.setEnumValue(EnumType.STRING)
				.setEnumValue(EnumType.STRING);
			annColumn.getRoasterAnnotation().setLiteralValue("nullable", "false");
			// @formatter:on
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaField, br.xtool.core.representation.EUmlFieldProperty)
	 */
	@Override
	public void visit(JavaFieldRepresentation javaField, PlantClassFieldPropertyRepresentation umlFieldProperty) {}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaField. ENotNullField, br.xtool.core.representation.EUmlFieldProperty)
	 */
	@Override
	public void visit(JavaFieldNotNullType notNullField, PlantClassFieldPropertyRepresentation property) {
		notNullField.addAnnotation(Column.class).setLiteralValue("nullable", "false");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaField. ETransientField, br.xtool.core.representation.EUmlFieldProperty)
	 */
	@Override
	public void visit(JavaFieldTransientType transientField, PlantClassFieldPropertyRepresentation property) {
		transientField.addAnnotation(Transient.class);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaField. EUniqueField, br.xtool.core.representation.EUmlFieldProperty)
	 */
	@Override
	public void visit(JavaFieldUniqueType uniqueField, PlantClassFieldPropertyRepresentation property) {
		uniqueField.addAnnotation(Column.class).setLiteralValue("unique", "true");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaField, br.xtool.core.representation.EUmlRelationship)
	 */
	@Override
	public void visit(JavaFieldRepresentation javaField, PlantRelationshipRepresentation umlRelationship) {
		if (javaField.getEnum().isPresent()) {
			javaField.addAnnotation(Enumerated.class).setEnumValue(EnumType.STRING);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaField. EOneToOneField, br.xtool.core.representation.EUmlRelationship.EAssociation)
	 */
	@Override
	public void visit(JavaFieldOneToOneType oneToOneField, PlantRelationshipAssociation association) {
		val annOneToOne = oneToOneField.addAnnotation(OneToOne.class);
		annOneToOne.setEnumValue("fetch", FetchType.LAZY);
		oneToOneField.addAnnotation(Fetch.class).setEnumValue(FetchMode.JOIN);
		if (!association.getSourceMultiplicity().isOptional()) annOneToOne.setLiteralValue("optional", "false");
		// Bidirecional
		if (!association.isSourceClassOwner() && association.getNavigability().isBidirectional()) {
			annOneToOne.setStringValue("mappedBy", association.getTargetRole());
		} else {

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaField. EOneToManyField, br.xtool.core.representation.EUmlRelationship.EAssociation)
	 */
	@Override
	public void visit(JavaFieldOneToManyType oneToManyField, PlantRelationshipAssociation association) {
		oneToManyField.addBatchSizeAnnotation(10);
		oneToManyField.addLazyCollectionAnnotation(LazyCollectionOption.EXTRA);
		val annOneToMany = oneToManyField.addAnnotation(OneToMany.class);
		// Bidirecional
		if (association.getNavigability().isBidirectional()) {
			annOneToMany.setStringValue("mappedBy", association.getTargetRole());
//			EntityTemplates.genAddListMethodRelationship(oneToManyField.getJavaClass(), association);
			// @formatter:off
			// add
//			oneToManyField.getJavaClass().addMethod(String.format("add%s", association.getTargetClass().getName()))
//				.getRoasterMethod()
//					.setReturnTypeVoid()
//					.setBody(String.format("this.%s.add(%s); %s.set%s(this);", 
//							association.getSourceRole(), 
//							association.getTargetClass().getInstanceName(), 
//							association.getTargetClass().getInstanceName(),
//							association.getSourceClass().getName()))
//					.addParameter(association.getTargetClass().getName(), association.getTargetClass().getInstanceName());
			// remove
//			EntityTemplates.genRemoveListMethodRelationship(oneToManyField.getJavaClass(), association);
//			oneToManyField.getJavaClass().addMethod(String.format("remove%s", association.getTargetClass().getName()))
//			.getRoasterMethod()
//				.setReturnTypeVoid()
//				.setBody(String.format("this.%s.remove(%s); %s.set%s(null);", 
//						association.getSourceRole(), 
//						association.getTargetClass().getInstanceName(), 
//						association.getTargetClass().getInstanceName(),
//						association.getSourceClass().getName()))
//				.addParameter(association.getTargetClass().getName(), association.getTargetClass().getInstanceName());
		// @formatter:on
		} else {
			val annJoinColumn = oneToManyField.addAnnotation(JoinColumn.class);
			annJoinColumn.setStringValue("name", EntityRepresentation.genFKName(association.getTargetClass().getName()));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaField. EManyToOneField, br.xtool.core.representation.EUmlRelationship.EAssociation)
	 */
	@Override
	public void visit(JavaFieldManyToOneType manyToOneField, PlantRelationshipAssociation association) {
		val ann = manyToOneField.addAnnotation(ManyToOne.class);
		ann.setEnumValue("fetch", FetchType.LAZY);
		manyToOneField.addAnnotation(Fetch.class).setEnumValue(FetchMode.JOIN);
		if (!association.getSourceMultiplicity().isOptional()) ann.setLiteralValue("optional", "false");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaField. EManyToManyField, br.xtool.core.representation.EUmlRelationship.EAssociation)
	 */
	@Override
	public void visit(JavaFieldManyToManyType manyToManyField, PlantRelationshipAssociation association) {
		val annMany = manyToManyField.addAnnotation(ManyToMany.class);
		manyToManyField.addBatchSizeAnnotation(10);
		manyToManyField.addLazyCollectionAnnotation(LazyCollectionOption.EXTRA);
		// Bidirecional
		if (!association.isSourceClassOwner() && association.getNavigability().isBidirectional()) {
			annMany.setStringValue("mappedBy", association.getTargetRole());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaField. EOneToOneField, br.xtool.core.representation.EUmlRelationship.EComposition)
	 */
	@Override
	public void visit(JavaFieldOneToOneType oneToOneField, PlantRelationshipComposition composition) {
		val annOneToOne = oneToOneField.addAnnotation(OneToOne.class);
		annOneToOne.setEnumValue("fetch", FetchType.LAZY);
		annOneToOne.setEnumValue("cascade", CascadeType.ALL);
		annOneToOne.setLiteralValue("orphanRemoval", "true");
		oneToOneField.addAnnotation(Fetch.class).setEnumValue(FetchMode.JOIN);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaField. EOneToManyField, br.xtool.core.representation.EUmlRelationship.EComposition)
	 */
	@Override
	public void visit(JavaFieldOneToManyType oneToManyField, PlantRelationshipComposition composition) {
		val annOneToMany = oneToManyField.addAnnotation(OneToMany.class);
		oneToManyField.addBatchSizeAnnotation(10);
		oneToManyField.addLazyCollectionAnnotation(LazyCollectionOption.EXTRA);
		annOneToMany.setEnumValue("cascade", CascadeType.ALL);
		annOneToMany.setLiteralValue("orphanRemoval", "true");
		// Bidirecional
		if (composition.getNavigability().isBidirectional()) {
			annOneToMany.setStringValue("mappedBy", composition.getTargetRole());
			// @formatter:off
			// add
//			EntityTemplates.genAddListMethodRelationship(oneToManyField.getJavaClass(), composition);
//			oneToManyField.getJavaClass().addMethod(String.format("add%s", composition.getTargetClass().getName()))
//				.getRoasterMethod()
//					.setReturnTypeVoid()
//					.setBody(String.format("this.%s.add(%s); %s.set%s(this);", 
//							composition.getSourceRole(), 
//							composition.getTargetClass().getInstanceName(), 
//							composition.getTargetClass().getInstanceName(),
//							composition.getSourceClass().getName()))
//					.addParameter(composition.getTargetClass().getName(), composition.getTargetClass().getInstanceName());
			// remove
//			EntityTemplates.genRemoveListMethodRelationship(oneToManyField.getJavaClass(), composition);
//			oneToManyField.getJavaClass().addMethod(String.format("remove%s", composition.getTargetClass().getName()))
//			.getRoasterMethod()
//				.setReturnTypeVoid()
//				.setBody(String.format("this.%s.remove(%s); %s.set%s(null);", 
//						composition.getSourceRole(), 
//						composition.getTargetClass().getInstanceName(), 
//						composition.getTargetClass().getInstanceName(),
//						composition.getSourceClass().getName()))
//				.addParameter(composition.getTargetClass().getName(), composition.getTargetClass().getInstanceName());
		// @formatter:on
		} else {
			val annJoinColumn = oneToManyField.addAnnotation(JoinColumn.class);
			annJoinColumn.setStringValue("name", EntityRepresentation.genFKName(composition.getTargetClass().getName()));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaField. EManyToOneField, br.xtool.core.representation.EUmlRelationship.EComposition)
	 */
	@Override
	public void visit(JavaFieldManyToOneType manyToOneField, PlantRelationshipComposition composition) {
		manyToOneField.addAnnotation(Fetch.class).setEnumValue(FetchMode.JOIN);
		val ann = manyToOneField.addAnnotation(ManyToOne.class);
		if (!composition.getSourceMultiplicity().isOptional()) ann.setLiteralValue("optional", "false");
	}

}
