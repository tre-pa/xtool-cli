package br.xtool.core.representation;

import java.lang.annotation.Annotation;
import java.nio.file.Path;
import java.util.Collection;
import java.util.SortedSet;

import org.jboss.forge.roaster.model.source.JavaClassSource;

/**
 * Representação de uma classe java.
 * 
 * @author jcruz
 *
 */
public interface EJavaClass extends Comparable<EJavaClass>, EJavaType<JavaClassSource> {

	/**
	 * Retorna o nome da classe.
	 * 
	 * @return
	 */
	@Override
	String getName();

	/**
	 * Retorna o nome qualificado da classe.
	 * 
	 * @return
	 */
	@Override
	String getQualifiedName();

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
	Collection<EJavaField> getJavaFields();

	/**
	 * Retorna os métodos da classe.
	 * 
	 * @return
	 */
	SortedSet<EJavaMethod<JavaClassSource>> getJavaMethods();

	/**
	 * Retorna as annotations da classe.
	 * 
	 * @return
	 */
	SortedSet<EJavaAnnotation<JavaClassSource>> getJavaAnnotations();

	/**
	 * Verifica se a classe possui a annotation.
	 * 
	 * @param name
	 * @return
	 */
	@Override
	boolean hasAnnotation(String name);

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
	EJavaMethod<JavaClassSource> addMethod(String name);

	/**
	 * Adiciona uma annotation
	 * 
	 * @param type
	 * @return
	 */
	EJavaAnnotation<JavaClassSource> addAnnotation(Class<? extends Annotation> type);

	/**
	 * Retorna o objeto Roaster da classe.
	 * 
	 * @return
	 */
	JavaClassSource getRoasterJavaClass();

	EJavaAnnotation<JavaClassSource> addTableAnnotation();

	EJavaAnnotation<JavaClassSource> addToStringAnnotation(String... attributes);

	EJavaAnnotation<JavaClassSource> addEqualsAndHashCodeAnnotation(String... attibutes);

	interface EAuditableJavaClass extends EJavaClass {}

	interface ECacheableJavaClass extends EJavaClass {}

	interface EIndexedJavaClass extends EJavaClass {}

	interface EViewJavaClass extends EJavaClass {}

	interface EReadOnlyJavaClass extends EJavaClass {}

	interface EVersionableJavaClass extends EJavaClass {}

}
