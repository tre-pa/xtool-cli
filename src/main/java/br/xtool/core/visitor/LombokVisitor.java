package br.xtool.core.visitor;

import org.springframework.stereotype.Component;

import br.xtool.core.Visitor;
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
	public void visit(JavaFieldRepresentation javaField, PlantClassFieldRepresentation umlField) {

	}

	@Override
	public void visit(JavaFieldRepresentation javaField, PlantClassFieldPropertyRepresentation umlFieldProperty) {

	}

	@Override
	public void visit(EStringField stringField, PlantClassFieldRepresentation umlField) {

	}

	@Override
	public void visit(EBooleanField booleanField, PlantClassFieldRepresentation umlField) {

	}

	@Override
	public void visit(ELongField longField, PlantClassFieldRepresentation umlField) {

	}

	@Override
	public void visit(EIntegerField integerField, PlantClassFieldRepresentation umlField) {

	}

	@Override
	public void visit(EByteField byteField, PlantClassFieldRepresentation umlField) {

	}

	@Override
	public void visit(EBigDecimalField bigDecimalField, PlantClassFieldRepresentation umlField) {

	}

	@Override
	public void visit(ELocalDateField localDateField, PlantClassFieldRepresentation umlField) {

	}

	@Override
	public void visit(ELocalDateTimeField localDateTimeField, PlantClassFieldRepresentation umlField) {

	}
	
	@Override
	public void visit(EEnumField enumField, PlantClassFieldRepresentation umlField) {

	}

	@Override
	public void visit(ENotNullField notNullField, PlantClassFieldPropertyRepresentation property) {

	}

	@Override
	public void visit(ETransientField notNullField, PlantClassFieldPropertyRepresentation property) {

	}

	@Override
	public void visit(EUniqueField notNullField, PlantClassFieldPropertyRepresentation property) {

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
