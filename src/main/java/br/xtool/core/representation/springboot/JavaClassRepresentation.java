package br.xtool.core.representation.springboot;

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
public interface JavaClassRepresentation extends Comparable<JavaClassRepresentation>, JavaTypeRepresentation<JavaClassSource> {

	String getInstanceName();

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
	Collection<JavaFieldRepresentation> getJavaFields();

	/**
	 * Retorna os métodos da classe.
	 * 
	 * @return
	 */
	SortedSet<JavaMethodRepresentation<JavaClassSource>> getJavaMethods();

	/**
	 * Retorna as annotations da classe.
	 * 
	 * @return
	 */
	SortedSet<JavaAnnotationRepresentation<JavaClassSource>> getJavaAnnotations();

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
	JavaFieldRepresentation addField(String name);

	/**
	 * 
	 * @param name
	 * @return
	 */
	JavaMethodRepresentation<JavaClassSource> addMethod(String name);

	/**
	 * Adiciona uma annotation
	 * 
	 * @param type
	 * @return
	 */
	JavaAnnotationRepresentation<JavaClassSource> addAnnotation(Class<? extends Annotation> type);

	/**
	 * Retorna o objeto Roaster da classe.
	 * 
	 * @return
	 */
	JavaClassSource getRoasterJavaClass();

	//	EJavaAnnotation<JavaClassSource> addTableAnnotation();

	JavaAnnotationRepresentation<JavaClassSource> addToStringAnnotation(String... attributes);

	JavaAnnotationRepresentation<JavaClassSource> addEqualsAndHashCodeAnnotation(String... attibutes);

	@Deprecated
	interface EAuditableJavaClass extends JavaClassRepresentation {}

	@Deprecated
	interface ECacheableJavaClass extends JavaClassRepresentation {}

	@Deprecated
	interface EIndexedJavaClass extends JavaClassRepresentation {}

	@Deprecated
	interface EViewJavaClass extends JavaClassRepresentation {}

	@Deprecated
	interface EReadOnlyJavaClass extends JavaClassRepresentation {}

	@Deprecated
	interface EVersionableJavaClass extends JavaClassRepresentation {}

}
