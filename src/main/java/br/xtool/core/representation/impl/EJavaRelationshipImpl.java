package br.xtool.core.representation.impl;

import java.util.Optional;
import java.util.function.Predicate;

import org.jboss.forge.roaster.model.util.Types;

import br.xtool.core.representation.EJavaClass;
import br.xtool.core.representation.EJavaField;
import br.xtool.core.representation.EJavaRelationship;

public class EJavaRelationshipImpl implements EJavaRelationship {

	private EJavaClass sourceClass;
	private EJavaClass targetClass;
	private EJavaField sourceField;

	public EJavaRelationshipImpl(EJavaClass sourceClass, EJavaClass targetClass, EJavaField sourceField) {
		this.sourceClass = sourceClass;
		this.targetClass = targetClass;
		this.sourceField = sourceField;
	}

	@Override
	public boolean isBidirectional() {
		return this.getTargetField().isPresent();
	}

	@Override
	public boolean isUnidirectional() {
		return !this.isBidirectional();
	}

	@Override
	public EJavaClass getSourceClass() {
		return this.sourceClass;
	}

	@Override
	public EJavaClass getTargetClass() {
		return this.targetClass;
	}

	@Override
	public EJavaField getSourceField() {
		return this.sourceField;
	}

	@Override
	public Optional<EJavaField> getTargetField() {
		Predicate<EJavaField> listPredicate = javaField -> javaField.isCollection()
				&& Types.getGenericsTypeParameter(javaField.getType().getQualifiedNameWithGenerics()).equals(this.sourceClass.getName());
		Predicate<EJavaField> nonListPredicate = javaField -> javaField.getType().getName().equals(this.sourceClass.getName());
		// @formatter:off
		return this.targetClass.getFields().stream()
				.filter(listPredicate.or(nonListPredicate))
				.findFirst();
		// @formatter:on
	}

}
