package br.xtool.core.pdiagram.map;

import java.util.function.Function;

import org.jboss.forge.roaster.model.source.JavaEnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.xtool.core.Workspace;
import br.xtool.core.helper.RoasterHelper;
import br.xtool.core.implementation.representation.JavaEnumRepresentationImpl;
import br.xtool.core.representation.plantuml.PlantEnumRepresentation;
import br.xtool.core.representation.springboot.JavaEnumRepresentation;
import br.xtool.core.representation.springboot.SpringBootProjectRepresentation;

/**
 * Transforma um enum UML (PlantUML) do diagrama de classe em um JavaEnumRepresentation.
 * 
 * @author jcruz
 *
 */
@Component
public class JavaEnumRepresentationMapper implements Function<PlantEnumRepresentation, JavaEnumRepresentation> {

	@Autowired
	private Workspace workspace;

	@Override
	public JavaEnumRepresentation apply(PlantEnumRepresentation plantEnum) {
		SpringBootProjectRepresentation springBootProject = this.workspace.getWorkingProject(SpringBootProjectRepresentation.class);
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
