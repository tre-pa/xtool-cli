package br.xtool.core.representation.converter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;

import br.xtool.core.representation.JavaClassRepresentation;
import br.xtool.core.representation.JavaFieldRepresentation;
import br.xtool.core.representation.PlantRelationshipRepresentation;
import br.xtool.core.representation.impl.EJavaFieldImpl.EManyToManyFieldImpl;
import br.xtool.core.representation.impl.EJavaFieldImpl.EManyToOneFieldImpl;
import br.xtool.core.representation.impl.EJavaFieldImpl.EOneToManyFieldImpl;
import br.xtool.core.representation.impl.EJavaFieldImpl.EOneToOneFieldImpl;
import br.xtool.core.representation.impl.EPlantRelationshipImpl.EAssociationImpl;
import br.xtool.core.representation.impl.EPlantRelationshipImpl.ECompositionImpl;
import br.xtool.core.visitor.Visitor;
import lombok.AllArgsConstructor;

/**
 * Converte um relacionamento UML em um EJavaField.
 * 
 * @author jcruz
 *
 */
@AllArgsConstructor
public class EUmlRelationshipConverter implements BiFunction<JavaClassRepresentation, PlantRelationshipRepresentation, JavaFieldRepresentation> {

	private Set<? extends Visitor> visitors = new HashSet<>();

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
		this.visitors.forEach(visitor -> {
			visitor.visit(javaField, umlRelationship);
			if (!javaField.getEnum().isPresent()) {
				if (umlRelationship.isOneToOne() && umlRelationship.isAssociation()) visitor.visit(new EOneToOneFieldImpl(javaField), new EAssociationImpl(umlRelationship));
				if (umlRelationship.isOneToMany() && umlRelationship.isAssociation()) visitor.visit(new EOneToManyFieldImpl(javaField), new EAssociationImpl(umlRelationship));
				if (umlRelationship.isManyToOne() && umlRelationship.isAssociation()) visitor.visit(new EManyToOneFieldImpl(javaField), new EAssociationImpl(umlRelationship));
				if (umlRelationship.isManyToMany() && umlRelationship.isAssociation()) visitor.visit(new EManyToManyFieldImpl(javaField), new EAssociationImpl(umlRelationship));
				if (umlRelationship.isOneToOne() && umlRelationship.isComposition()) visitor.visit(new EOneToOneFieldImpl(javaField), new ECompositionImpl(umlRelationship));
				if (umlRelationship.isOneToMany() && umlRelationship.isComposition()) visitor.visit(new EOneToManyFieldImpl(javaField), new ECompositionImpl(umlRelationship));
				if (umlRelationship.isManyToOne() && umlRelationship.isComposition()) visitor.visit(new EManyToOneFieldImpl(javaField), new ECompositionImpl(umlRelationship));
			}
		});
	}

}
