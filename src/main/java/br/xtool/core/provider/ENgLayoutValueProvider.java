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

import br.xtool.core.representation.ENgLayout;
import br.xtool.core.representation.ENgProject;
import br.xtool.service.WorkspaceService;

@Component
public class ENgLayoutValueProvider extends ValueProviderSupport {

	@Autowired
	private WorkspaceService workspaceService;

	@Override
	public List<CompletionProposal> complete(MethodParameter parameter, CompletionContext completionContext, String[] hints) {
		if (this.workspaceService.getWorkingProject() instanceof ENgProject) {
			ENgProject project = ENgProject.class.cast(this.workspaceService.getWorkingProject());
			// @formatter:off
			return project.getNgLayouts().stream()
					.map(ENgLayout::getName)
					.map(CompletionProposal::new)
					.collect(Collectors.toList());
			// @formatter:on
		}
		return new ArrayList<>();
	}

}
