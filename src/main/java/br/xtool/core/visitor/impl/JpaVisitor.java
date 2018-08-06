package br.xtool.core.visitor.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
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

@Component
public class JpaVisitor implements Visitor {

	@Override
	public void visit(EJavaClass javaClass, EUmlClass umlClass) {
		javaClass.addAnnotation(Entity.class);
		javaClass.addAnnotation(DynamicInsert.class);
		javaClass.addAnnotation(DynamicUpdate.class);
		javaClass.addAnnotation(Table.class).setStringValue("name", EJpaEntity.genDBTableName(javaClass.getName()));
	}

	@Override
	public void visit(EJavaClass javaClass, EUmlStereotype umlStereotype) {

	}

	@Override
	public void visit(EJavaField javaField, EUmlField umlField) {
		switch (umlField.getType()) {
		case LONG:
			if (umlField.isId()) {
				javaField.addAnnotation(Id.class);
				// @formatter:off
				javaField.addAnnotation(GeneratedValue.class)
					.setEnumValue("strategy", GenerationType.SEQUENCE)
					.setStringValue("generator", EJpaEntity.genDBSequenceName(javaField.getJavaClass().getName()));
				javaField.addAnnotation(SequenceGenerator.class)
					.setLiteralValue("initialValue", "1")
					.setLiteralValue("allocationSize", "1")
					.setStringValue("name", EJpaEntity.genDBSequenceName(javaField.getJavaClass().getName()))
					.setStringValue("sequenceName", EJpaEntity.genDBSequenceName(javaField.getJavaClass().getName()));
				// @formatter:on
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

	@Override
	public void visit(EJavaField javaField, EUmlRelationship umlRelationship) {
	}

}
