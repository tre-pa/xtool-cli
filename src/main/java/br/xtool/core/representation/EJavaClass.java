package br.xtool.core.representation;

import java.lang.annotation.Annotation;
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

	EBootProject getProject();

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

	/**
	 * Retorna um atributo da classe. Caso não encontre cria um novo atributo.
	 * 
	 * @param name
	 * @return
	 */
	EJavaField addField(String name);

	/**
	 * 
	 * @param name
	 * @return
	 */
	EJavaMethod addMethod(String name);

	/**
	 * Retorna as annotations da classe.
	 * 
	 * @return
	 */
	SortedSet<EJavaAnnotation> getAnnotations();

	EJavaAnnotation addAnnotation(Class<? extends Annotation> type);

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

	EJavaAnnotation addTableAnnotation();

	EJavaAnnotation addToStringAnnotation(String... attributes);

	EJavaAnnotation addEqualsAndHashCodeAnnotation(String... attibutes);

}
