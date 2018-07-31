package br.xtool.core.representation.impl;

import java.util.Optional;

import br.xtool.core.representation.EJpaRelationship;
import br.xtool.core.representation.EJpaAttribute;
import br.xtool.core.representation.EJpaEntity;

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
		// @formatter:off
		return this.entityTarget.getRelationships().stream()
				.filter(association -> association.getTargetEntity().isPresent())
				.anyMatch(association -> association.getTargetEntity().get().getName().equals(this.entitySource.getName()));
		// @formatter:on
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
		return this.attributeSource.hasAnnotation("OneToOne");
	}

	/**
	 * Verifica se a associação do atributo possui a annotation @OneToMany
	 * 
	 * @return
	 */
	@Override
	public boolean isOneToMany() {
		return this.attributeSource.hasAnnotation("OneToMany");
	}

	/**
	 * Verifica se a associação do atributo possui a annotation @ManyToOne
	 * 
	 * @return
	 */
	@Override
	public boolean isManyToOne() {
		return this.attributeSource.hasAnnotation("ManyToOne");
	}

	/**
	 * Verifica se a associação do atributo possui a annotation @ManyToMany
	 * 
	 * @return
	 */
	@Override
	public boolean isManyToMany() {
		return this.attributeSource.hasAnnotation("ManyToMany");
	}

	@Override
	public Optional<EJpaAttribute> getTargetAttribute() {
		// @formatter:off
		return this.entityTarget.getAttributes().stream()
				.filter(attrTarget -> attrTarget.getType().getName().equals(this.entityTarget.getName()))
				.findFirst();
		// @formatter:on
	}

	@Override
	public EJpaAttribute getSourceAttribute() {
		return this.attributeSource;
	}

	@Override
	public EJpaEntity getSourceEntity() {
		return this.entitySource;
	}

	@Override
	public Optional<EJpaEntity> getTargetEntity() {
		return Optional.ofNullable(this.entityTarget);
	}

}
