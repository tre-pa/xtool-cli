package br.xtool.core.representation;

import java.util.Collection;
import java.util.Set;

import org.jboss.forge.roaster.model.JavaType;
import org.jboss.forge.roaster.model.source.JavaClassSource;

/**
 * Representação de uma classe no diagrama de classe UML.
 * 
 * @author jcruz
 *
 */
public interface EUmlClass extends JavaType<JavaClassSource> {

	/**
	 * Retorna o nome da classe UML.
	 * 
	 * @return
	 */
	@Override
	String getName();

	/**
	 * 
	 * @return
	 */
	String getInstanceName();

	/**
	 * Retorna o nome qualificado.
	 * 
	 * @return
	 */
	@Override
	String getQualifiedName();;

	/**
	 * Retorna o pacote UML da classe.
	 * 
	 * @return
	 */
	EUmlPackage getUmlPackage();

	/**
	 * Retorna os atributos da classe UML.
	 * 
	 * @return
	 */
	Collection<EUmlField> getFields();

	/**
	 * Retorna os stereotipos da classe UML.
	 * 
	 * @return
	 */
	Set<EUmlStereotype> getStereotypes();

	/**
	 * 
	 * @return
	 */
	Set<EUmlRelationship> getRelationships();

}
