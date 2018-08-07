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
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.stereotype.Component;

import br.xtool.core.representation.EJavaClass;
import br.xtool.core.representation.EJavaField;
import br.xtool.core.representation.EJpaEntity;
import br.xtool.core.representation.EUmlClass;
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
	public void visit(EJavaClass javaClass, EUmlClass umlClass) {
		javaClass.addAnnotation(Entity.class);
		javaClass.addAnnotation(DynamicInsert.class);
		javaClass.addAnnotation(DynamicUpdate.class);
		javaClass.addAnnotation(Table.class).setStringValue("name", EJpaEntity.genDBTableName(javaClass.getName()));
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
		switch (umlField.getType()) {
		case LONG:
			if (umlField.isId()) {
				javaField.addAnnotation(Id.class);
				javaField.addGeneratedValue(GenerationType.SEQUENCE);
				javaField.addSequenceGenerator();
				return;
			}
			javaField.addAnnotation(Column.class);
			break;
		case STRING:
			javaField.addAnnotation(Column.class).setLiteralValue("length", String.valueOf(umlField.getMaxArrayLength().orElse(255)));
			break;
		case BIGDECIMAL:
			javaField.addAnnotation(Column.class);
			break;
		case INTEGER:
			javaField.addAnnotation(Column.class);
			break;
		case BOOLEAN:
			javaField.addAnnotation(Column.class);
			break;
		case BYTE:
			javaField.addAnnotation(Lob.class);
			break;
		case LOCALDATE:
			javaField.addAnnotation(Column.class);
			break;
		case LOCALDATETIME:
			javaField.addAnnotation(Column.class);
			break;
		default:
			break;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.visitor.Visitor#visit(br.xtool.core.representation.EJavaField, br.xtool.core.representation.EUmlFieldProperty)
	 */
	@Override
	public void visit(EJavaField javaField, EUmlFieldProperty umlFieldProperty) {
		switch (umlFieldProperty.getFieldProperty()) {
		case NOTNULL:
			javaField.addAnnotation(Column.class).setLiteralValue("nullable", "false");
			break;
		case TRANSIENT:
			javaField.addAnnotation(Transient.class);
			break;
		case UNIQUE:
			javaField.addAnnotation(Column.class).setLiteralValue("unique", "true");
			break;
		default:
			break;
		}
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
			javaField.addBatchSize(10);
			javaField.addLazyCollection(LazyCollectionOption.EXTRA);
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
			javaField.addBatchSize(10);
			javaField.addLazyCollection(LazyCollectionOption.EXTRA);
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
