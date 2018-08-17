package br.xtool.core.representation;

import java.util.Collection;

import org.jboss.forge.roaster.model.source.JavaEnumSource;

/**
 * Representação de um Enum Java.
 * 
 * @author jcruz
 *
 */
public interface EJavaEnum extends EJavaType<JavaEnumSource> {

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
