package br.xtool.core.visitor.jpa.impl;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.springframework.stereotype.Component;

import br.xtool.core.representation.EJavaField;
import br.xtool.core.representation.EJpaEntity;
import br.xtool.core.representation.EUmlField;
import br.xtool.core.visitor.jpa.JpaFieldVisitor;

@Component
public class JpaIdFieldVisitor implements JpaFieldVisitor {

	@Override
	public boolean test(EUmlField umlField) {
		return umlField.isId();
	}

	@Override
	public void accept(EJavaField javaField, EUmlField umlField) {
		// @formatter:off
		javaField.addAnnotation(Id.class);
		javaField.addAnnotation(GeneratedValue.class)
			.setEnumValue("strategy", GenerationType.SEQUENCE)
			.setStringValue("generator", EJpaEntity.genDBSequenceName(javaField.getRoasterField().getOrigin().getName()));
		javaField.addAnnotation(SequenceGenerator.class)
			.setLiteralValue("initialValue", "1")
			.setLiteralValue("allocationSize", "1")
			.setStringValue("name", EJpaEntity.genDBSequenceName(javaField.getRoasterField().getOrigin().getName()))
			.setStringValue("sequenceName", EJpaEntity.genDBSequenceName(javaField.getRoasterField().getOrigin().getName()));
		// @formatter:on
	}

}
