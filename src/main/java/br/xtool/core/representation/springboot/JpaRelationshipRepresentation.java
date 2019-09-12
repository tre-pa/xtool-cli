package br.xtool.core.representation.springboot;

import java.util.Optional;

/**
 * Representação de um relacionamento java JPA.
 * 
 * @author jcruz
 *
 */
public interface JpaRelationshipRepresentation extends JavaRelationshipRepresentation {

	/**
	 * Verifica se o relacionamento é uma associação.
	 * 
	 * @return
	 */
	boolean isAssociation();

	/**
	 * Verifica se o relacionamento é uma composição.
	 * 
	 * @return
	 */
	boolean isComposition();

	/**
	 * Retorna se o relacionamento é OneToOne.
	 * 
	 * @return
	 */
	boolean isOneToOne();

	/**
	 * Retorna se o relacionamento OneToMany.
	 * 
	 * @return
	 */
	boolean isOneToMany();

	/**
	 * Retorna se o relacionamento é ManyToOne.
	 * 
	 * @return
	 */
	boolean isManyToOne();

	/**
	 * Retorna se o relacionamento é ManyToMany
	 * 
	 * @return
	 */
	boolean isManyToMany();

	JpaEntityAttributeRepresentation getSourceAttribute();

	Optional<JpaEntityAttributeRepresentation> getTargetAttribute();

	JpaEntityRepresentation getSourceEntity();

	JpaEntityRepresentation getTargetEntity();
}
