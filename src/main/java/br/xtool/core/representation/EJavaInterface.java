package br.xtool.core.representation;

import java.util.Collection;

import org.jboss.forge.roaster.model.source.JavaInterfaceSource;

/**
 * Representação de uma interface Java.
 * 
 * @author jcruz
 *
 */
public interface EJavaInterface extends EJavaType<JavaInterfaceSource>, Comparable<EJavaInterface> {

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
	Collection<EJavaMethod<JavaInterfaceSource>> getJavaMethods();

}
