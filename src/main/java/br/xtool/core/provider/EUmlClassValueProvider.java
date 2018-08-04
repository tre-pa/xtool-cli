package br.xtool.core.provider;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.shell.CompletionContext;
import org.springframework.shell.CompletionProposal;
import org.springframework.shell.standard.ValueProviderSupport;
import org.springframework.stereotype.Component;

import br.xtool.core.representation.EBootProject;
import br.xtool.core.representation.EUmlClass;
import br.xtool.core.service.WorkspaceService;

@Component
public class EUmlClassValueProvider extends ValueProviderSupport {

	@Autowired
	private WorkspaceService workspaceService;

	@Override
	public List<CompletionProposal> complete(MethodParameter parameter, CompletionContext completionContext, String[] hints) {
		// @formatter:off
		if(this.workspaceService.getWorkingProject() instanceof EBootProject) {
			EBootProject project = EBootProject.class.cast(this.workspaceService.getWorkingProject());
			return project.getDomainClassDiagram().getClasses().stream()
					.map(EUmlClass::getName)
					.map(CompletionProposal::new)
					.collect(Collectors.toList());
		}
		return new ArrayList<>();
		// @formatter:on
	}

}
