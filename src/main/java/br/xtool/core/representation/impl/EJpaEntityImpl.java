package br.xtool.core.representation.impl;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jboss.forge.roaster.model.source.JavaClassSource;

import br.xtool.core.representation.EBootProject;
import br.xtool.core.representation.EJpaAttribute;
import br.xtool.core.representation.EJpaEntity;

/**
 * Classe que representa um entidade JPA
 * 
 * @author jcruz
 *
 */
public class EJpaEntityImpl extends EJavaClassImpl implements EJpaEntity {

	public EJpaEntityImpl(EBootProject springBootProject, JavaClassSource javaClassSource) {
		super(springBootProject, javaClassSource);
		this.javaClassSource = javaClassSource;
	}

	/**
	 * Retorna os atributos da classe.
	 * 
	 * @return
	 */
	@Override
	public Collection<EJpaAttribute> getAttributes() {
		// @formatter:off
		return this.javaClassSource.getFields().stream()
			.filter(fieldSource -> !fieldSource.isStatic())
			.map(fieldSource -> new EJpaAttributeImpl(this, fieldSource))
			.collect(Collectors.toList());
		// @formatter:on
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.EJpaEntity#getSimpleAttributes()
	 */
	@Override
	public Collection<EJpaAttribute> getSimpleAttributes() {
		// @formatter:off
		return this.getAttributes().stream()
				.filter(attr ->  !attr.getJpaRelationship().isPresent())
				.filter(attr -> !attr.getEnum().isPresent())
				.collect(Collectors.toList());
		// @formatter:on

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.EJpaEntity#getRelationshipAttributes()
	 */
	@Override
	public Collection<EJpaAttribute> getRelationshipAttributes() {
		// @formatter:off
		return this.getAttributes().stream()
				.filter(attr -> attr.getJpaRelationship().isPresent())
				.collect(Collectors.toList());
		// @formatter:on
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.EJpaEntity#getEnumAttributes()
	 */
	@Override
	public Collection<EJpaAttribute> getEnumAttributes() {
		// @formatter:off
		return this.getAttributes().stream()
				.filter(attr -> attr.getEnum().isPresent())
				.collect(Collectors.toList());
		// @formatter:on
	}

	@Override
	public Collection<EJpaAttribute> getToManyRelationshipAtttributes() {
		// @formatter:off
		return this.getRelationshipAttributes().stream()
				.filter(attr -> Stream.of("List", "Set", "Collection").anyMatch(type ->  attr.getType().getName().equals(type)))
				.collect(Collectors.toList());
		// @formatter:on
	}

	@Override
	public Collection<EJpaAttribute> getToOneRelationshipAttributes() {
		// @formatter:off
		return this.getRelationshipAttributes().stream()
				.filter(attr -> Stream.of("List", "Set", "Collection").noneMatch(type ->  attr.getType().getName().equals(type)))
				.collect(Collectors.toList());
		// @formatter:on
	}

}
