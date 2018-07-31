package br.xtool.core.representation;

import java.util.Optional;

/**
 * Representação de um relacionamento java JPA.
 * 
 * @author jcruz
 *
 */
public interface EJpaRelationship {

	/**
	 * Verifica se o relacionamento é bidirecional.
	 * 
	 * @return
	 */
	boolean isBidirectional();

	/**
	 * Verifica se o relacionamento é unidirecional.
	 * 
	 * @return
	 */
	boolean isUnidirectional();

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

	EJpaAttribute getSourceAttribute();

	Optional<EJpaAttribute> getTargetAttribute();

	EJpaEntity getSourceEntity();

	EJpaEntity getTargetEntity();
}
