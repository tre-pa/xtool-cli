package br.xtool.core.representation.provider;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.shell.CompletionContext;
import org.springframework.shell.CompletionProposal;
import org.springframework.shell.standard.ValueProviderSupport;
import org.springframework.stereotype.Component;

import br.xtool.core.WorkContext;
import br.xtool.core.representation.ERepository;

@Component
public class ERepositoryValueProvider extends ValueProviderSupport {

	@Autowired
	private WorkContext workContext;

	@Override
	public List<CompletionProposal> complete(MethodParameter parameter, CompletionContext completionContext, String[] hints) {
		if (workContext.getSpringBootProject().isPresent()) {
			// @formatter:off
			return workContext.getSpringBootProject().get().getRepositories()
					.stream().map(ERepository::getName)
					.map(CompletionProposal::new)
					.collect(Collectors.toList());
			// @formatter:on
		}
		return new ArrayList<>();
	}

}
