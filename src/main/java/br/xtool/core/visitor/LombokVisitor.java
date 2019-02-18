package br.xtool.core.visitor;

import org.springframework.stereotype.Component;

import br.xtool.core.Visitor;
import br.xtool.core.representation.plantuml.PlantClassFieldPropertyRepresentation;
import br.xtool.core.representation.plantuml.PlantClassFieldRepresentation;
import br.xtool.core.representation.plantuml.PlantClassRepresentation;
import br.xtool.core.representation.plantuml.PlantRelationshipRepresentation;
import br.xtool.core.representation.plantuml.PlantRelationshipRepresentation.PlantRelationshipAssociation;
import br.xtool.core.representation.plantuml.PlantRelationshipRepresentation.PlantRelationshipComposition;
import br.xtool.core.representation.springboot.JavaClassRepresentation;
import br.xtool.core.representation.springboot.JavaFieldRepresentation;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.JavaFieldManyToManyType;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.JavaFieldManyToOneType;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.JavaFieldNotNullType;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.JavaFieldOneToManyType;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.JavaFieldOneToOneType;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.JavaFieldTransientType;
import br.xtool.core.representation.springboot.JavaFieldRepresentation.JavaFieldUniqueType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
public class LombokVisitor implements Visitor {

	@Override
	public void visit(JavaClassRepresentation javaClass, PlantClassRepresentation umlClass) {
		javaClass.addAnnotation(Getter.class);
		javaClass.addAnnotation(Setter.class);
		javaClass.addAnnotation(NoArgsConstructor.class);
//		javaClass.addEqualsAndHashCodeAnnotation("id");
		javaClass.addToStringAnnotation("id");
		umlClass.getTaggedValueAsArray("EqualsAndHashCode.of").ifPresent(tagValues -> javaClass.addAnnotation(EqualsAndHashCode.class).setStringArrayValue("of", tagValues));
	}

	@Override
	public void visit(JavaFieldRepresentation javaField, PlantClassFieldRepresentation umlField) {

	}

	@Override
	public void visit(JavaFieldRepresentation javaField, PlantClassFieldPropertyRepresentation umlFieldProperty) {

	}

//	@Override
//	public void visit(JavaFieldStringType stringField, PlantClassFieldRepresentation umlField) {
//
//	}

//	@Override
//	public void visit(JavaFieldBooleanType booleanField, PlantClassFieldRepresentation umlField) {
//
//	}

//	@Override
//	public void visit(JavaFieldLongType longField, PlantClassFieldRepresentation umlField) {
//
//	}

//	@Override
//	public void visit(JavaFieldIntegerType integerField, PlantClassFieldRepresentation umlField) {
//
//	}

//	@Override
//	public void visit(JavaFieldByteType byteField, PlantClassFieldRepresentation umlField) {
//
//	}

//	@Override
//	public void visit(JavaFieldBigDecimalType bigDecimalField, PlantClassFieldRepresentation umlField) {
//
//	}

//	@Override
//	public void visit(JavaFieldLocalDateType localDateField, PlantClassFieldRepresentation umlField) {
//
//	}

//	@Override
//	public void visit(JavaFieldLocalDateTimeType localDateTimeField, PlantClassFieldRepresentation umlField) {
//
//	}

//	@Override
//	public void visit(JavaFieldEnumType enumField, PlantClassFieldRepresentation umlField) {
//
//	}

	@Override
	public void visit(JavaFieldNotNullType notNullField, PlantClassFieldPropertyRepresentation property) {

	}

	@Override
	public void visit(JavaFieldTransientType notNullField, PlantClassFieldPropertyRepresentation property) {

	}

	@Override
	public void visit(JavaFieldUniqueType notNullField, PlantClassFieldPropertyRepresentation property) {

	}

	@Override
	public void visit(JavaFieldRepresentation javaField, PlantRelationshipRepresentation umlRelationship) {

	}

	@Override
	public void visit(JavaFieldOneToOneType oneToOneField, PlantRelationshipAssociation association) {

	}

	@Override
	public void visit(JavaFieldOneToManyType oneToManyField, PlantRelationshipAssociation association) {

	}

	@Override
	public void visit(JavaFieldManyToOneType manyToOneField, PlantRelationshipAssociation association) {

	}

	@Override
	public void visit(JavaFieldManyToManyType manyToManyField, PlantRelationshipAssociation association) {

	}

	@Override
	public void visit(JavaFieldOneToOneType oneToOneField, PlantRelationshipComposition composition) {

	}

	@Override
	public void visit(JavaFieldOneToManyType oneToManyField, PlantRelationshipComposition composition) {

	}

	@Override
	public void visit(JavaFieldManyToOneType manyToOneField, PlantRelationshipComposition composition) {

	}

}
