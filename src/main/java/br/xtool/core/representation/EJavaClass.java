package br.xtool.core.representation;

import java.nio.file.Path;
import java.util.SortedSet;

import org.jboss.forge.roaster.model.source.JavaClassSource;

/**
 * Representação de uma classe java.
 * 
 * @author jcruz
 *
 */
public interface EJavaClass extends Comparable<EJavaClass> {

	/**
	 * Retorna o nome da classe.
	 * 
	 * @return
	 */
	String getName();

	/**
	 * Retorna o nome qualificado da classe.
	 * 
	 * @return
	 */
	String getQualifiedName();

	/**
	 * Retorna o package da classe.
	 * 
	 * @return
	 */
	EJavaPackage getPackage();

	/**
	 * Verifica se a classe possui a annotation.
	 * 
	 * @param name
	 * @return
	 */
	boolean hasAnnotation(String name);

	/**
	 * 
	 * @return
	 */
	Path getPath();

	/**
	 * Retorna os atributos da classe.
	 * 
	 * @return
	 */
	SortedSet<EJavaField> getFields();

	EJavaField getField(String name);

	/**
	 * Retorna as annotations da classe.
	 * 
	 * @return
	 */
	SortedSet<EJavaAnnotation> getAnnotations();

	/**
	 * Retorna os métodos da classe.
	 * 
	 * @return
	 */
	SortedSet<EJavaMethod> getMethods();

	/**
	 * Retorna o objeto Roaster da classe.
	 * 
	 * @return
	 */
	JavaClassSource getRoasterJavaClass();

	//	void save();
}
