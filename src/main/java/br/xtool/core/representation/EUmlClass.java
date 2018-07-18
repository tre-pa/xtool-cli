package br.xtool.core.representation;

import java.util.Set;

/**
 * Representação de uma classe no diagrama de classe UML.
 * 
 * @author jcruz
 *
 */
public interface EUmlClass {

	String getName();

	EUmlPackage getPackage();

	Set<EUmlField> getFields();

}
