package br.xtool.core.visitor;

import br.xtool.core.representation.EJavaClass;
import br.xtool.core.representation.EJavaField;
import br.xtool.core.representation.EJavaField.EBigDecimalField;
import br.xtool.core.representation.EJavaField.EBooleanField;
import br.xtool.core.representation.EJavaField.EByteField;
import br.xtool.core.representation.EJavaField.EIntegerField;
import br.xtool.core.representation.EJavaField.ELocalDateField;
import br.xtool.core.representation.EJavaField.ELocalDateTimeField;
import br.xtool.core.representation.EJavaField.ELongField;
import br.xtool.core.representation.EJavaField.EStringField;
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
	void visit(EJavaClass javaClass);

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
	 * @param stringField
	 * @param umlField
	 */
	void visit(EStringField stringField, EUmlField umlField);

	void visit(EBooleanField booleanField, EUmlField umlField);

	void visit(ELongField longField, EUmlField umlField);

	void visit(EIntegerField integerField, EUmlField umlField);

	void visit(EByteField byteField, EUmlField umlField);

	void visit(EBigDecimalField bigDecimalField, EUmlField umlField);

	void visit(ELocalDateField localDateField, EUmlField umlField);

	void visit(ELocalDateTimeField localDateTimeField, EUmlField umlField);

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
