package br.xtool.core.representation.impl;

import java.util.Optional;

import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;

import br.xtool.core.representation.EJpaAttribute;
import br.xtool.core.representation.EJpaRelationship;
import br.xtool.core.representation.EBootProject;

/**
 * Classe que representa um atributo JPA de uma entidade.
 * 
 * @author jcruz
 *
 */
public class EJpaAttributeImpl extends EJavaFieldImpl implements EJpaAttribute {

	private EBootProject springBootProject;

	private EJpaEntityImpl entitySource;

	public EJpaAttributeImpl(EBootProject springBootProject, EJpaEntityImpl entitySource, FieldSource<JavaClassSource> fieldSource) {
		super(fieldSource);
		this.springBootProject = springBootProject;
		this.entitySource = entitySource;
	}

	@Override
	public boolean isAssociation() {
		if (this.isCollection()) {
			// @formatter:off
//			return this.springBootProject.getEntities().parallelStream()
//					.anyMatch(entity -> this.getType().getTypeArguments().stream()
//												.anyMatch(t -> t.getName().equals(entity.getName())));
			return true;
			// @formatter:on
		}
		// @formatter:off
		return this.springBootProject.getEntities().parallelStream()
				.anyMatch(entity -> entity.getName().equals(this.getType().getName()));
		// @formatter:on
	}

	@Override
	public boolean isTransient() {
		return this.hasAnnotation("Transient");
	}

	@Override
	public boolean isLob() {
		return this.hasAnnotation("Lob");
	}

	@Override
	public Optional<EJpaRelationship> getRelationship() {
		if (this.isAssociation()) {
			if (this.isCollection()) {
				// @formatter:off
				return this.springBootProject.getEntities().stream()
						.filter(entity -> this.getType().getTypeArguments().stream().anyMatch(type -> type.getName().equals(entity.getName())))
						.map(entityTarget -> new EJpaRelationshipImpl(this.entitySource, entityTarget, this))
						.map(EJpaRelationship.class::cast)
						.findFirst();
				// @formatter:on
			}
			// @formatter:off
			return this.springBootProject.getEntities().stream()
					.filter(entity -> entity.getName().equals(this.getType().getName()))
					.map(entityTarget -> new EJpaRelationshipImpl(this.entitySource, entityTarget, this))
					.map(EJpaRelationship.class::cast)
					.findFirst();
			// @formatter:on
		}
		return Optional.empty();
	}

}
