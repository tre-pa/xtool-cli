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
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.stereotype.Component;

import br.xtool.core.representation.EJavaClass;
import br.xtool.core.representation.EJavaClass.EAuditableJavaClass;
import br.xtool.core.representation.EJavaClass.ECacheableJavaClass;
import br.xtool.core.representation.EJavaClass.EIndexedJavaClass;
import br.xtool.core.representation.EJavaClass.EReadOnlyJavaClass;
import br.xtool.core.representation.EJavaClass.EVersionableJavaClass;
import br.xtool.core.representation.EJavaClass.EViewJavaClass;
import br.xtool.core.representation.EJavaField;
import br.xtool.core.representation.EJavaField.EBigDecimalField;
import br.xtool.core.representation.EJavaField.EBooleanField;
import br.xtool.core.representation.EJavaField.EByteField;
import br.xtool.core.representation.EJavaField.EEnumField;
import br.xtool.core.representation.EJavaField.EIntegerField;
import br.xtool.core.representation.EJavaField.ELocalDateField;
import br.xtool.core.representation.EJavaField.ELocalDateTimeField;
import br.xtool.core.representation.EJavaField.ELongField;
import br.xtool.core.representation.EJavaField.EManyToManyField;
import br.xtool.core.representation.EJavaField.EManyToOneField;
import br.xtool.core.representation.EJavaField.ENotNullField;
import br.xtool.core.representation.EJavaField.EOneToManyField;
import br.xtool.core.representation.EJavaField.EOneToOneField;
import br.xtool.core.representation.EJavaField.EStringField;
import br.xtool.core.representation.EJavaField.ETransientField;
import br.xtool.core.representation.EJavaField.EUniqueField;
import br.xtool.core.representation.EJpaEntity;
import br.xtool.core.representation.EPlantClass;
import br.xtool.core.representation.EPlantField;
import br.xtool.core.representation.EPlantFieldProperty;
import br.xtool.core.representation.EPlantRelationship;
import br.xtool.core.representation.EPlantRelationship.EAssociation;
import br.xtool.core.representation.EPlantRelationship.EComposition;
import br.xtool.core.representation.EPlantStereotype;
import br.xtool.core.representation.EPlantStereotype.StereotypeType;
import br.xtool.core.template.JpaEntityTemplates;
import br.xtool.core.visitor.Visitor;
import lombok.EqualsAndHashCode;
import lombok.val;

@Component
public class JpaVisitor implements Visitor {

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaClass, br.xtool.core.representation.EUmlClass)
	 */
	@Override
	public void visit(EJavaClass javaClass, EPlantClass umlClass) {
		javaClass.addAnnotation(Entity.class);
		if (umlClass.getStereotypes().stream().noneMatch(st -> st.getStereotypeType().equals(StereotypeType.READ_ONLY) || st.getStereotypeType().equals(StereotypeType.VIEW))) {
			javaClass.addAnnotation(DynamicInsert.class);
			javaClass.addAnnotation(DynamicUpdate.class);
		}
		umlClass.getTaggedValue("table.name").ifPresent(tagValue -> javaClass.addAnnotation(Table.class).setStringValue("name", tagValue));
		umlClass.getTaggedValue("table.schema").ifPresent(tagValue -> javaClass.addAnnotation(Table.class).setStringValue("schema", tagValue));
		umlClass.getTaggedValueAsArray("equalsAndHashCode").ifPresent(tagValues -> javaClass.addAnnotation(EqualsAndHashCode.class).setStringArrayValue("of", tagValues));
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaClass, br.xtool.core.representation.EUmlStereotype)
	 */
	@Override
	public void visit(EJavaClass javaClass, EPlantStereotype umlStereotype) {

	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaClass.EAuditableJavaClass, br.xtool.core.representation.EUmlStereotype)
	 */
	@Override
	public void visit(EAuditableJavaClass auditableClass, EPlantStereotype umlStereotype) {

	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaClass.ECacheableJavaClass, br.xtool.core.representation.EUmlStereotype)
	 */
	@Override
	public void visit(ECacheableJavaClass cacheableClass, EPlantStereotype umlStereotype) {

	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaClass.EIndexedJavaClass, br.xtool.core.representation.EUmlStereotype)
	 */
	@Override
	public void visit(EIndexedJavaClass indexedClass, EPlantStereotype umlStereotype) {

	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaClass.EViewJavaClass, br.xtool.core.representation.EUmlStereotype)
	 */
	@Override
	public void visit(EViewJavaClass viewClass, EPlantStereotype umlStereotype) {
		viewClass.addAnnotation(Immutable.class);
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaClass.EReadOnlyJavaClass, br.xtool.core.representation.EUmlStereotype)
	 */
	@Override
	public void visit(EReadOnlyJavaClass readOnlyClass, EPlantStereotype umlStereotype) {
		readOnlyClass.addAnnotation(Immutable.class);
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaClass.EVersionableJavaClass, br.xtool.core.representation.EUmlStereotype)
	 */
	@Override
	public void visit(EVersionableJavaClass versionableClass, EPlantStereotype umlStereotype) {
		EJavaField javaField = versionableClass.addField("version");
		javaField.getRoasterField().setPrivate();
		javaField.getRoasterField().setType(Long.class);
		javaField.addAnnotation(Version.class);
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaField, br.xtool.core.representation.EUmlField)
	 */
	@Override
	public void visit(EJavaField javaField, EPlantField umlField) {
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaField.EStringField, br.xtool.core.representation.EUmlField)
	 */
	@Override
	public void visit(EStringField stringField, EPlantField umlField) {
		val annColumn = stringField.addAnnotation(Column.class);
		annColumn.setLiteralValue("length", String.valueOf(umlField.getMaxArrayLength().orElse(255)));
		umlField.getTaggedValue("column.name").ifPresent(tagValue -> annColumn.setStringValue("name", tagValue));
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaField.EBooleanField, br.xtool.core.representation.EUmlField)
	 */
	@Override
	public void visit(EBooleanField booleanField, EPlantField umlField) {
		val annColumn = booleanField.addAnnotation(Column.class);
		umlField.getTaggedValue("column.name").ifPresent(tagValue -> annColumn.setStringValue("name", tagValue));
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaField.ELongField, br.xtool.core.representation.EUmlField)
	 */
	@Override
	public void visit(ELongField longField, EPlantField umlField) {
		if (umlField.isId()) {
			longField.addAnnotation(Id.class);
			// @formatter:off
			val annColumn = longField.addAnnotation(Column.class)
				.setLiteralValue("updatable", "false")
				.setLiteralValue("nullable", "false");
			// @formatter:on
			umlField.getTaggedValue("column.name").ifPresent(tagValue -> annColumn.setStringValue("name", tagValue));
			if (!longField.getJavaClass().hasAnnotation(Immutable.class)) {
				longField.addGeneratedValueAnnotation(GenerationType.SEQUENCE);
				longField.addSequenceGeneratorAnnotation();
			}
			return;

		}
		longField.addAnnotation(Column.class);
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaField.EIntegerField, br.xtool.core.representation.EUmlField)
	 */
	@Override
	public void visit(EIntegerField integerField, EPlantField umlField) {
		val annColumn = integerField.addAnnotation(Column.class);
		umlField.getTaggedValue("column.name").ifPresent(tagValue -> annColumn.setStringValue("name", tagValue));
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaField.EByteField, br.xtool.core.representation.EUmlField)
	 */
	@Override
	public void visit(EByteField byteField, EPlantField umlField) {
		val annColumn = byteField.addAnnotation(Lob.class);
		umlField.getTaggedValue("column.name").ifPresent(tagValue -> annColumn.setStringValue("name", tagValue));
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaField.EBigDecimalField, br.xtool.core.representation.EUmlField)
	 */
	@Override
	public void visit(EBigDecimalField bigDecimalField, EPlantField umlField) {
		val annColumn = bigDecimalField.addAnnotation(Column.class);
		umlField.getTaggedValue("column.name").ifPresent(tagValue -> annColumn.setStringValue("name", tagValue));
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaField.ELocalDateField, br.xtool.core.representation.EUmlField)
	 */
	@Override
	public void visit(ELocalDateField localDateField, EPlantField umlField) {
		val annColumn = localDateField.addAnnotation(Column.class);
		umlField.getTaggedValue("column.name").ifPresent(tagValue -> annColumn.setStringValue("name", tagValue));
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaField.ELocalDateTimeField, br.xtool.core.representation.EUmlField)
	 */
	@Override
	public void visit(ELocalDateTimeField localDateTimeField, EPlantField umlField) {
		val annColumn = localDateTimeField.addAnnotation(Column.class);
		umlField.getTaggedValue("column.name").ifPresent(tagValue -> annColumn.setStringValue("name", tagValue));
	}
	
	@Override
	public void visit(EEnumField enumField, EPlantField umlField) {
		val annEnum = enumField.addAnnotation(Enumerated.class);
		annEnum.setEnumValue(EnumType.STRING);
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaField, br.xtool.core.representation.EUmlFieldProperty)
	 */
	@Override
	public void visit(EJavaField javaField, EPlantFieldProperty umlFieldProperty) {
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaField.ENotNullField, br.xtool.core.representation.EUmlFieldProperty)
	 */
	@Override
	public void visit(ENotNullField notNullField, EPlantFieldProperty property) {
		notNullField.addAnnotation(Column.class).setLiteralValue("nullable", "false");

	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaField.ETransientField, br.xtool.core.representation.EUmlFieldProperty)
	 */
	@Override
	public void visit(ETransientField transientField, EPlantFieldProperty property) {
		transientField.addAnnotation(Transient.class);

	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaField.EUniqueField, br.xtool.core.representation.EUmlFieldProperty)
	 */
	@Override
	public void visit(EUniqueField uniqueField, EPlantFieldProperty property) {
		uniqueField.addAnnotation(Column.class).setLiteralValue("unique", "true");
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaField, br.xtool.core.representation.EUmlRelationship)
	 */
	@Override
	public void visit(EJavaField javaField, EPlantRelationship umlRelationship) {
		if (javaField.isEnum()) {
			javaField.addAnnotation(Enumerated.class).setEnumValue(EnumType.STRING);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaField.EOneToOneField, br.xtool.core.representation.EUmlRelationship.EAssociation)
	 */
	@Override
	public void visit(EOneToOneField oneToOneField, EAssociation association) {
		val annOneToOne = oneToOneField.addAnnotation(OneToOne.class);
		annOneToOne.setEnumValue("fetch", FetchType.LAZY);
		if (!association.getSourceMultiplicity().isOptional()) annOneToOne.setLiteralValue("optional", "false");
		// Bidirecional
		if (!association.isSourceClassOwner() && association.getNavigability().isBidirectional()) {
			annOneToOne.setStringValue("mappedBy", association.getTargetRole());
		} else {

		}
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaField.EOneToManyField, br.xtool.core.representation.EUmlRelationship.EAssociation)
	 */
	@Override
	public void visit(EOneToManyField oneToManyField, EAssociation association) {
		oneToManyField.addBatchSizeAnnotation(10);
		oneToManyField.addLazyCollectionAnnotation(LazyCollectionOption.EXTRA);
		val annOneToMany = oneToManyField.addAnnotation(OneToMany.class);
		// Bidirecional
		if (association.getNavigability().isBidirectional()) {
			annOneToMany.setStringValue("mappedBy", association.getTargetRole());
			JpaEntityTemplates.genAddListMethodRelationship(oneToManyField.getJavaClass(), association);
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
			JpaEntityTemplates.genRemoveListMethodRelationship(oneToManyField.getJavaClass(), association);
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
			annJoinColumn.setStringValue("name", EJpaEntity.genFKName(association.getTargetClass().getName()));
		}
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaField.EManyToOneField, br.xtool.core.representation.EUmlRelationship.EAssociation)
	 */
	@Override
	public void visit(EManyToOneField manyToOneField, EAssociation association) {
		val ann = manyToOneField.addAnnotation(ManyToOne.class);
		ann.setEnumValue("fetch", FetchType.LAZY);
		if (!association.getSourceMultiplicity().isOptional()) ann.setLiteralValue("optional", "false");
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaField.EManyToManyField, br.xtool.core.representation.EUmlRelationship.EAssociation)
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
	 * @see br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaField.EOneToOneField, br.xtool.core.representation.EUmlRelationship.EComposition)
	 */
	@Override
	public void visit(EOneToOneField oneToOneField, EComposition composition) {
		val annOneToOne = oneToOneField.addAnnotation(OneToOne.class);
		annOneToOne.setEnumValue("fetch", FetchType.LAZY);
		annOneToOne.setEnumValue("cascade", CascadeType.ALL);
		annOneToOne.setLiteralValue("orphanRemoval", "true");
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaField.EOneToManyField, br.xtool.core.representation.EUmlRelationship.EComposition)
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
			JpaEntityTemplates.genAddListMethodRelationship(oneToManyField.getJavaClass(), composition);
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
			JpaEntityTemplates.genRemoveListMethodRelationship(oneToManyField.getJavaClass(), composition);
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
			annJoinColumn.setStringValue("name", EJpaEntity.genFKName(composition.getTargetClass().getName()));
		}
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaField.EManyToOneField, br.xtool.core.representation.EUmlRelationship.EComposition)
	 */
	@Override
	public void visit(EManyToOneField manyToOneField, EComposition composition) {
		val ann = manyToOneField.addAnnotation(ManyToOne.class);
		if (!composition.getSourceMultiplicity().isOptional()) ann.setLiteralValue("optional", "false");
	}

}
