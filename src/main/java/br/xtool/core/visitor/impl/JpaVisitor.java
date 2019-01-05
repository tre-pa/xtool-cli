package br.xtool.core.visitor.impl;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;

import br.xtool.core.representation.JavaClassRepresentation;
import br.xtool.core.representation.JavaClassRepresentation.EAuditableJavaClass;
import br.xtool.core.representation.JavaClassRepresentation.ECacheableJavaClass;
import br.xtool.core.representation.JavaClassRepresentation.EIndexedJavaClass;
import br.xtool.core.representation.JavaClassRepresentation.EReadOnlyJavaClass;
import br.xtool.core.representation.JavaClassRepresentation.EVersionableJavaClass;
import br.xtool.core.representation.JavaClassRepresentation.EViewJavaClass;
import br.xtool.core.representation.JavaFieldRepresentation;
import br.xtool.core.representation.JavaFieldRepresentation.EBigDecimalField;
import br.xtool.core.representation.JavaFieldRepresentation.EBooleanField;
import br.xtool.core.representation.JavaFieldRepresentation.EByteField;
import br.xtool.core.representation.JavaFieldRepresentation.EEnumField;
import br.xtool.core.representation.JavaFieldRepresentation.EIntegerField;
import br.xtool.core.representation.JavaFieldRepresentation.ELocalDateField;
import br.xtool.core.representation.JavaFieldRepresentation.ELocalDateTimeField;
import br.xtool.core.representation.JavaFieldRepresentation.ELongField;
import br.xtool.core.representation.JavaFieldRepresentation.EManyToManyField;
import br.xtool.core.representation.JavaFieldRepresentation.EManyToOneField;
import br.xtool.core.representation.JavaFieldRepresentation.ENotNullField;
import br.xtool.core.representation.JavaFieldRepresentation.EOneToManyField;
import br.xtool.core.representation.JavaFieldRepresentation.EOneToOneField;
import br.xtool.core.representation.JavaFieldRepresentation.EStringField;
import br.xtool.core.representation.JavaFieldRepresentation.ETransientField;
import br.xtool.core.representation.JavaFieldRepresentation.EUniqueField;
import br.xtool.core.Visitor;
import br.xtool.core.representation.EntityRepresentation;
import br.xtool.core.representation.PlantClassRepresentation;
import br.xtool.core.representation.PlantClassFieldRepresentation;
import br.xtool.core.representation.PlantClassFieldPropertyRepresentation;
import br.xtool.core.representation.PlantRelationshipRepresentation;
import br.xtool.core.representation.PlantRelationshipRepresentation.EAssociation;
import br.xtool.core.representation.PlantRelationshipRepresentation.EComposition;
import br.xtool.core.representation.PlantStereotypeRepresentation;
import br.xtool.core.representation.PlantStereotypeRepresentation.StereotypeType;
import br.xtool.core.template.EntityTemplates;
import lombok.EqualsAndHashCode;
import lombok.val;

@Component
public class JpaVisitor implements Visitor {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaClass,
	 * br.xtool.core.representation.EUmlClass)
	 */
	@Override
	public void visit(JavaClassRepresentation javaClass, PlantClassRepresentation umlClass) {
		javaClass.addAnnotation(Entity.class);
		if (umlClass.getStereotypes().stream()
				.noneMatch(st -> st.getStereotypeType().equals(StereotypeType.READ_ONLY) || st.getStereotypeType().equals(StereotypeType.VIEW))) {
			javaClass.addAnnotation(DynamicInsert.class);
			javaClass.addAnnotation(DynamicUpdate.class);
		}
		umlClass.getTaggedValue("table.name").ifPresent(tagValue -> javaClass.addAnnotation(Table.class).setStringValue("name", tagValue));
		umlClass.getTaggedValue("table.schema").ifPresent(tagValue -> javaClass.addAnnotation(Table.class).setStringValue("schema", tagValue));
		umlClass.getTaggedValueAsArray("equalsAndHashCode").ifPresent(tagValues -> javaClass.addAnnotation(EqualsAndHashCode.class).setStringArrayValue("of", tagValues));
		javaClass.addAnnotation(JsonInclude.class).setEnumArrayValue(JsonInclude.Include.NON_EMPTY);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaClass,
	 * br.xtool.core.representation.EUmlStereotype)
	 */
	@Override
	public void visit(JavaClassRepresentation javaClass, PlantStereotypeRepresentation umlStereotype) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaClass.
	 * EAuditableJavaClass, br.xtool.core.representation.EUmlStereotype)
	 */
	@Override
	public void visit(EAuditableJavaClass auditableClass, PlantStereotypeRepresentation umlStereotype) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaClass.
	 * ECacheableJavaClass, br.xtool.core.representation.EUmlStereotype)
	 */
	@Override
	public void visit(ECacheableJavaClass cacheableClass, PlantStereotypeRepresentation umlStereotype) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaClass.
	 * EIndexedJavaClass, br.xtool.core.representation.EUmlStereotype)
	 */
	@Override
	public void visit(EIndexedJavaClass indexedClass, PlantStereotypeRepresentation umlStereotype) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaClass.
	 * EViewJavaClass, br.xtool.core.representation.EUmlStereotype)
	 */
	@Override
	public void visit(EViewJavaClass viewClass, PlantStereotypeRepresentation umlStereotype) {
		viewClass.addAnnotation(Immutable.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaClass.
	 * EReadOnlyJavaClass, br.xtool.core.representation.EUmlStereotype)
	 */
	@Override
	public void visit(EReadOnlyJavaClass readOnlyClass, PlantStereotypeRepresentation umlStereotype) {
		readOnlyClass.addAnnotation(Immutable.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaClass.
	 * EVersionableJavaClass, br.xtool.core.representation.EUmlStereotype)
	 */
	@Override
	public void visit(EVersionableJavaClass versionableClass, PlantStereotypeRepresentation umlStereotype) {
		JavaFieldRepresentation javaField = versionableClass.addField("version");
		javaField.getRoasterField().setPrivate();
		javaField.getRoasterField().setType(Long.class);
		javaField.addAnnotation(Version.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaField,
	 * br.xtool.core.representation.EUmlField)
	 */
	@Override
	public void visit(JavaFieldRepresentation javaField, PlantClassFieldRepresentation umlField) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaField.
	 * EStringField, br.xtool.core.representation.EUmlField)
	 */
	@Override
	public void visit(EStringField stringField, PlantClassFieldRepresentation umlField) {
		val annColumn = stringField.addAnnotation(Column.class);
		annColumn.setLiteralValue("length", String.valueOf(umlField.getMaxArrayLength().orElse(255)));
		umlField.getTaggedValue("column.name").ifPresent(tagValue -> annColumn.setStringValue("name", tagValue));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaField.
	 * EBooleanField, br.xtool.core.representation.EUmlField)
	 */
	@Override
	public void visit(EBooleanField booleanField, PlantClassFieldRepresentation umlField) {
		val annColumn = booleanField.addAnnotation(Column.class);
		umlField.getTaggedValue("column.name").ifPresent(tagValue -> annColumn.setStringValue("name", tagValue));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaField.
	 * ELongField, br.xtool.core.representation.EUmlField)
	 */
	@Override
	public void visit(ELongField longField, PlantClassFieldRepresentation umlField) {
		if (umlField.isId()) {
			longField.addAnnotation(Id.class);
			// @formatter:off
			val annColumn = longField.addAnnotation(Column.class)
				.setLiteralValue("updatable", "false")
				.setLiteralValue("nullable", "false");
			// @formatter:on
			umlField.getTaggedValue("column.name").ifPresent(tagValue -> annColumn.setStringValue("name", tagValue));
			longField.addGeneratedValueAnnotation(GenerationType.SEQUENCE);
			longField.addSequenceGeneratorAnnotation();
			return;

		}
		longField.addAnnotation(Column.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaField.
	 * EIntegerField, br.xtool.core.representation.EUmlField)
	 */
	@Override
	public void visit(EIntegerField integerField, PlantClassFieldRepresentation umlField) {
		val annColumn = integerField.addAnnotation(Column.class);
		umlField.getTaggedValue("column.name").ifPresent(tagValue -> annColumn.setStringValue("name", tagValue));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaField.
	 * EByteField, br.xtool.core.representation.EUmlField)
	 */
	@Override
	public void visit(EByteField byteField, PlantClassFieldRepresentation umlField) {
		val annColumn = byteField.addAnnotation(Lob.class);
		umlField.getTaggedValue("column.name").ifPresent(tagValue -> annColumn.setStringValue("name", tagValue));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaField.
	 * EBigDecimalField, br.xtool.core.representation.EUmlField)
	 */
	@Override
	public void visit(EBigDecimalField bigDecimalField, PlantClassFieldRepresentation umlField) {
		val annColumn = bigDecimalField.addAnnotation(Column.class);
		umlField.getTaggedValue("column.name").ifPresent(tagValue -> annColumn.setStringValue("name", tagValue));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaField.
	 * ELocalDateField, br.xtool.core.representation.EUmlField)
	 */
	@Override
	public void visit(ELocalDateField localDateField, PlantClassFieldRepresentation umlField) {
		val annColumn = localDateField.addAnnotation(Column.class);
		umlField.getTaggedValue("column.name").ifPresent(tagValue -> annColumn.setStringValue("name", tagValue));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaField.
	 * ELocalDateTimeField, br.xtool.core.representation.EUmlField)
	 */
	@Override
	public void visit(ELocalDateTimeField localDateTimeField, PlantClassFieldRepresentation umlField) {
		val annColumn = localDateTimeField.addAnnotation(Column.class);
		umlField.getTaggedValue("column.name").ifPresent(tagValue -> annColumn.setStringValue("name", tagValue));
	}

	@Override
	public void visit(EEnumField enumField, PlantClassFieldRepresentation umlField) {
		val annEnum = enumField.addAnnotation(Enumerated.class).setEnumValue(EnumType.STRING);
		annEnum.setEnumValue(EnumType.STRING);
		enumField.addAnnotation(Column.class).setLiteralValue("nullable", "false");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaField,
	 * br.xtool.core.representation.EUmlFieldProperty)
	 */
	@Override
	public void visit(JavaFieldRepresentation javaField, PlantClassFieldPropertyRepresentation umlFieldProperty) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaField.
	 * ENotNullField, br.xtool.core.representation.EUmlFieldProperty)
	 */
	@Override
	public void visit(ENotNullField notNullField, PlantClassFieldPropertyRepresentation property) {
		notNullField.addAnnotation(Column.class).setLiteralValue("nullable", "false");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaField.
	 * ETransientField, br.xtool.core.representation.EUmlFieldProperty)
	 */
	@Override
	public void visit(ETransientField transientField, PlantClassFieldPropertyRepresentation property) {
		transientField.addAnnotation(Transient.class);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaField.
	 * EUniqueField, br.xtool.core.representation.EUmlFieldProperty)
	 */
	@Override
	public void visit(EUniqueField uniqueField, PlantClassFieldPropertyRepresentation property) {
		uniqueField.addAnnotation(Column.class).setLiteralValue("unique", "true");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaField,
	 * br.xtool.core.representation.EUmlRelationship)
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
	 * @see
	 * br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaField.
	 * EOneToOneField, br.xtool.core.representation.EUmlRelationship.EAssociation)
	 */
	@Override
	public void visit(EOneToOneField oneToOneField, EAssociation association) {
		val annOneToOne = oneToOneField.addAnnotation(OneToOne.class);
		annOneToOne.setEnumValue("fetch", FetchType.LAZY);
		oneToOneField.addAnnotation(Fetch.class).setEnumValue(FetchMode.JOIN);
		if (!association.getSourceMultiplicity().isOptional())
			annOneToOne.setLiteralValue("optional", "false");
		// Bidirecional
		if (!association.isSourceClassOwner() && association.getNavigability().isBidirectional()) {
			annOneToOne.setStringValue("mappedBy", association.getTargetRole());
		} else {

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaField.
	 * EOneToManyField, br.xtool.core.representation.EUmlRelationship.EAssociation)
	 */
	@Override
	public void visit(EOneToManyField oneToManyField, EAssociation association) {
		oneToManyField.addBatchSizeAnnotation(10);
		oneToManyField.addLazyCollectionAnnotation(LazyCollectionOption.EXTRA);
		val annOneToMany = oneToManyField.addAnnotation(OneToMany.class);
		// Bidirecional
		if (association.getNavigability().isBidirectional()) {
			annOneToMany.setStringValue("mappedBy", association.getTargetRole());
			EntityTemplates.genAddListMethodRelationship(oneToManyField.getJavaClass(), association);
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
			EntityTemplates.genRemoveListMethodRelationship(oneToManyField.getJavaClass(), association);
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
	 * @see
	 * br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaField.
	 * EManyToOneField, br.xtool.core.representation.EUmlRelationship.EAssociation)
	 */
	@Override
	public void visit(EManyToOneField manyToOneField, EAssociation association) {
		val ann = manyToOneField.addAnnotation(ManyToOne.class);
		ann.setEnumValue("fetch", FetchType.LAZY);
		manyToOneField.addAnnotation(Fetch.class).setEnumValue(FetchMode.JOIN);
		if (!association.getSourceMultiplicity().isOptional())
			ann.setLiteralValue("optional", "false");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaField.
	 * EManyToManyField, br.xtool.core.representation.EUmlRelationship.EAssociation)
	 */
	@Override
	public void visit(EManyToManyField manyToManyField, EAssociation association) {
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
	 * @see
	 * br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaField.
	 * EOneToOneField, br.xtool.core.representation.EUmlRelationship.EComposition)
	 */
	@Override
	public void visit(EOneToOneField oneToOneField, EComposition composition) {
		val annOneToOne = oneToOneField.addAnnotation(OneToOne.class);
		annOneToOne.setEnumValue("fetch", FetchType.LAZY);
		annOneToOne.setEnumValue("cascade", CascadeType.ALL);
		annOneToOne.setLiteralValue("orphanRemoval", "true");
		oneToOneField.addAnnotation(Fetch.class).setEnumValue(FetchMode.JOIN);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaField.
	 * EOneToManyField, br.xtool.core.representation.EUmlRelationship.EComposition)
	 */
	@Override
	public void visit(EOneToManyField oneToManyField, EComposition composition) {
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
			EntityTemplates.genAddListMethodRelationship(oneToManyField.getJavaClass(), composition);
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
			EntityTemplates.genRemoveListMethodRelationship(oneToManyField.getJavaClass(), composition);
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
	 * @see
	 * br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaField.
	 * EManyToOneField, br.xtool.core.representation.EUmlRelationship.EComposition)
	 */
	@Override
	public void visit(EManyToOneField manyToOneField, EComposition composition) {
		manyToOneField.addAnnotation(Fetch.class).setEnumValue(FetchMode.JOIN);
		val ann = manyToOneField.addAnnotation(ManyToOne.class);
		if (!composition.getSourceMultiplicity().isOptional())
			ann.setLiteralValue("optional", "false");
	}

}
