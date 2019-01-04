package br.xtool.core.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.xtool.core.Workspace;
import br.xtool.core.representation.NgLayoutRepresentation;
import br.xtool.core.representation.NgProjectRepresentation;

@Component
public class StringToNgLayoutRepresentationConverter implements Converter<String, NgLayoutRepresentation> {

	@Autowired
	private Workspace workspace;

	@Override
	public NgLayoutRepresentation convert(String source) {
		if (this.workspace.getWorkingProject() instanceof NgProjectRepresentation) {
			NgProjectRepresentation project = NgProjectRepresentation.class.cast(this.workspace.getWorkingProject());
			// @formatter:off
			return project.getNgLayouts()
				.stream()
				.filter(e -> e.getName().equals(source))
				.findFirst()
				.orElseThrow(() -> new RuntimeException("Erro ao converter Layout."));
			// @formatter:on
		}
		return null;
	}

}
