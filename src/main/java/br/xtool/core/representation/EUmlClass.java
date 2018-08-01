package br.xtool.core.representation;

import java.util.Collection;
import java.util.Set;

import org.jboss.forge.roaster.model.source.JavaClassSource;

import br.xtool.core.visitor.Visitable;

/**
 * Representação de uma classe no diagrama de classe UML.
 * 
 * @author jcruz
 *
 */
public interface EUmlClass extends Visitable {

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

	/**
	 * Converte de UmlClass para JavaClassSource.
	 * 
	 * @param bootProject
	 * @return
	 */
	JavaClassSource convertToJavaClassSource(EBootProject bootProject);
}
