package br.xtool.command.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.xtool.core.Workspace;
import br.xtool.core.representation.angular.NgComponentRepresentation;

@Component
public class NgComponentRepresentationConverter implements Converter<String, NgComponentRepresentation> {

	@Autowired
	private Workspace workspace;

	@Override
	public NgComponentRepresentation convert(String source) {

		if (workspace.getAngularProject().isPresent()) {
			// @formatter:off
			return workspace.getAngularProject().get().getNgComponents()
				.stream()
				.filter(e -> e.getName().equals(source))
				.findFirst()
				.orElseThrow(() -> new RuntimeException("Erro ao converter Modulo."));
			// @formatter:on
		}
		return null;
	}

}
