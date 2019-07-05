package br.xtool.command.converter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.xtool.core.Workspace;
import br.xtool.core.representation.plantuml.PlantClassDiagramRepresentation;

@Component
public class PlantClassDiagramRepresentationConverter implements Converter<String, PlantClassDiagramRepresentation> {

	@Autowired
	private Workspace workspace;

	@Override
	public PlantClassDiagramRepresentation convert(String source) {
		if (StringUtils.isNotEmpty(source)) {
			if (this.workspace.getSpringBootProject().isPresent()) {
				// @formatter:off
				return this.workspace.getSpringBootProject().get().getClassDiagrams()
					.stream()
					.filter(e -> e.getPath().getFileName().toString().equals(source))
					.findFirst()
					.orElseThrow(() -> new RuntimeException("Erro ao converter PlantClassDiagramRepresentation."));
				// @formatter:on
			}
		}
		return null;
	}

}
