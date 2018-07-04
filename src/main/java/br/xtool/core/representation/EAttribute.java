package br.xtool.core.representation;

import java.util.Optional;

import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;

/**
 * Classe que representa um atributo JPA de uma entidade.
 * 
 * @author jcruz
 *
 */
public class EAttribute extends EField {

	private ESpringBootProject springBootProject;

	private EEntity entitySource;

	public EAttribute(ESpringBootProject springBootProject, EEntity entitySource, FieldSource<JavaClassSource> fieldSource) {
		super(entitySource, fieldSource);
		this.springBootProject = springBootProject;
		this.entitySource = entitySource;
	}

	public boolean isAssociation() {
		if (this.isCollection()) {
			// @formatter:off
			return this.springBootProject.getEntities().parallelStream()
					.anyMatch(entity -> this.getType().getTypeArguments().stream()
												.anyMatch(t -> t.getName().equals(entity.getName())));
			// @formatter:on
		}
		// @formatter:off
		return this.springBootProject.getEntities().parallelStream()
				.anyMatch(entity -> entity.getName().equals(this.getType().getName()));
		// @formatter:on
	}

	public boolean isJpaTransient() {
		return this.hasAnnotation("Transient");
	}

	public boolean isJpaLob() {
		return this.hasAnnotation("Lob");
	}

	public Optional<EAssociation> getAssociation() {
		if (this.isAssociation()) {
			if (this.isCollection()) {
				// @formatter:off
				return this.springBootProject.getEntities().stream()
						.filter(entity -> this.getType().getTypeArguments().stream()
								.anyMatch(type -> type.getName().equals(entity.getName())))
						.map(entityTarget -> new EAssociation(entitySource, entityTarget, this))
						.findFirst();
				// @formatter:on
			}
			// @formatter:off
			return this.springBootProject.getEntities().stream()
					.filter(entity -> entity.getName().equals(this.getType().getName()))
					.map(entityTarget -> new EAssociation(entitySource, entityTarget, this))
					.findFirst();
			// @formatter:on
		}
		return Optional.empty();
	}

}
