package br.xtool.core.representation.impl;

import java.util.Optional;

import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.jboss.forge.roaster.model.util.Types;

import br.xtool.core.representation.EJpaAttribute;
import br.xtool.core.representation.EJpaEntity;
import br.xtool.core.representation.EJpaRelationship;

public class EJpaRelationshipImpl implements EJpaRelationship {

	private EJpaEntity entitySource;

	private EJpaEntity entityTarget;

	private EJpaAttribute attributeSource;

	public EJpaRelationshipImpl(EJpaEntity source, EJpaEntity target, EJpaAttribute attributeSource) {
		super();
		this.entitySource = source;
		this.entityTarget = target;
		this.attributeSource = attributeSource;
	}

	/**
	 * Verifica se a associação é bidirecional;
	 * 
	 * @return
	 */
	@Override
	public boolean isBidirectional() {
		return this.getTargetAttribute().isPresent();
	}

	/**
	 * Verifica se a associação é unidirecional.
	 * 
	 * @return
	 */
	@Override
	public boolean isUnidirectional() {
		return !this.isBidirectional();
	}

	/**
	 * Verifica se a associação é @OneToOne ou @ManyToOne
	 * 
	 * @return
	 */
	@Override
	public boolean isSingleRelationship() {
		return !this.isCollectionRelationship();
	}

	/**
	 * Verifica se a associação é @ManyToMany ou @OneToMany
	 * 
	 * @return
	 */
	@Override
	public boolean isCollectionRelationship() {
		return this.attributeSource.isCollection();
	}

	/**
	 * Verifica se a associação do atributo possui a annotation @OneToOne
	 * 
	 * @return
	 */
	@Override
	public boolean isOneToOne() {
		return this.attributeSource.getRoasterFieldSource().hasAnnotation(OneToOne.class);
	}

	/**
	 * Verifica se a associação do atributo possui a annotation @OneToMany
	 * 
	 * @return
	 */
	@Override
	public boolean isOneToMany() {
		return this.attributeSource.getRoasterFieldSource().hasAnnotation(OneToMany.class);
	}

	/**
	 * Verifica se a associação do atributo possui a annotation @ManyToOne
	 * 
	 * @return
	 */
	@Override
	public boolean isManyToOne() {
		return this.attributeSource.getRoasterFieldSource().hasAnnotation(ManyToOne.class);
	}

	/**
	 * Verifica se a associação do atributo possui a annotation @ManyToMany
	 * 
	 * @return
	 */
	@Override
	public boolean isManyToMany() {
		return this.attributeSource.getRoasterFieldSource().hasAnnotation(ManyToMany.class);
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EJpaRelationship#getTargetAttribute()
	 */
	@Override
	public Optional<EJpaAttribute> getTargetAttribute() {
		if (this.isManyToMany() || this.isManyToOne()) {
			// @formatter:off
			return this.entityTarget.getAttributes().stream()
					.filter(attrTarget -> Types.getGenericsTypeParameter(attrTarget.getType().getQualifiedNameWithGenerics()).equals(this.entitySource.getName()))
					.findFirst();
			// @formatter:on
		}
		// @formatter:off
		return this.entityTarget.getAttributes().stream()
				.filter(attrTarget -> attrTarget.getType().getName().equals(this.entitySource.getName()))
				.findFirst();
		// @formatter:on
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EJpaRelationship#getSourceAttribute()
	 */
	@Override
	public EJpaAttribute getSourceAttribute() {
		return this.attributeSource;
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EJpaRelationship#getSourceEntity()
	 */
	@Override
	public EJpaEntity getSourceEntity() {
		return this.entitySource;
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EJpaRelationship#getTargetEntity()
	 */
	@Override
	public EJpaEntity getTargetEntity() {
		return this.entityTarget;
	}

}
