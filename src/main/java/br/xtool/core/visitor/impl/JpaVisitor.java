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
import br.xtool.core.representation.EJavaField.ENotNullField;
import br.xtool.core.representation.EJavaField.EStringField;
import br.xtool.core.representation.EJavaField.ETransientField;
import br.xtool.core.representation.EJavaField.EUniqueField;
import br.xtool.core.representation.EJpaEntity;
import br.xtool.core.representation.EUmlField;
import br.xtool.core.representation.EUmlFieldProperty;
import br.xtool.core.representation.EUmlRelationship;
import br.xtool.core.representation.EUmlStereotype;
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
		visitManyToMany(javaField, umlRelationship);
		visitManyToOne(javaField, umlRelationship);
		visitOneToMany(javaField, umlRelationship);
		visitOneToOne(javaField, umlRelationship);
	}

	/*
	 * Visitor ManyToMany
	 */
	private void visitManyToMany(EJavaField javaField, EUmlRelationship umlRelationship) {
		if (umlRelationship.getSourceMultiplicity().isToMany() && umlRelationship.getTargetMultiplicity().isToMany()) {
			javaField.addBatchSizeAnnotation(10);
			javaField.addLazyCollectionAnnotation(LazyCollectionOption.EXTRA);
			val annMany = javaField.addAnnotation(ManyToMany.class);
			// Bidirecional
			if (!umlRelationship.isSourceClassOwner() && umlRelationship.getNavigability().isBidirectional()) {
				annMany.setStringValue("mappedBy", umlRelationship.getTargetRole());
			}
		}
	}

	/*
	 * Visitor OneToMany
	 */
	private void visitOneToMany(EJavaField javaField, EUmlRelationship umlRelationship) {
		if (umlRelationship.getSourceMultiplicity().isToMany() && umlRelationship.getTargetMultiplicity().isToOne()) {
			javaField.addBatchSizeAnnotation(10);
			javaField.addLazyCollectionAnnotation(LazyCollectionOption.EXTRA);
			val annOneToMany = javaField.addAnnotation(OneToMany.class);
			// Bidirecional
			if (umlRelationship.getNavigability().isBidirectional()) {
				annOneToMany.setStringValue("mappedBy", umlRelationship.getTargetRole());
				// @formatter:off
				// add
				javaField.getJavaClass().addMethod(String.format("add%s", umlRelationship.getTargetClass().getName()))
					.getRoasterMethod()
						.setReturnTypeVoid()
						.setBody(String.format("this.%s.add(%s); %s.set%s(this);", 
								umlRelationship.getSourceRole(), 
								umlRelationship.getTargetClass().getInstanceName(), 
								umlRelationship.getTargetClass().getInstanceName(),
								umlRelationship.getSourceClass().getName()))
						.addParameter(umlRelationship.getTargetClass().getName(), umlRelationship.getTargetClass().getInstanceName());
				// remove
				javaField.getJavaClass().addMethod(String.format("remove%s", umlRelationship.getTargetClass().getName()))
				.getRoasterMethod()
					.setReturnTypeVoid()
					.setBody(String.format("this.%s.remove(%s); %s.set%s(null);", 
							umlRelationship.getSourceRole(), 
							umlRelationship.getTargetClass().getInstanceName(), 
							umlRelationship.getTargetClass().getInstanceName(),
							umlRelationship.getSourceClass().getName()))
					.addParameter(umlRelationship.getTargetClass().getName(), umlRelationship.getTargetClass().getInstanceName());
			// @formatter:on
			} else {
				val annJoinColumn = javaField.addAnnotation(JoinColumn.class);
				annJoinColumn.setStringValue("name", EJpaEntity.genFKName(umlRelationship.getTargetClass().getName()));
			}
			if (umlRelationship.isComposition()) {
				annOneToMany.setEnumValue("cascade", CascadeType.ALL);
				annOneToMany.setLiteralValue("orphanRemoval", "true");
			}
		}
	}

	/*
	 * Visitor ManyToOne
	 */
	private void visitManyToOne(EJavaField javaField, EUmlRelationship umlRelationship) {
		if (umlRelationship.getSourceMultiplicity().isToOne() && umlRelationship.getTargetMultiplicity().isToMany()) {
			val ann = javaField.addAnnotation(ManyToOne.class);
			if (!umlRelationship.getSourceMultiplicity().isOptional()) ann.setLiteralValue("optional", "false");
		}
	}

	/*
	 * Visitor OneToOne
	 */
	private void visitOneToOne(EJavaField javaField, EUmlRelationship umlRelationship) {
		if (umlRelationship.getSourceMultiplicity().isToOne() && umlRelationship.getTargetMultiplicity().isToOne()) {
			val annOneToOne = javaField.addAnnotation(OneToOne.class);
			if (!umlRelationship.getSourceMultiplicity().isOptional()) annOneToOne.setLiteralValue("optional", "false");
			// Bidirecional
			if (!umlRelationship.isSourceClassOwner() && umlRelationship.getNavigability().isBidirectional()) {
				annOneToOne.setStringValue("mappedBy", umlRelationship.getTargetRole());
			} else {

			}
			if (umlRelationship.isComposition()) {
				annOneToOne.setEnumValue("cascade", CascadeType.ALL);
				annOneToOne.setLiteralValue("orphanRemoval", "true");
			}
		}
	}
}
