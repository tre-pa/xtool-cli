package br.xtool.core.visitor;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.xtool.core.Visitor;
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
import lombok.val;

@Component
public class JacksonVisitor implements Visitor {

	@Override
	public void visit(JavaClassRepresentation javaClass, PlantClassRepresentation umlClass) {

	}

	@Override
	public void visit(JavaFieldRepresentation javaField, PlantClassFieldRepresentation umlField) {

	}

	@Override
	public void visit(JavaFieldRepresentation javaField, PlantClassFieldPropertyRepresentation umlFieldProperty) {

	}

	@Override
	public void visit(JavaFieldStringType stringField, PlantClassFieldRepresentation umlField) {

	}

	@Override
	public void visit(JavaFieldBooleanType booleanField, PlantClassFieldRepresentation umlField) {

	}

	@Override
	public void visit(JavaFieldLongType longField, PlantClassFieldRepresentation umlField) {

	}

	@Override
	public void visit(JavaFieldIntegerType integerField, PlantClassFieldRepresentation umlField) {

	}

	@Override
	public void visit(JavaFieldByteType byteField, PlantClassFieldRepresentation umlField) {

	}

	@Override
	public void visit(JavaFieldBigDecimalType bigDecimalField, PlantClassFieldRepresentation umlField) {

	}

	@Override
	public void visit(JavaFieldLocalDateType localDateField, PlantClassFieldRepresentation umlField) {

	}

	@Override
	public void visit(JavaFieldLocalDateTimeType localDateTimeField, PlantClassFieldRepresentation umlField) {

	}

	@Override
	public void visit(JavaFieldEnumType enumField, PlantClassFieldRepresentation umlField) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(JavaFieldNotNullType notNullField, PlantClassFieldPropertyRepresentation property) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(JavaFieldTransientType notNullField, PlantClassFieldPropertyRepresentation property) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(JavaFieldUniqueType notNullField, PlantClassFieldPropertyRepresentation property) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(JavaFieldRepresentation javaField, PlantRelationshipRepresentation umlRelationship) {
		// javaField.getRelationship().get().getTargetClass().getJavaFields();
		// @formatter:off
		String[] relationships = umlRelationship.getTargetClass().getRelationships().stream()
				.map(_umlRelationship -> _umlRelationship.getSourceRole())
				.toArray(String[]::new);
		// @formatter:on
		if (relationships.length > 0) {
			val ann = javaField.addAnnotation(JsonIgnoreProperties.class);
			ann.setLiteralValue("allowSetters", "true");
			ann.setStringArrayValue("value", relationships);
		}
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
