package br.xtool.core.provider;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.shell.CompletionContext;
import org.springframework.shell.CompletionProposal;
import org.springframework.shell.standard.ValueProviderSupport;
import org.springframework.stereotype.Component;

import br.xtool.core.WorkContext;
import br.xtool.core.representation.EJavaEntity;

@Component
public class EJavaEntityValueProvider extends ValueProviderSupport {

	@Autowired
	private WorkContext workContext;

	@Override
	public List<CompletionProposal> complete(MethodParameter parameter, CompletionContext completionContext, String[] hints) {
		// @formatter:off
		return this.workContext.getSpringBootProject().getEntities().stream()
				.map(EJavaEntity::getName)
				.map(CompletionProposal::new)
				.collect(Collectors.toList());
		// @formatter:on
	}

}
