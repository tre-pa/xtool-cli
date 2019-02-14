package br.xtool.core.representation.springboot;

import java.util.Optional;

/**
 * Representação de um atributo de uma entidade JPA.
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
	boolean isJpaTransientField();

	/**
	 * Retorna se o atributo é do tipo Lob.
	 * 
	 * @return
	 */
	boolean isLobField();

	/**
	 * Retorna o relacionamento JPA.
	 * 
	 * @return
	 */
	Optional<JpaRelationshipRepresentation> getJpaRelationship();

}
