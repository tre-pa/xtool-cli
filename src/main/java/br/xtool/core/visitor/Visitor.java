package br.xtool.core.visitor;

import br.xtool.core.representation.EJavaClass;
import br.xtool.core.representation.EJavaClass.EAuditableJavaClass;
import br.xtool.core.representation.EJavaClass.ECacheableJavaClass;
import br.xtool.core.representation.EJavaClass.EIndexedJavaClass;
import br.xtool.core.representation.EJavaClass.EViewJavaClass;
import br.xtool.core.representation.EJavaField;
import br.xtool.core.representation.EJavaField.EBigDecimalField;
import br.xtool.core.representation.EJavaField.EBooleanField;
import br.xtool.core.representation.EJavaField.EByteField;
import br.xtool.core.representation.EJavaField.EIntegerField;
import br.xtool.core.representation.EJavaField.ELocalDateField;
import br.xtool.core.representation.EJavaField.ELocalDateTimeField;
import br.xtool.core.representation.EJavaField.ELongField;
import br.xtool.core.representation.EJavaField.EManyToManyField;
import br.xtool.core.representation.EJavaField.EManyToOneField;
import br.xtool.core.representation.EJavaField.ENotNullField;
import br.xtool.core.representation.EJavaField.EOneToManyField;
import br.xtool.core.representation.EJavaField.EOneToOneField;
import br.xtool.core.representation.EJavaField.EStringField;
import br.xtool.core.representation.EJavaField.ETransientField;
import br.xtool.core.representation.EJavaField.EUniqueField;
import br.xtool.core.representation.EUmlField;
import br.xtool.core.representation.EUmlFieldProperty;
import br.xtool.core.representation.EUmlRelationship;
import br.xtool.core.representation.EUmlRelationship.EAssociation;
import br.xtool.core.representation.EUmlRelationship.EComposition;
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
	 * @param umlStereotype
	 */
	void visit(EAuditableJavaClass auditableClass, EUmlStereotype umlStereotype);

	/**
	 * 
	 * @param umlStereotype
	 */
	void visit(ECacheableJavaClass cacheableClass, EUmlStereotype umlStereotype);

	/**
	 * 
	 * @param umlStereotype
	 */
	void visit(EIndexedJavaClass indexedClass, EUmlStereotype umlStereotype);

	/**
	 * 
	 * @param umlStereotype
	 */
	void visit(EViewJavaClass viewClass, EUmlStereotype umlStereotype);

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

	/**
	 * 
	 * @param booleanField
	 * @param umlField
	 */
	void visit(EBooleanField booleanField, EUmlField umlField);

	/**
	 * 
	 * @param longField
	 * @param umlField
	 */
	void visit(ELongField longField, EUmlField umlField);

	/**
	 * 
	 * @param integerField
	 * @param umlField
	 */
	void visit(EIntegerField integerField, EUmlField umlField);

	/**
	 * 
	 * @param byteField
	 * @param umlField
	 */
	void visit(EByteField byteField, EUmlField umlField);

	/**
	 * 
	 * @param bigDecimalField
	 * @param umlField
	 */
	void visit(EBigDecimalField bigDecimalField, EUmlField umlField);

	/**
	 * 
	 * @param localDateField
	 * @param umlField
	 */
	void visit(ELocalDateField localDateField, EUmlField umlField);

	/**
	 * 
	 * @param localDateTimeField
	 * @param umlField
	 */
	void visit(ELocalDateTimeField localDateTimeField, EUmlField umlField);

	/**
	 * 
	 * @param notNullField
	 * @param property
	 */
	void visit(ENotNullField notNullField, EUmlFieldProperty property);

	/**
	 * 
	 * @param transientField
	 * @param property
	 */
	void visit(ETransientField transientField, EUmlFieldProperty property);

	/**
	 * 
	 * @param uniqueField
	 * @param property
	 */
	void visit(EUniqueField uniqueField, EUmlFieldProperty property);

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

	/**
	 * 
	 * @param oneToOneField
	 * @param association
	 */
	void visit(EOneToOneField oneToOneField, EAssociation association);

	/**
	 * 
	 * @param oneToManyField
	 * @param association
	 */
	void visit(EOneToManyField oneToManyField, EAssociation association);

	/**
	 * 
	 * @param manyToOneField
	 * @param association
	 */
	void visit(EManyToOneField manyToOneField, EAssociation association);

	/**
	 * 
	 * @param manyToManyField
	 * @param association
	 */
	void visit(EManyToManyField manyToManyField, EAssociation association);

	/**
	 * 
	 * @param oneToOneField
	 * @param composition
	 */
	void visit(EOneToOneField oneToOneField, EComposition composition);

	/**
	 * 
	 * @param oneToManyField
	 * @param composition
	 */
	void visit(EOneToManyField oneToManyField, EComposition composition);

	/**
	 * 
	 * @param manyToOneField
	 * @param composition
	 */
	void visit(EManyToOneField manyToOneField, EComposition composition);

}
