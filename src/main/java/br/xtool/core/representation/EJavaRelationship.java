package br.xtool.core.representation;

import java.util.Optional;

/**
 * Representação de um relacionamento java JPA.
 * 
 * @author jcruz
 *
 */
public interface EJavaRelationship {

	boolean isBidirectional();

	boolean isUnidirectional();

	boolean isSingleRelationship();

	boolean isCollectionRelationship();

	boolean isOneToOne();

	boolean isOneToMany();

	boolean isManyToOne();

	boolean isManyToMany();

	EJavaAttribute getSourceAttribute();

	Optional<EJavaAttribute> getTargetAttribute();

	EJavaEntity getSourceEntity();

	Optional<EJavaEntity> getTargetEntity();
}
