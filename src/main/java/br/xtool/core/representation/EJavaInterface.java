package br.xtool.core.representation;

import java.util.Collection;

import org.jboss.forge.roaster.model.JavaType;
import org.jboss.forge.roaster.model.source.JavaInterfaceSource;

/**
 * Representação de uma interface Java.
 * 
 * @author jcruz
 *
 */
public interface EJavaInterface extends JavaType<JavaInterfaceSource>, Comparable<EJavaInterface> {

	/**
	 * 
	 * @return
	 */
	String getInstanceName();

	/**
	 * 
	 * @return
	 */
	EBootProject getProject();

	/**
	 * Retorna a interface roaster.
	 * 
	 * @return
	 */
	JavaInterfaceSource getRoasterInterface();

	/**
	 * Retorna o package da classe.
	 * 
	 * @return
	 */
	EJavaPackage getJavaPackage();

	/**
	 * Retorna os métodos da interface.
	 * 
	 * @return
	 */
	Collection<EJavaMethod<JavaInterfaceSource>> getJavaMethods();

}
