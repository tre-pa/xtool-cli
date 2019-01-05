package br.xtool.core.converter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.xtool.core.Workspace;
import br.xtool.core.representation.EntityRepresentation;
import br.xtool.core.representation.SpringBootProjectRepresentation;

@Component
public class ShellEntityRepresentationConverter implements Converter<String, EntityRepresentation> {

	@Autowired
	private Workspace workspace;

	@Override
	public EntityRepresentation convert(String source) {
		if (StringUtils.isNotEmpty(source)) {
			if (this.workspace.getWorkingProject() instanceof SpringBootProjectRepresentation) {
				SpringBootProjectRepresentation project = SpringBootProjectRepresentation.class.cast(this.workspace.getWorkingProject());
				// @formatter:off
				return project.getEntities()
					.stream()
					.filter(e -> e.getName().equals(source))
					.findFirst()
					.orElseThrow(() -> new RuntimeException("Erro ao converter entidade."));
				// @formatter:on
			}
		}
		return null;
	}

}
