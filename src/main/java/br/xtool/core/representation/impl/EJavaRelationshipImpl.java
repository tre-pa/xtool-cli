package br.xtool.core.representation.impl;

import java.util.Optional;

import br.xtool.core.representation.EJavaRelationship;
import br.xtool.core.representation.EJavaAttribute;
import br.xtool.core.representation.EJavaEntity;

public class EJavaRelationshipImpl implements EJavaRelationship {

	private EJavaEntity entitySource;

	private EJavaEntity entityTarget;

	private EJavaAttribute attributeSource;

	public EJavaRelationshipImpl(EJavaEntity source, EJavaEntity target, EJavaAttribute attributeSource) {
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
	public Optional<EJavaAttribute> getTargetAttribute() {
		// @formatter:off
		return this.entityTarget.getAttributes().stream()
				.filter(attrTarget -> attrTarget.getType().getName().equals(this.entityTarget.getName()))
				.findFirst();
		// @formatter:on
	}

	@Override
	public EJavaAttribute getSourceAttribute() {
		return this.attributeSource;
	}

	@Override
	public EJavaEntity getSourceEntity() {
		return this.entitySource;
	}

	@Override
	public Optional<EJavaEntity> getTargetEntity() {
		return Optional.ofNullable(this.entityTarget);
	}

}
