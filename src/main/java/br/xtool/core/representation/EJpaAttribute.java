package br.xtool.core.representation;

import java.util.Optional;

/**
 * Representação de um atributo java JPA.
 * 
 * @author jcruz
 *
 */
public interface EJpaAttribute extends EJavaField {

	boolean isAssociation();

	@Override
	boolean isTransient();

	boolean isLob();

	Optional<EJpaRelationship> getRelationship();

}
