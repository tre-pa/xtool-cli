package br.xtool.core.representation;

import java.util.Collection;
import java.util.Set;

/**
 * Representação de uma classe no diagrama de classe UML.
 * 
 * @author jcruz
 *
 */
public interface EUmlClass {

	/**
	 * Retorna o nome da classe UML.
	 * 
	 * @return
	 */
	String getName();

	/**
	 * Retorna o nome qualificado.
	 * 
	 * @return
	 */
	String getQualifiedName();;

	/**
	 * Retorna o pacote UML da classe.
	 * 
	 * @return
	 */
	EUmlPackage getPackage();

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

	//	/**
	//	 * Converte de UmlClass para JavaClassSource.
	//	 * 
	//	 * @param bootProject
	//	 * @return
	//	 */
	//	@Deprecated
	//	EJavaClass convertToJavaClass(EBootProject bootProject);
}
