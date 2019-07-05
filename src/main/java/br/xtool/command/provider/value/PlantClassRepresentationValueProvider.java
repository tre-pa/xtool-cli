package br.xtool.command.provider.value;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.shell.CompletionContext;
import org.springframework.shell.CompletionProposal;
import org.springframework.shell.standard.ValueProviderSupport;
import org.springframework.stereotype.Component;

import br.xtool.core.Workspace;
import br.xtool.core.representation.plantuml.PlantClassRepresentation;
import br.xtool.core.representation.springboot.SpringBootNgProjectRepresentation;
import br.xtool.core.representation.springboot.SpringBootProjectRepresentation;

@Component
public class PlantClassRepresentationValueProvider extends ValueProviderSupport {

	@Autowired
	private Workspace workspace;

	@Override
	public List<CompletionProposal> complete(MethodParameter parameter, CompletionContext completionContext, String[] hints) {
		// @formatter:off
		if(this.workspace.getWorkingProject() instanceof SpringBootProjectRepresentation) {
			SpringBootProjectRepresentation project = SpringBootProjectRepresentation.class.cast(this.workspace.getWorkingProject());
			return project.getMainDomainClassDiagram().getClasses().stream()
					.map(PlantClassRepresentation::getName)
					.map(CompletionProposal::new)
					.collect(Collectors.toList());
		} else if (this.workspace.getWorkingProject() instanceof SpringBootNgProjectRepresentation) {
			SpringBootNgProjectRepresentation project = SpringBootNgProjectRepresentation.class.cast(this.workspace.getWorkingProject());
			return project.getSpringBootProject().getMainDomainClassDiagram().getClasses().stream()
					.map(PlantClassRepresentation::getName)
					.map(CompletionProposal::new)
					.collect(Collectors.toList());
		}
		return new ArrayList<>();
		// @formatter:on
	}

}
