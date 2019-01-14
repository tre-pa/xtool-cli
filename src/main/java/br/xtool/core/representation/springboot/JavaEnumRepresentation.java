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
	 * 
	 * @return
	 */
	Collection<String> getConstants();

	/**
	 * 
	 * @return
	 */
	JavaEnumSource getRoasterEnum();
}
