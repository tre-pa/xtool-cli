package br.xtool.command.provider.value;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.shell.CompletionContext;
import org.springframework.shell.CompletionProposal;
import org.springframework.shell.standard.ValueProviderSupport;
import org.springframework.stereotype.Component;

import br.xtool.core.Workspace;
import br.xtool.core.representation.ProjectRepresentation;

/**
 * ValueProviderSupport retornando todos os projeto SpringBoot e SpringBootNg
 * 
 * @author jcruz
 *
 */
@Component
public class SpringBootProjectRepresentationValueProvider extends ValueProviderSupport {

	@Autowired
	private Workspace workspace;

	@Override
	public List<CompletionProposal> complete(MethodParameter parameter, CompletionContext completionContext, String[] hints) {
		// @formatter:off
		return workspace.getWorkspace().getProjects().stream()
				.filter(prj -> prj.getProjectType().equals(ProjectRepresentation.Type.SPRINGBOOT) || prj.getProjectType().equals(ProjectRepresentation.Type.SPRINGBOOTNG))
				.map(ProjectRepresentation::getName)
				.map(CompletionProposal::new)
				.collect(Collectors.toList());
		// @formatter:on
	}

}
