package br.xtool.core.representation;

import java.util.Optional;

public interface EAttribute extends EField {

	boolean isAssociation();

	boolean isJpaTransient();

	boolean isJpaLob();

	Optional<ERelationship> getRelationship();

}
