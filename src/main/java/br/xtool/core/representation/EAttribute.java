package br.xtool.core.representation;

import java.util.Optional;

import br.xtool.core.representation.impl.EAssociationImpl;

public interface EAttribute extends EField {

	boolean isAssociation();

	boolean isJpaTransient();

	boolean isJpaLob();

	Optional<EAssociationImpl> getAssociation();

}
