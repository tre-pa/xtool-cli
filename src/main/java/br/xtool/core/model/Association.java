package br.xtool.core.model;

import lombok.Getter;

public class Association implements Comparable<Association> {

	@Getter
	private Entity source;

	@Getter
	private Entity target;

	private Attribute attributeSource;

	public Association(Entity source, Entity target, Attribute attributeSource) {
		super();
		this.source = source;
		this.target = target;
		this.attributeSource = attributeSource;
	}

	/**
	 * Verifica se a associação é bidirecional;
	 * 
	 * @return
	 */
	public boolean isBidirectional() {
		// @formatter:off
		return target.getAssociations().stream()
				.anyMatch(association -> association.getTarget().getName().equals(this.source.getName()));
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

	@Override
	public int compareTo(Association o) {
		return this.target.getName().compareTo(o.getTarget().getName());
	}

}
