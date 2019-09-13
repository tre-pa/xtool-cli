package br.xtool.pdiagram.map;

import java.util.function.BiFunction;

import org.jboss.forge.roaster.model.source.JavaEnumSource;
import org.springframework.stereotype.Component;

import br.xtool.helper.RoasterHelper;
import br.xtool.implementation.representation.JavaEnumRepresentationImpl;
import br.xtool.representation.plantuml.PlantEnumRepresentation;
import br.xtool.representation.springboot.JavaEnumRepresentation;
import br.xtool.representation.springboot.SpringBootProjectRepresentation;

/**
 * Transforma um enum UML (PlantUML) do diagrama de classe em um JavaEnumRepresentation.
 * 
 * @author jcruz
 *
 */
@Component
public class JavaEnumRepresentationMapper implements BiFunction<SpringBootProjectRepresentation, PlantEnumRepresentation, JavaEnumRepresentation> {

	@Override
	public JavaEnumRepresentation apply(SpringBootProjectRepresentation springBootProject, PlantEnumRepresentation plantEnum) {
		// @formatter:off
		JavaEnumRepresentation javaEnum = springBootProject.getRoasterJavaUnits().stream()
				.filter(javaUnit -> javaUnit.getGoverningType().isEnum())
				.filter(javaUnit -> javaUnit.getGoverningType().getName().equals(plantEnum.getName()))
				.map(javaUnit -> javaUnit.<JavaEnumSource>getGoverningType())
				.map(javaEnumSource -> new JavaEnumRepresentationImpl(springBootProject, javaEnumSource))
				.findFirst()
				.orElseGet(() -> new JavaEnumRepresentationImpl(springBootProject,RoasterHelper.createJavaEnumSource(plantEnum.getUmlPackage().getName(),plantEnum.getName())));
		plantEnum.getValues().stream()
			.filter(value -> !javaEnum.getConstants().contains(value))
			.forEach(value -> javaEnum.getRoasterEnum().addEnumConstant(value));
		// @formatter:on
		return javaEnum;
	}

}
