package br.xtool.command.provider;

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
import br.xtool.core.representation.angular.NgModuleRepresentation;
import br.xtool.core.representation.angular.NgProjectRepresentation;
import br.xtool.core.representation.springboot.SpringBootProjectRepresentation;

@Component
public class NgModuleRepresentationValueProvider extends ValueProviderSupport {

	@Autowired
	private Workspace workspace;

	@Override
	public List<CompletionProposal> complete(MethodParameter parameter, CompletionContext completionContext, String[] hints) {

		boolean isNgProjectRepresentation = this.workspace.getWorkingProject() instanceof NgProjectRepresentation;
		boolean isSpringBootRepresentation = this.workspace.getWorkingProject() instanceof SpringBootProjectRepresentation;

		if (isNgProjectRepresentation || isSpringBootRepresentation) {
			// @formatter:off
			NgProjectRepresentation project = isNgProjectRepresentation ? 
					NgProjectRepresentation.class.cast(this.workspace.getWorkingProject()) : 
					SpringBootProjectRepresentation.class.cast(this.workspace.getWorkingProject()).getAssociatedAngularProject().get();
			return project.getNgModules().stream()
					.map(NgModuleRepresentation::getName)
					.map(CompletionProposal::new)
					.collect(Collectors.toList());
			// @formatter:on
		}
		return new ArrayList<>();
	}

}
