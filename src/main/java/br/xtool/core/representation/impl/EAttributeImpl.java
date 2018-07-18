package br.xtool.core.representation.impl;

import java.util.Optional;

import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;

import br.xtool.core.representation.EAttribute;
import br.xtool.core.representation.EEntity;
import br.xtool.core.representation.ESpringBootProject;

/**
 * Classe que representa um atributo JPA de uma entidade.
 * 
 * @author jcruz
 *
 */
public class EAttributeImpl extends EFieldImpl implements EAttribute {

	private ESpringBootProject springBootProject;

	private EEntity entitySource;

	public EAttributeImpl(ESpringBootProject springBootProject, EEntity entitySource, FieldSource<JavaClassSource> fieldSource) {
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
	public boolean isJpaTransient() {
		return this.hasAnnotation("Transient");
	}

	@Override
	public boolean isJpaLob() {
		return this.hasAnnotation("Lob");
	}

	@Override
	public Optional<EAssociationImpl> getAssociation() {
		if (this.isAssociation()) {
			if (this.isCollection()) {
				// @formatter:off
				return this.springBootProject.getEntities().stream()
						.filter(entity -> this.getType().getTypeArguments().stream()
								.anyMatch(type -> type.getName().equals(entity.getName())))
						.map(entityTarget -> new EAssociationImpl(this.entitySource, entityTarget, this))
						.findFirst();
				// @formatter:on
			}
			// @formatter:off
			return this.springBootProject.getEntities().stream()
					.filter(entity -> entity.getName().equals(this.getType().getName()))
					.map(entityTarget -> new EAssociationImpl(this.entitySource, entityTarget, this))
					.findFirst();
			// @formatter:on
		}
		return Optional.empty();
	}

}
