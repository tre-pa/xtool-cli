package br.xtool.core.representation.impl;

import org.jboss.forge.roaster.model.source.JavaClassSource;

import br.xtool.core.representation.EBootProject;
import br.xtool.core.representation.EJpaEntity;
import br.xtool.core.representation.EJpaSpecification;

public class EJpaSpecificationImpl extends EJavaClassImpl implements EJpaSpecification {

	public EJpaSpecificationImpl(EBootProject project, JavaClassSource javaClassSource) {
		super(project, javaClassSource);
	}

	@Override
	public EJpaEntity getTargetEntity() {
		// @formatter:off
		return this.getProject().getEntities().stream()
					.filter(jpaEntity -> jpaEntity.getName().concat("Specification").equals(this.getName()))
					.findFirst()
					.orElseThrow(() -> new IllegalArgumentException(String.format("A specification '%s' não está associada a nehuma classe Jpa.", this.getName())));
		// @formatter:on
	}

}
