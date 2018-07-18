package br.xtool.core.representation;

import java.util.Optional;

public interface ERelationship {

	boolean isBidirectional();

	boolean isUnidirectional();

	boolean isSingleRelationship();

	boolean isCollectionRelationship();

	boolean isOneToOne();

	boolean isOneToMany();

	boolean isManyToOne();

	boolean isManyToMany();

	EAttribute getSourceAttribute();

	Optional<EAttribute> getTargetAttribute();

	EEntity getSourceEntity();

	Optional<EEntity> getTargetEntity();
}
