package br.xtool.core;

import br.xtool.core.representation.plantuml.PlantClassFieldPropertyRepresentation;
import br.xtool.core.representation.plantuml.PlantClassFieldRepresentation;
import br.xtool.core.representation.plantuml.PlantClassRepresentation;
import br.xtool.core.representation.plantuml.PlantRelationshipRepresentation;
import br.xtool.core.representation.plantuml.PlantStereotypeRepresentation;
import br.xtool.core.representation.plantuml.PlantRelationshipRepresentation.EAssociation;
import br.xtool.core.representation.plantuml.PlantRelationshipRepresentation.EComposition;
import br.xtool.core.representation.springboot.JavaClassRepresentation;
import br.xtool.core.representation.springboot.JavaFieldRepresentation;
import br.xtool.core.representation.springboot.JavaClassRepresentation.EAuditableJavaClass;
import br.xtool.core.representation.springboot.JavaClassRepresentation.ECacheableJavaClass;
import br.xtool.core.representation.springboot.JavaClassRepresentation.EIndexedJavaClass;
import br.xtool.core.representation.springboot.JavaClassRepresentation.EReadOnlyJavaClass;
import br.xtool.core.representation.springboot.JavaClassRepresentation.EVersionableJavaClass;
import br.xtool.core.representation.springboot.JavaClassRepresentation.EViewJavaClass;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.EBigDecimalField;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.EBooleanField;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.EByteField;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.EEnumField;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.EIntegerField;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.ELocalDateField;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.ELocalDateTimeField;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.ELongField;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.EManyToManyField;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.EManyToOneField;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.ENotNullField;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.EOneToManyField;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.EOneToOneField;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.EStringField;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.ETransientField;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.EUniqueField;

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
	void visit(JavaClassRepresentation javaClass, PlantClassRepresentation umlClass);

	/**
	 * 
	 * @param umlStereotype
	 */
	void visit(JavaClassRepresentation javaClass, PlantStereotypeRepresentation umlStereotype);

	/**
	 * 
	 * @param umlStereotype
	 */
	void visit(EAuditableJavaClass auditableClass, PlantStereotypeRepresentation umlStereotype);

	/**
	 * 
	 * @param umlStereotype
	 */
	void visit(ECacheableJavaClass cacheableClass, PlantStereotypeRepresentation umlStereotype);

	/**
	 * 
	 * @param umlStereotype
	 */
	void visit(EIndexedJavaClass indexedClass, PlantStereotypeRepresentation umlStereotype);

	/**
	 * 
	 * @param umlStereotype
	 */
	void visit(EViewJavaClass viewClass, PlantStereotypeRepresentation umlStereotype);

	/**
	 * 
	 * @param umlStereotype
	 */
	void visit(EReadOnlyJavaClass readOnlyClass, PlantStereotypeRepresentation umlStereotype);

	/**
	 * 
	 * @param umlStereotype
	 */
	void visit(EVersionableJavaClass versionableClass, PlantStereotypeRepresentation umlStereotype);

	/**
	 * 
	 * @param umlField
	 */
	void visit(JavaFieldRepresentation javaField, PlantClassFieldRepresentation umlField);

	/**
	 * 
	 * @param stringField
	 * @param umlField
	 */
	void visit(EStringField stringField, PlantClassFieldRepresentation umlField);

	/**
	 * 
	 * @param booleanField
	 * @param umlField
	 */
	void visit(EBooleanField booleanField, PlantClassFieldRepresentation umlField);

	/**
	 * 
	 * @param longField
	 * @param umlField
	 */
	void visit(ELongField longField, PlantClassFieldRepresentation umlField);

	/**
	 * 
	 * @param integerField
	 * @param umlField
	 */
	void visit(EIntegerField integerField, PlantClassFieldRepresentation umlField);

	/**
	 * 
	 * @param byteField
	 * @param umlField
	 */
	void visit(EByteField byteField, PlantClassFieldRepresentation umlField);

	/**
	 * 
	 * @param bigDecimalField
	 * @param umlField
	 */
	void visit(EBigDecimalField bigDecimalField, PlantClassFieldRepresentation umlField);

	/**
	 * 
	 * @param localDateField
	 * @param umlField
	 */
	void visit(ELocalDateField localDateField, PlantClassFieldRepresentation umlField);

	/**
	 * 
	 * @param localDateTimeField
	 * @param umlField
	 */
	void visit(ELocalDateTimeField localDateTimeField, PlantClassFieldRepresentation umlField);
	
	/**
	 * 
	 * @param enumField
	 * @param umlField
	 */
	void visit(EEnumField enumField, PlantClassFieldRepresentation umlField);

	/**
	 * 
	 * @param notNullField
	 * @param property
	 */
	void visit(ENotNullField notNullField, PlantClassFieldPropertyRepresentation property);

	/**
	 * 
	 * @param transientField
	 * @param property
	 */
	void visit(ETransientField transientField, PlantClassFieldPropertyRepresentation property);

	/**
	 * 
	 * @param uniqueField
	 * @param property
	 */
	void visit(EUniqueField uniqueField, PlantClassFieldPropertyRepresentation property);

	/**
	 * 
	 * @param umlFieldProperty
	 */
	void visit(JavaFieldRepresentation javaField, PlantClassFieldPropertyRepresentation umlFieldProperty);

	/**
	 * 
	 * @param umlRelationship
	 */
	void visit(JavaFieldRepresentation javaField, PlantRelationshipRepresentation umlRelationship);

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
