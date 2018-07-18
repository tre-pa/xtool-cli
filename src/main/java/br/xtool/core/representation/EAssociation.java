package br.xtool.core.representation;

import java.util.Optional;

public interface EAssociation {

	boolean isBidirectional();

	boolean isUnidirectional();

	boolean isSingleAssociation();

	boolean isCollectionAssociation();

	boolean isOneToOneAnnotation();

	boolean isOneToManyAnnotation();

	boolean isManyToOneAnnotation();

	boolean isManyToManyAnnotation();

	EAttribute getAttributeSource();

	Optional<EAttribute> getAttributeTarget();

	EEntity getEntitySource();

	Optional<EEntity> getEntityTarget();
}
