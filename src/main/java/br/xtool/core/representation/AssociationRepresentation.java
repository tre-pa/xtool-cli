package br.xtool.core.representation;

import java.util.Optional;

import lombok.Getter;

public class AssociationRepresentation implements Comparable<AssociationRepresentation> {

	@Getter
	private EntityRepresentation entitySource;

	@Getter
	private EntityRepresentation entityTarget;

	@Getter
	private AttributeRepresentation attributeSource;

	public AssociationRepresentation(EntityRepresentation source, EntityRepresentation target, AttributeRepresentation attributeSource) {
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
	public boolean isBidirectional() {
		// @formatter:off
		return entityTarget.getAssociations().stream()
				.anyMatch(association -> association.getEntityTarget().getName().equals(this.entitySource.getName()));
		// @formatter:on
	}

	/**
	 * Verifica se a associação é unidirecional.
	 * 
	 * @return
	 */
	public boolean isUnidirectional() {
		return !this.isBidirectional();
	}

	/**
	 * Verifica se a associação é @OneToOne ou @ManyToOne
	 * 
	 * @return
	 */
	public boolean isSingleAssociation() {
		return !this.isCollectionAssociation();
	}

	/**
	 * Verifica se a associação é @ManyToMany ou @OneToMany
	 * 
	 * @return
	 */
	public boolean isCollectionAssociation() {
		return this.attributeSource.isCollection();
	}

	/**
	 * Verifica se a associação do atributo possui a annotation @OneToOne
	 * 
	 * @return
	 */
	public boolean hasOneToOneAnnotation() {
		return this.attributeSource.hasAnnotation("OneToOne");
	}

	/**
	 * Verifica se a associação do atributo possui a annotation @OneToMany
	 * 
	 * @return
	 */
	public boolean hasOneToManyAnnotation() {
		return this.attributeSource.hasAnnotation("OneToMany");
	}

	/**
	 * Verifica se a associação do atributo possui a annotation @ManyToOne
	 * 
	 * @return
	 */
	public boolean hasManyToOneAnnotation() {
		return this.attributeSource.hasAnnotation("ManyToOne");
	}

	/**
	 * Verifica se a associação do atributo possui a annotation @ManyToMany
	 * 
	 * @return
	 */
	public boolean hasManyToManyAnnotation() {
		return this.attributeSource.hasAnnotation("ManyToMany");
	}

	public Optional<AttributeRepresentation> getAttributeTarget() {
		// @formatter:off
		return this.entityTarget.getAttributes().stream()
				.filter(attrTarget -> attrTarget.getType().getName().equals(entityTarget.getName()))
				.findFirst();
		// @formatter:on
	}

	@Override
	public int compareTo(AssociationRepresentation o) {
		return this.entityTarget.getName().compareTo(o.getEntityTarget().getName());
	}

}
