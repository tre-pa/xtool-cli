package br.xtool.core.representation;

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
	Set<EUmlField> getFields();

	/**
	 * Retorna os stereotipos da classe UML.
	 * 
	 * @return
	 */
	Set<EUmlStereotype> getStereotypes();
}
