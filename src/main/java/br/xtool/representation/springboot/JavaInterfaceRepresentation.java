package br.xtool.representation.springboot;

import java.util.Collection;

import org.jboss.forge.roaster.model.source.JavaInterfaceSource;

/**
 * Representação de uma interface Java.
 * 
 * @author jcruz
 *
 */
public interface JavaInterfaceRepresentation extends JavaTypeRepresentation<JavaInterfaceSource>, Comparable<JavaInterfaceRepresentation> {

	/**
	 * 
	 * @return
	 */
	String getInstanceName();

	/**
	 * Retorna a interface roaster.
	 * 
	 * @return
	 */
	JavaInterfaceSource getRoasterInterface();

	/**
	 * Retorna os métodos da interface.
	 * 
	 * @return
	 */
	Collection<JavaMethodRepresentation<JavaInterfaceSource>> getJavaMethods();

}
