package br.xtool.command.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.xtool.core.Workspace;
import br.xtool.core.representation.angular.NgPageRepresentation;

@Component
public class NgPageRepresentationConverter implements Converter<String, NgPageRepresentation> {

	@Autowired
	private Workspace workspace;

	@Override
	public NgPageRepresentation convert(String source) {
		if (workspace.getAngularProject().isPresent()) {
			// @formatter:off
			return workspace.getAngularProject().get().getNgPages()
				.stream()
				.filter(e -> e.getName().equals(source))
				.findFirst()
				.orElseThrow(() -> new RuntimeException("Erro ao converter Page."));
			// @formatter:on
		}
		return null;
	}

}
