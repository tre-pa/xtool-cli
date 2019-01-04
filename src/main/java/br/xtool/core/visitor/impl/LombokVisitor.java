package br.xtool.core.visitor.impl;

import org.springframework.stereotype.Component;

import br.xtool.core.representation.JavaClassRepresentation;
import br.xtool.core.representation.JavaClassRepresentation.EAuditableJavaClass;
import br.xtool.core.representation.JavaClassRepresentation.ECacheableJavaClass;
import br.xtool.core.representation.JavaClassRepresentation.EIndexedJavaClass;
import br.xtool.core.representation.JavaClassRepresentation.EReadOnlyJavaClass;
import br.xtool.core.representation.JavaClassRepresentation.EVersionableJavaClass;
import br.xtool.core.representation.JavaClassRepresentation.EViewJavaClass;
import br.xtool.core.representation.JavaFieldRepresentation;
import br.xtool.core.representation.JavaFieldRepresentation.EBigDecimalField;
import br.xtool.core.representation.JavaFieldRepresentation.EBooleanField;
import br.xtool.core.representation.JavaFieldRepresentation.EByteField;
import br.xtool.core.representation.JavaFieldRepresentation.EEnumField;
import br.xtool.core.representation.JavaFieldRepresentation.EIntegerField;
import br.xtool.core.representation.JavaFieldRepresentation.ELocalDateField;
import br.xtool.core.representation.JavaFieldRepresentation.ELocalDateTimeField;
import br.xtool.core.representation.JavaFieldRepresentation.ELongField;
import br.xtool.core.representation.JavaFieldRepresentation.EManyToManyField;
import br.xtool.core.representation.JavaFieldRepresentation.EManyToOneField;
import br.xtool.core.representation.JavaFieldRepresentation.ENotNullField;
import br.xtool.core.representation.JavaFieldRepresentation.EOneToManyField;
import br.xtool.core.representation.JavaFieldRepresentation.EOneToOneField;
import br.xtool.core.representation.JavaFieldRepresentation.EStringField;
import br.xtool.core.representation.JavaFieldRepresentation.ETransientField;
import br.xtool.core.representation.JavaFieldRepresentation.EUniqueField;
import br.xtool.core.representation.PlantClassRepresentation;
import br.xtool.core.representation.PlantFieldRepresentation;
import br.xtool.core.representation.PlantFieldPropertyRepresentation;
import br.xtool.core.representation.PlantRelationshipRepresentation;
import br.xtool.core.representation.PlantRelationshipRepresentation.EAssociation;
import br.xtool.core.representation.PlantRelationshipRepresentation.EComposition;
import br.xtool.core.representation.PlantStereotypeRepresentation;
import br.xtool.core.visitor.Visitor;
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
		javaClass.addEqualsAndHashCodeAnnotation("id");
		javaClass.addToStringAnnotation("id");
	}

	@Override
	public void visit(JavaClassRepresentation javaClass, PlantStereotypeRepresentation umlStereotype) {

	}

	@Override
	public void visit(EAuditableJavaClass auditableClass, PlantStereotypeRepresentation umlStereotype) {

	}

	@Override
	public void visit(ECacheableJavaClass cacheableClass, PlantStereotypeRepresentation umlStereotype) {

	}

	@Override
	public void visit(EIndexedJavaClass indexedClass, PlantStereotypeRepresentation umlStereotype) {

	}

	@Override
	public void visit(EViewJavaClass viewClass, PlantStereotypeRepresentation umlStereotype) {

	}

	@Override
	public void visit(EReadOnlyJavaClass readOnlyClass, PlantStereotypeRepresentation umlStereotype) {

	}

	@Override
	public void visit(EVersionableJavaClass versionableClass, PlantStereotypeRepresentation umlStereotype) {
	}

	@Override
	public void visit(JavaFieldRepresentation javaField, PlantFieldRepresentation umlField) {

	}

	@Override
	public void visit(JavaFieldRepresentation javaField, PlantFieldPropertyRepresentation umlFieldProperty) {

	}

	@Override
	public void visit(EStringField stringField, PlantFieldRepresentation umlField) {

	}

	@Override
	public void visit(EBooleanField booleanField, PlantFieldRepresentation umlField) {

	}

	@Override
	public void visit(ELongField longField, PlantFieldRepresentation umlField) {

	}

	@Override
	public void visit(EIntegerField integerField, PlantFieldRepresentation umlField) {

	}

	@Override
	public void visit(EByteField byteField, PlantFieldRepresentation umlField) {

	}

	@Override
	public void visit(EBigDecimalField bigDecimalField, PlantFieldRepresentation umlField) {

	}

	@Override
	public void visit(ELocalDateField localDateField, PlantFieldRepresentation umlField) {

	}

	@Override
	public void visit(ELocalDateTimeField localDateTimeField, PlantFieldRepresentation umlField) {

	}
	
	@Override
	public void visit(EEnumField enumField, PlantFieldRepresentation umlField) {

	}

	@Override
	public void visit(ENotNullField notNullField, PlantFieldPropertyRepresentation property) {

	}

	@Override
	public void visit(ETransientField notNullField, PlantFieldPropertyRepresentation property) {

	}

	@Override
	public void visit(EUniqueField notNullField, PlantFieldPropertyRepresentation property) {

	}

	@Override
	public void visit(JavaFieldRepresentation javaField, PlantRelationshipRepresentation umlRelationship) {

	}

	@Override
	public void visit(EOneToOneField oneToOneField, EAssociation association) {

	}

	@Override
	public void visit(EOneToManyField oneToManyField, EAssociation association) {

	}

	@Override
	public void visit(EManyToOneField manyToOneField, EAssociation association) {

	}

	@Override
	public void visit(EManyToManyField manyToManyField, EAssociation association) {

	}

	@Override
	public void visit(EOneToOneField oneToOneField, EComposition composition) {

	}

	@Override
	public void visit(EOneToManyField oneToManyField, EComposition composition) {

	}

	@Override
	public void visit(EManyToOneField manyToOneField, EComposition composition) {

	}

}
