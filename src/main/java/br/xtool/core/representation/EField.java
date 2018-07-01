package br.xtool.core.representation;

import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jboss.forge.roaster.model.Type;
import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;

import lombok.Getter;

public class EField implements Comparable<EAttribute> {

	protected FieldSource<JavaClassSource> fieldSource;

	@Getter(lazy = true)
	private final SortedSet<EAnnotation> annotations = buildAnnotations();

	public EField(FieldSource<JavaClassSource> fieldSource) {
		super();
		this.fieldSource = fieldSource;
	}

	/**
	 * Retorna o nome do attributo.
	 * 
	 * @return
	 */
	public String getName() {
		return this.fieldSource.getName();
	}

	/**
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.fieldSource.setName(name);
	}

	/**
	 * Retorna o tipo do atributo.
	 * 
	 * @return
	 */
	public Type<JavaClassSource> getType() {
		return this.fieldSource.getType();
	}

	/**
	 * Verifica se o atributo é uma coleção
	 * 
	 * @return
	 */
	public boolean isCollection() {
		return Stream.of("List", "Set", "Collection").anyMatch(type -> type.equals(this.getType().getName()));
	}

	/**
	 * Verifica se o atributo é static.
	 * 
	 * @return
	 */
	public boolean isStatic() {
		return this.fieldSource.isStatic();
	}

	public boolean hasAnnotation(String name) {
		return this.fieldSource.hasAnnotation(name);
	}

	public void setStringInitialize(String value) {
		this.fieldSource.setStringInitializer(value);
	}

	public void setLiteralInitialize(String value) {
		this.fieldSource.setLiteralInitializer(value);
	}

	private SortedSet<EAnnotation> buildAnnotations() {
		// @formatter:off
		return this.fieldSource.getAnnotations()
				.stream()
				.map(EAnnotation::new)
				.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

	public EAnnotation addAnnotation() {
		return new EAnnotation(this.fieldSource.addAnnotation());
	}

	@Override
	public int compareTo(EAttribute o) {
		return this.getName().compareTo(o.getName());
	}

}
