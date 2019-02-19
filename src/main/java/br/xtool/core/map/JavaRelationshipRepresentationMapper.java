package br.xtool.core.map;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.xtool.core.Visitor;
import br.xtool.core.implementation.representation.EntityAttributeRepresentationImpl;
import br.xtool.core.implementation.representation.EntityRepresentationImpl;
import br.xtool.core.representation.plantuml.PlantRelationshipRepresentation;
import br.xtool.core.representation.springboot.EntityAttributeRepresentation;
import br.xtool.core.representation.springboot.EntityRepresentation;
import br.xtool.core.representation.springboot.JavaClassRepresentation;
import br.xtool.core.representation.springboot.JavaFieldRepresentation;
import br.xtool.core.representation.springboot.SpringBootProjectRepresentation;

/**
 * Transforma um relacionamento UML em um EJavaField.
 * 
 * @author jcruz
 *
 */
@Component
public class JavaRelationshipRepresentationMapper implements BiFunction<JavaClassRepresentation, PlantRelationshipRepresentation, JavaFieldRepresentation> {

	@Autowired
	private Set<Visitor> visitors;

	@Override
	public JavaFieldRepresentation apply(JavaClassRepresentation javaClass, PlantRelationshipRepresentation umlRelationship) {
		JavaFieldRepresentation javaField = javaClass.addField(umlRelationship.getSourceRole());
		if (umlRelationship.getSourceMultiplicity().isToMany()) {
			// @formatter:off
			javaField.getRoasterField().getOrigin().addImport(List.class);
			javaField.getRoasterField().getOrigin().addImport(ArrayList.class);
			javaField.getRoasterField().getOrigin().addImport(umlRelationship.getTargetClass().getQualifiedName());
			javaField.getRoasterField()
					.setPrivate()
					.setName(umlRelationship.getSourceRole())
					.setType(String.format("List<%s>", umlRelationship.getTargetClass().getName()))
					.setLiteralInitializer("new ArrayList<>()");
			// @formatter:on
			this.visit(javaField, umlRelationship);
			return javaField;
		}
		javaField.getRoasterField().getOrigin().addImport(umlRelationship.getTargetClass().getQualifiedName());
		// @formatter:off
		javaField.getRoasterField()
				.setPrivate()
				.setName(umlRelationship.getSourceRole())
				.setType(umlRelationship.getTargetClass().getName());
		// @formatter:on
		this.visit(javaField, umlRelationship);
		return javaField;
	}

	private void visit(JavaFieldRepresentation javaField, PlantRelationshipRepresentation umlRelationship) {
		SpringBootProjectRepresentation project = javaField.getJavaClass().getProject();
		EntityRepresentation entity = new EntityRepresentationImpl(project, javaField.getJavaClass().getRoasterJavaClass());
		EntityAttributeRepresentation attribute = new EntityAttributeRepresentationImpl(project, entity, javaField.getRoasterField());
		this.visitors.forEach(visitor -> visitor.visit(attribute, umlRelationship));
	}

}
