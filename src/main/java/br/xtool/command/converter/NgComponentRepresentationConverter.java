package br.xtool.command.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.xtool.core.Workspace;
import br.xtool.core.representation.angular.NgComponentRepresentation;
import br.xtool.core.representation.angular.NgProjectRepresentation;
import br.xtool.core.representation.springboot.SpringBootProjectRepresentation;

@Component
public class NgComponentRepresentationConverter implements Converter<String, NgComponentRepresentation> {

	@Autowired
	private Workspace workspace;

	@Override
	public NgComponentRepresentation convert(String source) {

		boolean isNgProjectRepresentation = this.workspace.getWorkingProject() instanceof NgProjectRepresentation;
		boolean isSpringBootRepresentation = this.workspace.getWorkingProject() instanceof SpringBootProjectRepresentation;

		if (isNgProjectRepresentation || isSpringBootRepresentation) {
			// @formatter:off
			NgProjectRepresentation project = isNgProjectRepresentation ? 
					NgProjectRepresentation.class.cast(this.workspace.getWorkingProject()) : 
					SpringBootProjectRepresentation.class.cast(this.workspace.getWorkingProject()).getAssociatedAngularProject().get();
			return project.getNgComponents()
				.stream()
				.filter(e -> e.getName().equals(source))
				.findFirst()
				.orElseThrow(() -> new RuntimeException("Erro ao converter Modulo."));
			// @formatter:on
		}
		return null;
	}

}
