package br.xtool.core.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.xtool.core.Workspace;
import br.xtool.core.representation.NgModuleRepresentation;
import br.xtool.core.representation.NgProjectRepresentation;

@Component
public class ShellNgModuleRepresentationConverter implements Converter<String, NgModuleRepresentation> {

	@Autowired
	private Workspace workspace;

	@Override
	public NgModuleRepresentation convert(String source) {
		if (this.workspace.getWorkingProject() instanceof NgProjectRepresentation) {
			NgProjectRepresentation project = NgProjectRepresentation.class.cast(this.workspace.getWorkingProject());
			// @formatter:off
			return project.getNgModules()
				.stream()
				.filter(e -> e.getName().equals(source))
				.findFirst()
				.orElseThrow(() -> new RuntimeException("Erro ao converter Modulo."));
			// @formatter:on
		}
		return null;
	}

}