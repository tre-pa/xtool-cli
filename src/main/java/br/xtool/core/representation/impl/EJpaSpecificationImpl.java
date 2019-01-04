package br.xtool.core.representation.impl;

import org.jboss.forge.roaster.model.source.JavaClassSource;

import br.xtool.core.representation.SpringBootProjectRepresentation;
import br.xtool.core.representation.EntityRepresentation;
import br.xtool.core.representation.SpecificationRepresentation;

public class EJpaSpecificationImpl extends EJavaClassImpl implements SpecificationRepresentation {

	public EJpaSpecificationImpl(SpringBootProjectRepresentation project, JavaClassSource javaClassSource) {
		super(project, javaClassSource);
	}

	@Override
	public EntityRepresentation getTargetEntity() {
		// @formatter:off
		return this.getProject().getEntities().stream()
					.filter(jpaEntity -> jpaEntity.getName().concat("Specification").equals(this.getName()))
					.findFirst()
					.orElseThrow(() -> new IllegalArgumentException(String.format("A specification '%s' não está associada a nehuma classe Jpa.", this.getName())));
		// @formatter:on
	}

}
