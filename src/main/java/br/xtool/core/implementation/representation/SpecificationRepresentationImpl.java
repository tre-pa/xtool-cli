package br.xtool.core.implementation.representation;

import org.jboss.forge.roaster.model.source.JavaClassSource;

import br.xtool.core.representation.springboot.EntityRepresentation;
import br.xtool.core.representation.springboot.SpecificationRepresentation;
import br.xtool.core.representation.springboot.SpringBootProjectRepresentation;

public class SpecificationRepresentationImpl extends JavaClassRepresentationImpl implements SpecificationRepresentation {

	public SpecificationRepresentationImpl(SpringBootProjectRepresentation project, JavaClassSource javaClassSource) {
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
