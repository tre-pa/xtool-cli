package br.xtool.representation.springboot;

import java.util.Optional;

import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.JavaDocSource;

/**
 * Representação de um atributo de uma entidade JPA.
 * 
 * @author jcruz
 *
 */
public interface JpaEntityAttributeRepresentation extends JavaFieldRepresentation {

	JpaEntityRepresentation getEntity();

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
	 * Adciona uma doclet ao atributo
	 * 
	 */
	JavaDocSource<FieldSource<JavaClassSource>> addTagValue(String tagName, String value);

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
