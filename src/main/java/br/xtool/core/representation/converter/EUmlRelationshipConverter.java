package br.xtool.core.representation.converter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;

import br.xtool.core.representation.EJavaClass;
import br.xtool.core.representation.EJavaField;
import br.xtool.core.representation.EUmlRelationship;
import br.xtool.core.representation.impl.EJavaFieldImpl.EManyToManyFieldImpl;
import br.xtool.core.representation.impl.EJavaFieldImpl.EManyToOneFieldImpl;
import br.xtool.core.representation.impl.EJavaFieldImpl.EOneToManyFieldImpl;
import br.xtool.core.representation.impl.EJavaFieldImpl.EOneToOneFieldImpl;
import br.xtool.core.visitor.Visitor;
import lombok.AllArgsConstructor;

/**
 * Converte um relacionamento UML em um EJavaField.
 * 
 * @author jcruz
 *
 */
@AllArgsConstructor
public class EUmlRelationshipConverter implements BiFunction<EJavaClass, EUmlRelationship, EJavaField> {

	private Set<? extends Visitor> visitors = new HashSet<>();

	@Override
	public EJavaField apply(EJavaClass javaClass, EUmlRelationship umlRelationship) {
		EJavaField javaField = javaClass.addField(umlRelationship.getSourceRole());
		if (umlRelationship.getSourceMultiplicity().isToMany()) {
			// @formatter:off
			javaField.getRoasterField().getOrigin().addImport(List.class);
			javaField.getRoasterField().getOrigin().addImport(ArrayList.class);
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

	private void visit(EJavaField javaField, EUmlRelationship umlRelationship) {
		this.visitors.forEach(visitor -> {
			visitor.visit(javaField, umlRelationship);
			if (umlRelationship.getSourceMultiplicity().isToOne() && umlRelationship.getTargetMultiplicity().isToOne()) visitor.visit(new EOneToOneFieldImpl(javaField), umlRelationship);
			if (umlRelationship.getSourceMultiplicity().isToMany() && umlRelationship.getTargetMultiplicity().isToOne()) visitor.visit(new EOneToManyFieldImpl(javaField), umlRelationship);
			if (umlRelationship.getSourceMultiplicity().isToOne() && umlRelationship.getTargetMultiplicity().isToMany()) visitor.visit(new EManyToOneFieldImpl(javaField), umlRelationship);
			if (umlRelationship.getSourceMultiplicity().isToMany() && umlRelationship.getTargetMultiplicity().isToMany()) visitor.visit(new EManyToManyFieldImpl(javaField), umlRelationship);
		});
	}

}
