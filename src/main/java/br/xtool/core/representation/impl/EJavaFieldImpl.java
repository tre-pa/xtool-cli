package br.xtool.core.representation.impl;

import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jboss.forge.roaster.model.Type;
import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;

import br.xtool.core.representation.EJavaAnnotation;
import br.xtool.core.representation.EJavaField;

public class EJavaFieldImpl implements EJavaField {

	protected FieldSource<JavaClassSource> fieldSource;

	public EJavaFieldImpl(FieldSource<JavaClassSource> fieldSource) {
		super();
		this.fieldSource = fieldSource;
	}

	/**
	 * Retorna o nome do attributo.
	 * 
	 * @return
	 */
	@Override
	public String getName() {
		return this.fieldSource.getName();
	}

	/**
	 * Retorna o tipo do atributo.
	 * 
	 * @return
	 */
	@Override
	public Type<JavaClassSource> getType() {
		return this.fieldSource.getType();
	}

	/**
	 * Verifica se o atributo é uma coleção
	 * 
	 * @return
	 */
	@Override
	public boolean isCollection() {
		return Stream.of("List", "Set", "Collection").peek(type -> this.getType().getName()).anyMatch(type -> type.equals(this.getType().getName()));
	}

	/**
	 * Verifica se o atributo é static.
	 * 
	 * @return
	 */
	@Override
	public boolean isStatic() {
		return this.fieldSource.isStatic();
	}

	@Override
	public SortedSet<EJavaAnnotation> getAnnotations() {
		// @formatter:off
		return this.fieldSource.getAnnotations()
				.stream()
				.map(EJavaAnnotationImpl::new)
				.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

	@Override
	public boolean isFinal() {
		return this.fieldSource.isFinal();
	}

	@Override
	public boolean isPackagePrivate() {
		return this.fieldSource.isPackagePrivate();
	}

	@Override
	public boolean isPublic() {
		return this.fieldSource.isPublic();
	}

	@Override
	public boolean isPrivate() {
		return this.fieldSource.isPrivate();
	}

	@Override
	public boolean isProtected() {
		return this.fieldSource.isProtected();
	}

	@Override
	public boolean hasJavaDoc() {
		return this.fieldSource.hasJavaDoc();
	}

	@Override
	public boolean isTransient() {
		return this.fieldSource.isTransient();
	}

	@Override
	public boolean isVolatile() {
		return this.fieldSource.isVolatile();
	}

	@Override
	public int getLineNumber() {
		return this.fieldSource.getLineNumber();
	}

	@Override
	public FieldSource<JavaClassSource> getRoasterFieldSource() {
		return this.fieldSource;
	}

	@Override
	public int compareTo(EJavaField o) {
		return this.getName().compareTo(o.getName());
	}

}
