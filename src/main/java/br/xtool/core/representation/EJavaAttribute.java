package br.xtool.core.representation;

import java.util.Optional;

/**
 * Representaçã de um atributo java JPA.
 * 
 * @author jcruz
 *
 */
public interface EJavaAttribute extends EJavaField {

	boolean isAssociation();

	boolean isJpaTransient();

	boolean isJpaLob();

	Optional<EJavaRelationship> getRelationship();

}
