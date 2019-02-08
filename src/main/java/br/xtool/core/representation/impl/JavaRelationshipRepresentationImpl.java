package br.xtool.core.representation.impl;

import java.util.Optional;
import java.util.function.Predicate;

import org.jboss.forge.roaster.model.util.Types;

import br.xtool.core.representation.springboot.JavaClassRepresentation;
import br.xtool.core.representation.springboot.JavaFieldRepresentation;
import br.xtool.core.representation.springboot.JavaRelationshipRepresentation;

public class JavaRelationshipRepresentationImpl implements JavaRelationshipRepresentation {

	private JavaClassRepresentation sourceClass;
	private JavaClassRepresentation targetClass;
	private JavaFieldRepresentation sourceField;

	public JavaRelationshipRepresentationImpl(JavaClassRepresentation sourceClass, JavaClassRepresentation targetClass, JavaFieldRepresentation sourceField) {
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
	public JavaClassRepresentation getSourceClass() {
		return this.sourceClass;
	}

	@Override
	public JavaClassRepresentation getTargetClass() {
		return this.targetClass;
	}

	@Override
	public JavaFieldRepresentation getSourceField() {
		return this.sourceField;
	}

	@Override
	public Optional<JavaFieldRepresentation> getTargetField() {
		Predicate<JavaFieldRepresentation> listPredicate = javaField -> javaField.isCollection()
				&& Types.getGenericsTypeParameter(javaField.getType().getQualifiedNameWithGenerics()).equals(this.sourceClass.getName());
		Predicate<JavaFieldRepresentation> nonListPredicate = javaField -> javaField.getType().getName().equals(this.sourceClass.getName());
		// @formatter:off
		return this.targetClass.getJavaFields().stream()
				.filter(listPredicate.or(nonListPredicate))
				.findFirst();
		// @formatter:on
	}

}
