package br.xtool.core.converter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.xtool.core.Workspace;
import br.xtool.core.representation.PlantClassDiagramRepresentation;
import br.xtool.core.representation.SpringBootProjectRepresentation;

@Component
public class ShellPlantClassDiagramRepresentationConverter implements Converter<String, PlantClassDiagramRepresentation> {

	@Autowired
	private Workspace workspace;

	@Override
	public PlantClassDiagramRepresentation convert(String source) {
		if (StringUtils.isNotEmpty(source)) {
			if (this.workspace.getWorkingProject() instanceof SpringBootProjectRepresentation) {
				SpringBootProjectRepresentation project = SpringBootProjectRepresentation.class.cast(this.workspace.getWorkingProject());
				// @formatter:off
				return project.getClassDiagrams()
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