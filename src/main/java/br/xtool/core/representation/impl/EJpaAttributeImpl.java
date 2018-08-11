package br.xtool.core.representation.impl;

import java.util.Optional;

import javax.persistence.Lob;
import javax.persistence.Transient;

import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.util.Types;

import br.xtool.core.representation.EBootProject;
import br.xtool.core.representation.EJpaAttribute;
import br.xtool.core.representation.EJpaEntity;
import br.xtool.core.representation.EJpaRelationship;

/**
 * Classe que representa um atributo JPA de uma entidade.
 * 
 * @author jcruz
 *
 */
public class EJpaAttributeImpl extends EJavaFieldImpl implements EJpaAttribute {

	private EBootProject springBootProject;

	private EJpaEntity entitySource;

	public EJpaAttributeImpl(EJpaEntity entitySource, FieldSource<JavaClassSource> fieldSource) {
		super(entitySource, fieldSource);
		this.entitySource = entitySource;
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EJpaAttribute#isJpaTransient()
	 */
	@Override
	public boolean isJpaTransient() {
		return this.getRoasterField().hasAnnotation(Transient.class);
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EJpaAttribute#isLob()
	 */
	@Override
	public boolean isLob() {
		return this.getRoasterField().hasAnnotation(Lob.class);
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EJpaAttribute#getRelationship()
	 */
	@Override
	public Optional<EJpaRelationship> getJpaRelationship() {
		if (this.isCollection()) {
			String entityName = Types.getGenericsTypeParameter(this.getType().getQualifiedNameWithGenerics());
			// @formatter:off
			return this.springBootProject.getEntities().stream()
					.filter(entity -> entity.getName().equals(entityName))
					.map(entityTarget -> new EJpaRelationshipImpl(this.entitySource, entityTarget, this))
					.map(EJpaRelationship.class::cast)
					.findFirst();
			// @formatter:on
		}
		// @formatter:off
		String entityName = this.getType().getName();
		return this.springBootProject.getEntities().stream()
				.filter(entity -> entity.getName().equals(entityName))
				.map(entityTarget -> new EJpaRelationshipImpl(this.entitySource, entityTarget, this))
				.map(EJpaRelationship.class::cast)
				.findFirst();
		// @formatter:on
	}

}
