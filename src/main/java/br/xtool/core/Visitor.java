package br.xtool.core;

import br.xtool.core.representation.plantuml.PlantClassFieldPropertyRepresentation;
import br.xtool.core.representation.plantuml.PlantClassFieldRepresentation;
import br.xtool.core.representation.plantuml.PlantClassRepresentation;
import br.xtool.core.representation.plantuml.PlantRelationshipRepresentation;
import br.xtool.core.representation.plantuml.PlantRelationshipRepresentation.PlantRelationshipAssociation;
import br.xtool.core.representation.plantuml.PlantRelationshipRepresentation.PlantRelationshipComposition;
import br.xtool.core.representation.springboot.JavaClassRepresentation;
import br.xtool.core.representation.springboot.JavaFieldRepresentation;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.JavaFieldBigDecimalType;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.JavaFieldBooleanType;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.JavaFieldByteType;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.JavaFieldEnumType;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.JavaFieldIntegerType;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.JavaFieldLocalDateTimeType;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.JavaFieldLocalDateType;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.JavaFieldLongType;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.JavaFieldManyToManyType;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.JavaFieldManyToOneType;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.JavaFieldNotNullType;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.JavaFieldOneToManyType;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.JavaFieldOneToOneType;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.JavaFieldStringType;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.JavaFieldTransientType;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.JavaFieldUniqueType;

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

//	/**
//	 * 
//	 * @param umlStereotype
//	 */
//	void visit(JavaClassRepresentation javaClass, PlantStereotypeRepresentation umlStereotype);
//
//	/**
//	 * 
//	 * @param umlStereotype
//	 */
//	void visit(EAuditableJavaClass auditableClass, PlantStereotypeRepresentation umlStereotype);
//
//	/**
//	 * 
//	 * @param umlStereotype
//	 */
//	void visit(ECacheableJavaClass cacheableClass, PlantStereotypeRepresentation umlStereotype);
//
//	/**
//	 * 
//	 * @param umlStereotype
//	 */
//	void visit(EIndexedJavaClass indexedClass, PlantStereotypeRepresentation umlStereotype);
//
//	/**
//	 * 
//	 * @param umlStereotype
//	 */
//	void visit(EViewJavaClass viewClass, PlantStereotypeRepresentation umlStereotype);
//
//	/**
//	 * 
//	 * @param umlStereotype
//	 */
//	void visit(EReadOnlyJavaClass readOnlyClass, PlantStereotypeRepresentation umlStereotype);
//
//	/**
//	 * 
//	 * @param umlStereotype
//	 */
//	void visit(EVersionableJavaClass versionableClass, PlantStereotypeRepresentation umlStereotype);

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
	void visit(JavaFieldStringType stringField, PlantClassFieldRepresentation umlField);

	/**
	 * 
	 * @param booleanField
	 * @param umlField
	 */
	void visit(JavaFieldBooleanType booleanField, PlantClassFieldRepresentation umlField);

	/**
	 * 
	 * @param longField
	 * @param umlField
	 */
	void visit(JavaFieldLongType longField, PlantClassFieldRepresentation umlField);

	/**
	 * 
	 * @param integerField
	 * @param umlField
	 */
	void visit(JavaFieldIntegerType integerField, PlantClassFieldRepresentation umlField);

	/**
	 * 
	 * @param byteField
	 * @param umlField
	 */
	void visit(JavaFieldByteType byteField, PlantClassFieldRepresentation umlField);

	/**
	 * 
	 * @param bigDecimalField
	 * @param umlField
	 */
	void visit(JavaFieldBigDecimalType bigDecimalField, PlantClassFieldRepresentation umlField);

	/**
	 * 
	 * @param localDateField
	 * @param umlField
	 */
	void visit(JavaFieldLocalDateType localDateField, PlantClassFieldRepresentation umlField);

	/**
	 * 
	 * @param localDateTimeField
	 * @param umlField
	 */
	void visit(JavaFieldLocalDateTimeType localDateTimeField, PlantClassFieldRepresentation umlField);
	
	/**
	 * 
	 * @param enumField
	 * @param umlField
	 */
	void visit(JavaFieldEnumType enumField, PlantClassFieldRepresentation umlField);

	/**
	 * 
	 * @param notNullField
	 * @param property
	 */
	void visit(JavaFieldNotNullType notNullField, PlantClassFieldPropertyRepresentation property);

	/**
	 * 
	 * @param transientField
	 * @param property
	 */
	void visit(JavaFieldTransientType transientField, PlantClassFieldPropertyRepresentation property);

	/**
	 * 
	 * @param uniqueField
	 * @param property
	 */
	void visit(JavaFieldUniqueType uniqueField, PlantClassFieldPropertyRepresentation property);

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
	void visit(JavaFieldOneToOneType oneToOneField, PlantRelationshipAssociation association);

	/**
	 * 
	 * @param oneToManyField
	 * @param association
	 */
	void visit(JavaFieldOneToManyType oneToManyField, PlantRelationshipAssociation association);

	/**
	 * 
	 * @param manyToOneField
	 * @param association
	 */
	void visit(JavaFieldManyToOneType manyToOneField, PlantRelationshipAssociation association);

	/**
	 * 
	 * @param manyToManyField
	 * @param association
	 */
	void visit(JavaFieldManyToManyType manyToManyField, PlantRelationshipAssociation association);

	/**
	 * 
	 * @param oneToOneField
	 * @param composition
	 */
	void visit(JavaFieldOneToOneType oneToOneField, PlantRelationshipComposition composition);

	/**
	 * 
	 * @param oneToManyField
	 * @param composition
	 */
	void visit(JavaFieldOneToManyType oneToManyField, PlantRelationshipComposition composition);

	/**
	 * 
	 * @param manyToOneField
	 * @param composition
	 */
	void visit(JavaFieldManyToOneType manyToOneField, PlantRelationshipComposition composition);

}
