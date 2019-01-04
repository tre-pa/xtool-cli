package br.xtool.core.converter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.xtool.core.Workspace;
import br.xtool.core.representation.RepositoryRepresentation;
import br.xtool.core.representation.SpringBootProjectRepresentation;

@Component
public class StringToRepositoryRepresentationConverter implements Converter<String, RepositoryRepresentation> {

	@Autowired
	private Workspace workspace;

	@Override
	public RepositoryRepresentation convert(String source) {
		if (StringUtils.isNotEmpty(source)) {
			if (this.workspace.getWorkingProject() instanceof SpringBootProjectRepresentation) {
				SpringBootProjectRepresentation project = SpringBootProjectRepresentation.class.cast(this.workspace.getWorkingProject());
				// @formatter:off
				return project.getRepositories()
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
