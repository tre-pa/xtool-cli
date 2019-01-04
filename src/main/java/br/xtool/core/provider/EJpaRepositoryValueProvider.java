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

import br.xtool.core.Workspace;
import br.xtool.core.representation.RepositoryRepresentation;
import br.xtool.core.representation.SpringBootProjectRepresentation;

@Component
public class EJpaRepositoryValueProvider extends ValueProviderSupport {

	@Autowired
	private Workspace workspace;

	@Override
	public List<CompletionProposal> complete(MethodParameter parameter, CompletionContext completionContext, String[] hints) {
		if (this.workspace.getWorkingProject() instanceof SpringBootProjectRepresentation) {
			SpringBootProjectRepresentation project = SpringBootProjectRepresentation.class.cast(this.workspace.getWorkingProject());
			// @formatter:off
			return project.getRepositories()
				.stream().map(RepositoryRepresentation::getName)
				.map(CompletionProposal::new)
				.collect(Collectors.toList());
			// @formatter:on
		}
		return new ArrayList<>();
	}

}
