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
import br.xtool.core.representation.EEntity;

@Component
public class EEntityValueProvider extends ValueProviderSupport {

	@Autowired
	private WorkContext workContext;

	@Override
	public List<CompletionProposal> complete(MethodParameter parameter, CompletionContext completionContext, String[] hints) {
		if (this.workContext.getSpringBootProject().isPresent()) {
			// @formatter:off
			return this.workContext.getSpringBootProject().get().getEntities().stream()
					.map(EEntity::getName)
					.map(CompletionProposal::new)
					.collect(Collectors.toList());
			// @formatter:on
		}
		return new ArrayList<>();
	}

}
