package br.xtool.core.representation.springboot;

import java.util.Optional;

/**
 * Representação de um atributo java JPA.
 * 
 * @author jcruz
 *
 */
public interface EntityAttributeRepresentation extends JavaFieldRepresentation {

	/**
	 * Retorna se o atributo é do tipo JPA transient.
	 * 
	 * @return
	 */
	boolean isJpaTransient();

	/**
	 * Retorna se o atributo é do tipo Lob.
	 * 
	 * @return
	 */
	boolean isLob();

	// Optional<EJpaEntity> getGenericType();

	/**
	 * Retorna o relacionamento JPA.
	 * 
	 * @return
	 */
	Optional<JpaRelationshipRepresentation> getJpaRelationship();

}
