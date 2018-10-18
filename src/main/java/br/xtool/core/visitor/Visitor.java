package br.xtool.core.visitor;

import br.xtool.core.representation.EJavaClass;
import br.xtool.core.representation.EJavaClass.EAuditableJavaClass;
import br.xtool.core.representation.EJavaClass.ECacheableJavaClass;
import br.xtool.core.representation.EJavaClass.EIndexedJavaClass;
import br.xtool.core.representation.EJavaClass.EReadOnlyJavaClass;
import br.xtool.core.representation.EJavaClass.EVersionableJavaClass;
import br.xtool.core.representation.EJavaClass.EViewJavaClass;
import br.xtool.core.representation.EJavaField;
import br.xtool.core.representation.EJavaField.EBigDecimalField;
import br.xtool.core.representation.EJavaField.EBooleanField;
import br.xtool.core.representation.EJavaField.EByteField;
import br.xtool.core.representation.EJavaField.EEnumField;
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
import br.xtool.core.representation.EPlantClass;
import br.xtool.core.representation.EPlantField;
import br.xtool.core.representation.EPlantFieldProperty;
import br.xtool.core.representation.EPlantRelationship;
import br.xtool.core.representation.EPlantRelationship.EAssociation;
import br.xtool.core.representation.EPlantRelationship.EComposition;
import br.xtool.core.representation.EPlantStereotype;

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
	void visit(EJavaClass javaClass, EPlantClass umlClass);

	/**
	 * 
	 * @param umlStereotype
	 */
	void visit(EJavaClass javaClass, EPlantStereotype umlStereotype);

	/**
	 * 
	 * @param umlStereotype
	 */
	void visit(EAuditableJavaClass auditableClass, EPlantStereotype umlStereotype);

	/**
	 * 
	 * @param umlStereotype
	 */
	void visit(ECacheableJavaClass cacheableClass, EPlantStereotype umlStereotype);

	/**
	 * 
	 * @param umlStereotype
	 */
	void visit(EIndexedJavaClass indexedClass, EPlantStereotype umlStereotype);

	/**
	 * 
	 * @param umlStereotype
	 */
	void visit(EViewJavaClass viewClass, EPlantStereotype umlStereotype);

	/**
	 * 
	 * @param umlStereotype
	 */
	void visit(EReadOnlyJavaClass readOnlyClass, EPlantStereotype umlStereotype);

	/**
	 * 
	 * @param umlStereotype
	 */
	void visit(EVersionableJavaClass versionableClass, EPlantStereotype umlStereotype);

	/**
	 * 
	 * @param umlField
	 */
	void visit(EJavaField javaField, EPlantField umlField);

	/**
	 * 
	 * @param stringField
	 * @param umlField
	 */
	void visit(EStringField stringField, EPlantField umlField);

	/**
	 * 
	 * @param booleanField
	 * @param umlField
	 */
	void visit(EBooleanField booleanField, EPlantField umlField);

	/**
	 * 
	 * @param longField
	 * @param umlField
	 */
	void visit(ELongField longField, EPlantField umlField);

	/**
	 * 
	 * @param integerField
	 * @param umlField
	 */
	void visit(EIntegerField integerField, EPlantField umlField);

	/**
	 * 
	 * @param byteField
	 * @param umlField
	 */
	void visit(EByteField byteField, EPlantField umlField);

	/**
	 * 
	 * @param bigDecimalField
	 * @param umlField
	 */
	void visit(EBigDecimalField bigDecimalField, EPlantField umlField);

	/**
	 * 
	 * @param localDateField
	 * @param umlField
	 */
	void visit(ELocalDateField localDateField, EPlantField umlField);

	/**
	 * 
	 * @param localDateTimeField
	 * @param umlField
	 */
	void visit(ELocalDateTimeField localDateTimeField, EPlantField umlField);
	
	/**
	 * 
	 * @param enumField
	 * @param umlField
	 */
	void visit(EEnumField enumField, EPlantField umlField);

	/**
	 * 
	 * @param notNullField
	 * @param property
	 */
	void visit(ENotNullField notNullField, EPlantFieldProperty property);

	/**
	 * 
	 * @param transientField
	 * @param property
	 */
	void visit(ETransientField transientField, EPlantFieldProperty property);

	/**
	 * 
	 * @param uniqueField
	 * @param property
	 */
	void visit(EUniqueField uniqueField, EPlantFieldProperty property);

	/**
	 * 
	 * @param umlFieldProperty
	 */
	void visit(EJavaField javaField, EPlantFieldProperty umlFieldProperty);

	/**
	 * 
	 * @param umlRelationship
	 */
	void visit(EJavaField javaField, EPlantRelationship umlRelationship);

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
