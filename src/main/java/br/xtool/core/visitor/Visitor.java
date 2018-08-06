package br.xtool.core.visitor;

import br.xtool.core.representation.EJavaClass;
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
	void visit(EJavaClass javaClass, EUmlClass umlClass);

	/**
	 * 
	 * @param umlStereotype
	 */
	void visit(EJavaClass javaClass, EUmlStereotype umlStereotype);

	/**
	 * 
	 * @param umlField
	 */
	void visit(EJavaField javaField, EUmlField umlField);

	/**
	 * 
	 * @param umlFieldProperty
	 */
	void visit(EJavaField javaField, EUmlFieldProperty umlFieldProperty);

	/**
	 * 
	 * @param umlRelationship
	 */
	void visit(EJavaField javaField, EUmlRelationship umlRelationship);

}
