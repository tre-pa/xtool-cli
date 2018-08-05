package br.xtool.core.visitor;

import br.xtool.core.representation.EJavaField;
import br.xtool.core.representation.EUmlClass;
import br.xtool.core.representation.EUmlField;
import br.xtool.core.representation.EUmlFieldProperty;
import br.xtool.core.representation.EUmlRelationship;
import br.xtool.core.representation.EUmlStereotype;

/**
 * 
 * @author jcruz
 *
 */
public interface Visitor {

	/**
	 * 
	 * @param umlClass
	 */
	void visit(EUmlClass umlClass);

	/**
	 * 
	 * @param umlStereotype
	 */
	void visit(EUmlStereotype umlStereotype);

	/**
	 * 
	 * @param umlField
	 */
	void visit(EJavaField javaField, EUmlField umlField);

	/**
	 * 
	 * @param umlFieldProperty
	 */
	void visit(EUmlFieldProperty umlFieldProperty);

	/**
	 * 
	 * @param umlRelationship
	 */
	void visit(EUmlRelationship umlRelationship);

}
