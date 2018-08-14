package br.xtool.core.visitor.impl;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.stereotype.Component;

import br.xtool.core.representation.EJavaClass;
import br.xtool.core.representation.EJavaField;
import br.xtool.core.representation.EJavaField.EBigDecimalField;
import br.xtool.core.representation.EJavaField.EBooleanField;
import br.xtool.core.representation.EJavaField.EByteField;
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
import br.xtool.core.representation.EUmlField;
import br.xtool.core.representation.EUmlFieldProperty;
import br.xtool.core.representation.EUmlRelationship;
import br.xtool.core.representation.EUmlRelationship.EAssociation;
import br.xtool.core.representation.EUmlRelationship.EComposition;
import br.xtool.core.representation.EUmlStereotype;
import br.xtool.core.template.JavaClassTemplates;
import br.xtool.core.visitor.Visitor;
import lombok.val;

@Component
public class JpaVisitor implements Visitor {

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaClass, br.xtool.core.representation.EUmlClass)
	 */
	@Override
	public void visit(EJavaClass javaClass) {
		javaClass.addAnnotation(Entity.class);
		javaClass.addAnnotation(DynamicInsert.class);
		javaClass.addAnnotation(DynamicUpdate.class);
		javaClass.addTableAnnotation();
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaClass, br.xtool.core.representation.EUmlStereotype)
	 */
	@Override
	public void visit(EJavaClass javaClass, EUmlStereotype umlStereotype) {

	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaField, br.xtool.core.representation.EUmlField)
	 */
	@Override
	public void visit(EJavaField javaField, EUmlField umlField) {
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaField.EStringField, br.xtool.core.representation.EUmlField)
	 */
	@Override
	public void visit(EStringField stringField, EUmlField umlField) {
		stringField.addAnnotation(Column.class).setLiteralValue("length", String.valueOf(umlField.getMaxArrayLength().orElse(255)));
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaField.EBooleanField, br.xtool.core.representation.EUmlField)
	 */
	@Override
	public void visit(EBooleanField booleanField, EUmlField umlField) {
		booleanField.addAnnotation(Column.class);
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaField.ELongField, br.xtool.core.representation.EUmlField)
	 */
	@Override
	public void visit(ELongField longField, EUmlField umlField) {
		if (umlField.isId()) {
			longField.addAnnotation(Id.class);
			longField.addGeneratedValueAnnotation(GenerationType.SEQUENCE);
			longField.addSequenceGeneratorAnnotation();
			return;
		}
		longField.addAnnotation(Column.class);
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaField.EIntegerField, br.xtool.core.representation.EUmlField)
	 */
	@Override
	public void visit(EIntegerField integerField, EUmlField umlField) {
		integerField.addAnnotation(Column.class);
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaField.EByteField, br.xtool.core.representation.EUmlField)
	 */
	@Override
	public void visit(EByteField byteField, EUmlField umlField) {
		byteField.addAnnotation(Lob.class);
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaField.EBigDecimalField, br.xtool.core.representation.EUmlField)
	 */
	@Override
	public void visit(EBigDecimalField bigDecimalField, EUmlField umlField) {
		bigDecimalField.addAnnotation(Column.class);
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaField.ELocalDateField, br.xtool.core.representation.EUmlField)
	 */
	@Override
	public void visit(ELocalDateField localDateField, EUmlField umlField) {
		localDateField.addAnnotation(Column.class);
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaField.ELocalDateTimeField, br.xtool.core.representation.EUmlField)
	 */
	@Override
	public void visit(ELocalDateTimeField localDateTimeField, EUmlField umlField) {
		localDateTimeField.addAnnotation(Column.class);
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaField, br.xtool.core.representation.EUmlFieldProperty)
	 */
	@Override
	public void visit(EJavaField javaField, EUmlFieldProperty umlFieldProperty) {
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaField.ENotNullField, br.xtool.core.representation.EUmlFieldProperty)
	 */
	@Override
	public void visit(ENotNullField notNullField, EUmlFieldProperty property) {
		notNullField.addAnnotation(Column.class).setLiteralValue("nullable", "false");

	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaField.ETransientField, br.xtool.core.representation.EUmlFieldProperty)
	 */
	@Override
	public void visit(ETransientField transientField, EUmlFieldProperty property) {
		transientField.addAnnotation(Transient.class);

	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaField.EUniqueField, br.xtool.core.representation.EUmlFieldProperty)
	 */
	@Override
	public void visit(EUniqueField uniqueField, EUmlFieldProperty property) {
		uniqueField.addAnnotation(Column.class).setLiteralValue("unique", "true");
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaField, br.xtool.core.representation.EUmlRelationship)
	 */
	@Override
	public void visit(EJavaField javaField, EUmlRelationship umlRelationship) {
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaField.EOneToOneField, br.xtool.core.representation.EUmlRelationship.EAssociation)
	 */
	@Override
	public void visit(EOneToOneField oneToOneField, EAssociation association) {
		val annOneToOne = oneToOneField.addAnnotation(OneToOne.class);
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
			JavaClassTemplates.genAddListMethodRelationship(oneToManyField.getJavaClass(), association);
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
			JavaClassTemplates.genRemoveListMethodRelationship(oneToManyField.getJavaClass(), association);
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
			JavaClassTemplates.genAddListMethodRelationship(oneToManyField.getJavaClass(), composition);
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
			JavaClassTemplates.genRemoveListMethodRelationship(oneToManyField.getJavaClass(), composition);
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
