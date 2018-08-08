package br.xtool.core.provider;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.shell.CompletionContext;
import org.springframework.shell.CompletionProposal;
import org.springframework.shell.standard.ValueProviderSupport;
import org.springframework.stereotype.Component;

import br.xtool.core.representation.EProject;
import br.xtool.service.WorkspaceService;

@Component
public class EProjectValueProvider extends ValueProviderSupport {

	@Autowired
	private WorkspaceService workspaceService;

	@Override
	public List<CompletionProposal> complete(MethodParameter parameter, CompletionContext completionContext, String[] hints) {
		// @formatter:off
		return this.workspaceService.getWorkspace().getProjects().stream()
				.map(EProject::getName)
				.map(CompletionProposal::new)
				.collect(Collectors.toList());
		// @formatter:on
	}

}
