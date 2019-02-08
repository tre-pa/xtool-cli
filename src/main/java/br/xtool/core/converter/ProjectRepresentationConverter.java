package br.xtool.core.converter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.xtool.core.Workspace;
import br.xtool.core.representation.ProjectRepresentation;

@Component
public class ProjectRepresentationConverter implements Converter<String, ProjectRepresentation> {

	@Autowired
	private Workspace workspace;

	@Override
	public ProjectRepresentation convert(String source) {
		if (StringUtils.isNotEmpty(source)) {
			// @formatter:off
			return this.workspace.getWorkspace().getProjects()
				.stream()
				.filter(e -> e.getName().equals(source))
				.findFirst()
				.orElseThrow(() -> new RuntimeException("Projeto não encontrado no workspace."));
			// @formatter:on
		}
		return null;
	}

}
