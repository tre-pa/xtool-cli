package br.xtool.command.converter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.xtool.core.Workspace;
import br.xtool.core.representation.springboot.RepositoryRepresentation;

@Component
public class RepositoryRepresentationConverter implements Converter<String, RepositoryRepresentation> {

	@Autowired
	private Workspace workspace;

	@Override
	public RepositoryRepresentation convert(String source) {
		if (StringUtils.isNotEmpty(source)) {
			if (workspace.getSpringBootProject().isPresent()) {
				// @formatter:off
				return workspace.getSpringBootProject().get().getRepositories()
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
