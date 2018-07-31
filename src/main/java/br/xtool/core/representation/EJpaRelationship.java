package br.xtool.core.representation;

import java.util.Optional;

/**
 * Representação de um relacionamento java JPA.
 * 
 * @author jcruz
 *
 */
public interface EJpaRelationship {

	boolean isBidirectional();

	boolean isUnidirectional();

	boolean isSingleRelationship();

	boolean isCollectionRelationship();

	boolean isOneToOne();

	boolean isOneToMany();

	boolean isManyToOne();

	boolean isManyToMany();

	EJpaAttribute getSourceAttribute();

	Optional<EJpaAttribute> getTargetAttribute();

	EJpaEntity getSourceEntity();

	EJpaEntity getTargetEntity();
}
