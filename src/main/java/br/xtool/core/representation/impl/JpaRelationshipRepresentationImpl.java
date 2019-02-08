package br.xtool.core.representation.impl;

import java.util.Optional;

import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.jboss.forge.roaster.model.util.Types;

import br.xtool.core.representation.springboot.EntityAttributeRepresentation;
import br.xtool.core.representation.springboot.EntityRepresentation;
import br.xtool.core.representation.springboot.JpaRelationshipRepresentation;
import lombok.val;

public class JpaRelationshipRepresentationImpl extends JavaRelationshipRepresentationImpl implements JpaRelationshipRepresentation {

	private EntityRepresentation sourceEntity;

	private EntityRepresentation targetEntity;

	private EntityAttributeRepresentation sourceAttribute;

	public JpaRelationshipRepresentationImpl(EntityRepresentation sourceEntity, EntityRepresentation targetEntity, EntityAttributeRepresentation sourceAttribute) {
		super(sourceEntity, targetEntity, sourceAttribute);
		this.sourceEntity = sourceEntity;
		this.targetEntity = targetEntity;
		this.sourceAttribute = sourceAttribute;
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

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EJpaRelationship#isAssociation()
	 */
	@Override
	public boolean isAssociation() {
		return !this.isComposition();
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EJpaRelationship#isComposition()
	 */
	@Override
	public boolean isComposition() {
		if (isOneToOne()) {
			val ann = this.getSourceField().getRoasterField().getAnnotation(OneToOne.class);
			return Boolean.valueOf(ann.getLiteralValue("orphanRemoval"));
		} else if (isOneToMany()) {
			val ann = this.getSourceField().getRoasterField().getAnnotation(OneToMany.class);
			return Boolean.valueOf(ann.getLiteralValue("orphanRemoval"));
		}
		return false;
	}

	/**
	 * Verifica se a associação do atributo possui a annotation @OneToOne
	 * 
	 * @return
	 */
	@Override
	public boolean isOneToOne() {
		return this.sourceAttribute.getRoasterField().hasAnnotation(OneToOne.class);
	}

	/**
	 * Verifica se a associação do atributo possui a annotation @OneToMany
	 * 
	 * @return
	 */
	@Override
	public boolean isOneToMany() {
		return this.sourceAttribute.getRoasterField().hasAnnotation(OneToMany.class);
	}

	/**
	 * Verifica se a associação do atributo possui a annotation @ManyToOne
	 * 
	 * @return
	 */
	@Override
	public boolean isManyToOne() {
		return this.sourceAttribute.getRoasterField().hasAnnotation(ManyToOne.class);
	}

	/**
	 * Verifica se a associação do atributo possui a annotation @ManyToMany
	 * 
	 * @return
	 */
	@Override
	public boolean isManyToMany() {
		return this.sourceAttribute.getRoasterField().hasAnnotation(ManyToMany.class);
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EJpaRelationship#getTargetAttribute()
	 */
	@Override
	public Optional<EntityAttributeRepresentation> getTargetAttribute() {
		if (this.isManyToMany() || this.isManyToOne()) {
			// @formatter:off
			return this.targetEntity.getAttributes().stream()
					.filter(attrTarget -> Types.getGenericsTypeParameter(attrTarget.getType().getQualifiedNameWithGenerics()).equals(this.sourceEntity.getName()))
					.findFirst();
			// @formatter:on
		}
		// @formatter:off
		return this.targetEntity.getAttributes().stream()
				.filter(attrTarget -> attrTarget.getType().getName().equals(this.sourceEntity.getName()))
				.findFirst();
		// @formatter:on
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EJpaRelationship#getSourceAttribute()
	 */
	@Override
	public EntityAttributeRepresentation getSourceAttribute() {
		return this.sourceAttribute;
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EJpaRelationship#getSourceEntity()
	 */
	@Override
	public EntityRepresentation getSourceEntity() {
		return this.sourceEntity;
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EJpaRelationship#getTargetEntity()
	 */
	@Override
	public EntityRepresentation getTargetEntity() {
		return this.targetEntity;
	}

}
