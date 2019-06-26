package br.xtool.core.pdiagram.map;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.xtool.core.implementation.representation.EntityAttributeRepresentationImpl;
import br.xtool.core.implementation.representation.EntityRepresentationImpl;
import br.xtool.core.pdiagram.RelationshipVisitor;
import br.xtool.core.representation.plantuml.PlantRelationshipRepresentation;
import br.xtool.core.representation.springboot.EntityAttributeRepresentation;
import br.xtool.core.representation.springboot.EntityRepresentation;
import br.xtool.core.representation.springboot.JavaClassRepresentation;
import br.xtool.core.representation.springboot.JavaFieldRepresentation;
import br.xtool.core.representation.springboot.SpringBootProjectRepresentation;

/**
 * Transforma um relacionamento UML (PlantUML) em um EJavaField.
 * 
 * @author jcruz
 *
 */
@Component
public class JavaRelationshipRepresentationMapper implements BiFunction<JavaClassRepresentation, PlantRelationshipRepresentation, JavaFieldRepresentation> {

	@Autowired
	private Set<RelationshipVisitor> visitors;

	@Override
	public JavaFieldRepresentation apply(JavaClassRepresentation javaClass, PlantRelationshipRepresentation plantRelationship) {
		JavaFieldRepresentation javaField = javaClass.addField(plantRelationship.getSourceRole());
		if (plantRelationship.getSourceMultiplicity().isToMany()) {
			// @formatter:off
			javaField.getRoasterField().getOrigin().addImport(List.class);
			javaField.getRoasterField().getOrigin().addImport(ArrayList.class);
			javaField.getRoasterField().getOrigin().addImport(plantRelationship.getTargetClass().getQualifiedName());
			javaField.getRoasterField()
					.setPrivate()
					.setName(plantRelationship.getSourceRole())
					.setType(String.format("List<%s>", plantRelationship.getTargetClass().getName()))
					.setLiteralInitializer("new ArrayList<>()");
			// @formatter:on
			this.visit(javaField, plantRelationship);
			return javaField;
		}
		javaField.getRoasterField().getOrigin().addImport(plantRelationship.getTargetClass().getQualifiedName());
		// @formatter:off
		javaField.getRoasterField()
				.setPrivate()
				.setName(plantRelationship.getSourceRole())
				.setType(plantRelationship.getTargetClass().getName());
		// @formatter:on
		this.visit(javaField, plantRelationship);
		return javaField;
	}

	private void visit(JavaFieldRepresentation javaField, PlantRelationshipRepresentation plantRelationship) {
		SpringBootProjectRepresentation project = javaField.getJavaClass().getProject();
		EntityRepresentation entity = new EntityRepresentationImpl(project, javaField.getJavaClass().getRoasterJavaClass());
		EntityAttributeRepresentation attribute = new EntityAttributeRepresentationImpl(project, entity, javaField.getRoasterField());
		this.visitors.forEach(visitor -> visitor.visit(attribute, plantRelationship));
	}

}
