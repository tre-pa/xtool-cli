package br.xtool.core.representation.springboot;

import java.util.Optional;

/**
 * Representação de um atributo de uma entidade JPA.
 * 
 * @author jcruz
 *
 */
public interface EntityAttributeRepresentation extends JavaFieldRepresentation {

	EntityRepresentation getEntity();

	/**
	 * Retorna se o atributo é Id da entidade.
	 * 
	 * @return
	 */
	boolean isId();

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
	 * Verifica se o atributo é requerido.
	 * 
	 * @return
	 */
	boolean isRequired();

	/**
	 * Retorna o tamanho máximo da String.
	 * 
	 * @return
	 */
	Integer getColumnLength();

	/**
	 * Retorna a mascara aplicável ao atributo se houver.
	 * 
	 * @return
	 */
	Optional<String> getMask();

	/**
	 * Retorna o relacionamento JPA.
	 * 
	 * @return
	 */
	Optional<JpaRelationshipRepresentation> getJpaRelationship();

}
