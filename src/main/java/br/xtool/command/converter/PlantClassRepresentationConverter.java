package br.xtool.command.converter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.xtool.core.Workspace;
import br.xtool.core.representation.plantuml.PlantClassRepresentation;

@Component
public class PlantClassRepresentationConverter implements Converter<String, PlantClassRepresentation> {

	@Autowired
	private Workspace workspace;

	@Override
	public PlantClassRepresentation convert(String source) {
		if (StringUtils.isNotEmpty(source)) {
			if (this.workspace.getSpringBootProject().isPresent()) {
				// @formatter:off
				return this.workspace.getSpringBootProject().get().getMainDomainClassDiagram().getClasses()
					.stream()
					.filter(e -> e.getName().equals(source))
					.findFirst()
					.orElseThrow(() -> new RuntimeException("Erro ao converter repositório."));
				// @formatter:on
			}
		}
		return null;
	}

}
