package br.xtool.core.representation.converter;

import java.util.function.Function;

import org.jboss.forge.roaster.model.source.JavaEnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.xtool.core.Workspace;
import br.xtool.core.representation.JavaEnumRepresentation;
import br.xtool.core.representation.PlantEnumRepresentation;
import br.xtool.core.representation.SpringBootProjectRepresentation;
import br.xtool.core.representation.impl.EJavaEnumImpl;
import br.xtool.core.util.RoasterUtil;

/**
 * Converter um enum UML do diagrama de classe em um EJavaEnum.
 * 
 * @author jcruz
 *
 */
@Component
public class JavaEnumRepresentationConverter implements Function<PlantEnumRepresentation, JavaEnumRepresentation> {

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
				.map(javaEnumSource -> new EJavaEnumImpl(springBootProject, javaEnumSource))
				.findFirst()
				.orElseGet(() -> new EJavaEnumImpl(springBootProject,RoasterUtil.createJavaEnumSource(plantEnum.getUmlPackage().getName(),plantEnum.getName())));
		plantEnum.getValues().stream()
			.filter(value -> !javaEnum.getConstants().contains(value))
			.forEach(value -> javaEnum.getRoasterEnum().addEnumConstant(value));
		// @formatter:on
		return javaEnum;
	}

}
