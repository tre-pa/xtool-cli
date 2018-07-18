package br.xtool.core.representation.impl;

import java.util.Optional;

import br.xtool.core.representation.EAssociation;
import br.xtool.core.representation.EAttribute;
import br.xtool.core.representation.EEntity;

public class EAssociationImpl implements EAssociation {

	private EEntity entitySource;

	private EEntity entityTarget;

	private EAttribute attributeSource;

	public EAssociationImpl(EEntity source, EEntity target, EAttribute attributeSource) {
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
		return this.entityTarget.getAssociations().stream()
				.filter(association -> association.getEntityTarget().isPresent())
				.anyMatch(association -> association.getEntityTarget().get().getName().equals(this.entitySource.getName()));
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
	public boolean isSingleAssociation() {
		return !this.isCollectionAssociation();
	}

	/**
	 * Verifica se a associação é @ManyToMany ou @OneToMany
	 * 
	 * @return
	 */
	@Override
	public boolean isCollectionAssociation() {
		return this.attributeSource.isCollection();
	}

	/**
	 * Verifica se a associação do atributo possui a annotation @OneToOne
	 * 
	 * @return
	 */
	@Override
	public boolean isOneToOneAnnotation() {
		return this.attributeSource.hasAnnotation("OneToOne");
	}

	/**
	 * Verifica se a associação do atributo possui a annotation @OneToMany
	 * 
	 * @return
	 */
	@Override
	public boolean isOneToManyAnnotation() {
		return this.attributeSource.hasAnnotation("OneToMany");
	}

	/**
	 * Verifica se a associação do atributo possui a annotation @ManyToOne
	 * 
	 * @return
	 */
	@Override
	public boolean isManyToOneAnnotation() {
		return this.attributeSource.hasAnnotation("ManyToOne");
	}

	/**
	 * Verifica se a associação do atributo possui a annotation @ManyToMany
	 * 
	 * @return
	 */
	@Override
	public boolean isManyToManyAnnotation() {
		return this.attributeSource.hasAnnotation("ManyToMany");
	}

	@Override
	public Optional<EAttribute> getAttributeTarget() {
		// @formatter:off
		return this.entityTarget.getAttributes().stream()
				.filter(attrTarget -> attrTarget.getType().getName().equals(this.entityTarget.getName()))
				.findFirst();
		// @formatter:on
	}

	@Override
	public EAttribute getAttributeSource() {
		return this.attributeSource;
	}

	@Override
	public EEntity getEntitySource() {
		return this.entitySource;
	}

	@Override
	public Optional<EEntity> getEntityTarget() {
		return Optional.ofNullable(this.entityTarget);
	}

}
