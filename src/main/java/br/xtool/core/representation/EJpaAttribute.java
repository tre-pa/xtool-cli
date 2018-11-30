package br.xtool.core.representation;

import java.util.Optional;

/**
 * Representação de um atributo java JPA.
 * 
 * @author jcruz
 *
 */
public interface EJpaAttribute extends EJavaField {

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
	Optional<EJpaRelationship> getJpaRelationship();

}
