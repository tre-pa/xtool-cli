package br.xtool.core.representation.impl;

import java.util.Collection;
import java.util.stream.Collectors;

import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.jboss.forge.roaster.model.source.JavaClassSource;

import br.xtool.core.representation.SpringBootProjectRepresentation;
import br.xtool.core.representation.EntityAttributeRepresentation;
import br.xtool.core.representation.EntityRepresentation;

/**
 * Classe que representa um entidade JPA
 * 
 * @author jcruz
 *
 */
public class EJpaEntityImpl extends EJavaClassImpl implements EntityRepresentation {

	public EJpaEntityImpl(SpringBootProjectRepresentation springBootProject, JavaClassSource javaClassSource) {
		super(springBootProject, javaClassSource);
		this.javaClassSource = javaClassSource;
	}

	/**
	 * Retorna os atributos da classe.
	 * 
	 * @return
	 */
	@Override
	public Collection<EntityAttributeRepresentation> getAttributes() {
		// @formatter:off
		return this.javaClassSource.getFields().stream()
			.filter(fieldSource -> !fieldSource.isStatic())
			.map(fieldSource -> new EJpaAttributeImpl(this.getProject(),this, fieldSource))
			.collect(Collectors.toList());
		// @formatter:on
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.EJpaEntity#getSimpleAttributes()
	 */
	@Override
	public Collection<EntityAttributeRepresentation> getSimpleAttributes() {
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
	public Collection<EntityAttributeRepresentation> getRelationshipAttributes() {
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
	public Collection<EntityAttributeRepresentation> getEnumAttributes() {
		// @formatter:off
		return this.getAttributes().stream()
				.filter(attr -> attr.getEnum().isPresent())
				.collect(Collectors.toList());
		// @formatter:on
	}

	@Override
	public Collection<EntityAttributeRepresentation> getToManyRelationshipAttributes() {
		// @formatter:off
		return this.getRelationshipAttributes().stream()
				.filter(attr -> attr.getRoasterField().hasAnnotation(ManyToMany.class) || attr.getRoasterField().hasAnnotation(OneToMany.class))
				.collect(Collectors.toList());
		// @formatter:on
	}

	@Override
	public Collection<EntityAttributeRepresentation> getToOneRelationshipAttributes() {
		// @formatter:off
		return this.getRelationshipAttributes().stream()
				.filter(attr -> attr.getRoasterField().hasAnnotation(OneToOne.class) || attr.getRoasterField().hasAnnotation(ManyToOne.class))
				.collect(Collectors.toList());
		// @formatter:on
	}

}
