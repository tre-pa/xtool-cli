package br.xtool.core.representation.impl;

import java.util.Optional;

import br.xtool.core.representation.ERelationship;
import br.xtool.core.representation.EAttribute;
import br.xtool.core.representation.EEntity;

public class ERelationshipImpl implements ERelationship {

	private EEntity entitySource;

	private EEntity entityTarget;

	private EAttribute attributeSource;

	public ERelationshipImpl(EEntity source, EEntity target, EAttribute attributeSource) {
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
		return this.entityTarget.getRelationship().stream()
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
	public Optional<EAttribute> getTargetAttribute() {
		// @formatter:off
		return this.entityTarget.getAttributes().stream()
				.filter(attrTarget -> attrTarget.getType().getName().equals(this.entityTarget.getName()))
				.findFirst();
		// @formatter:on
	}

	@Override
	public EAttribute getSourceAttribute() {
		return this.attributeSource;
	}

	@Override
	public EEntity getSourceEntity() {
		return this.entitySource;
	}

	@Override
	public Optional<EEntity> getTargetEntity() {
		return Optional.ofNullable(this.entityTarget);
	}

}
