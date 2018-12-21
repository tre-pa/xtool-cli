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

import br.xtool.core.representation.EJpaRepository;
import br.xtool.core.service.WorkspaceService;
import br.xtool.core.representation.EBootProject;

@Component
public class EJpaRepositoryValueProvider extends ValueProviderSupport {

	@Autowired
	private WorkspaceService workspaceService;

	@Override
	public List<CompletionProposal> complete(MethodParameter parameter, CompletionContext completionContext, String[] hints) {
		if (this.workspaceService.getWorkingProject() instanceof EBootProject) {
			EBootProject project = EBootProject.class.cast(this.workspaceService.getWorkingProject());
			// @formatter:off
			return project.getRepositories()
				.stream().map(EJpaRepository::getName)
				.map(CompletionProposal::new)
				.collect(Collectors.toList());
			// @formatter:on
		}
		return new ArrayList<>();
	}

}
