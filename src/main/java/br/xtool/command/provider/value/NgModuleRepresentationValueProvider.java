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
import br.xtool.core.representation.angular.NgModuleRepresentation;

@Component
public class NgModuleRepresentationValueProvider extends ValueProviderSupport {

	@Autowired
	private Workspace workspace;

	@Override
	public List<CompletionProposal> complete(MethodParameter parameter, CompletionContext completionContext, String[] hints) {
		if (workspace.getAngularProject().isPresent()) {
			// @formatter:off
			return workspace.getAngularProject().get().getNgModules().stream()
					.map(NgModuleRepresentation::getName)
					.map(CompletionProposal::new)
					.collect(Collectors.toList());
			// @formatter:on
		}
		return new ArrayList<>();
	}

}
