package br.xtool.command.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.xtool.core.Workspace;
import br.xtool.core.representation.angular.NgPageRepresentation;
import br.xtool.core.representation.angular.NgProjectRepresentation;

@Component
public class NgPageRepresentationConverter implements Converter<String, NgPageRepresentation> {

	@Autowired
	private Workspace workspace;

	@Override
	public NgPageRepresentation convert(String source) {
		if (this.workspace.getWorkingProject() instanceof NgProjectRepresentation) {
			NgProjectRepresentation project = NgProjectRepresentation.class.cast(this.workspace.getWorkingProject());
			// @formatter:off
			return project.getNgPages()
				.stream()
				.filter(e -> e.getName().equals(source))
				.findFirst()
				.orElseThrow(() -> new RuntimeException("Erro ao converter Page."));
			// @formatter:on
		}
		return null;
	}

}
