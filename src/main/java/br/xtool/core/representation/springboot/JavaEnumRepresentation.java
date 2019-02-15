package br.xtool.core.representation.springboot;

import java.util.Collection;

import org.jboss.forge.roaster.model.source.JavaEnumSource;

/**
 * Representação de um Enum Java.
 * 
 * @author jcruz
 *
 */
public interface JavaEnumRepresentation extends JavaTypeRepresentation<JavaEnumSource> {

	/**
	 * Retorna a lista de constantes do enum
	 * 
	 * @return
	 */
	Collection<String> getConstants();

	/**
	 * Retorna o objeto Roaster
	 * 
	 * @return
	 */
	JavaEnumSource getRoasterEnum();
}
