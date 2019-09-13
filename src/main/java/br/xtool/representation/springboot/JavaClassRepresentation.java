package br.xtool.representation.springboot;

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

	/**
	 * Retorna o nome de instância da classe.
	 *
	 * @return
	 */
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
	 * Retorna o nome no formato de arquivo TypeScript.
	 *
	 * @return
	 */
	String getTsFileName();

	/**
	 * Retorna o nome no formato de API Rest.
	 *
	 * @return
	 */
	String getApiPath();

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

	/**
	 * Verifica se a classe é imutável (Views, ou entidades sem edição)
	 *
	 * @return
	 */
	boolean isImmutable();

}
