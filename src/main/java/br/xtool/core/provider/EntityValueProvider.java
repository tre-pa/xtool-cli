package br.xtool.core.provider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.shell.CompletionContext;
import org.springframework.shell.CompletionProposal;
import org.springframework.shell.standard.ValueProviderSupport;
import org.springframework.stereotype.Component;

import br.xtool.core.PathContext;
import br.xtool.core.model.Entity;

@Component
public class EntityValueProvider extends ValueProviderSupport {

	@Autowired
	private PathContext pathCtx;

	@Override
	public List<CompletionProposal> complete(MethodParameter parameter, CompletionContext completionContext, String[] hints) {
		if (pathCtx.getSpringBootProject().isPresent()) {
			// @formatter:off
			return pathCtx.getSpringBootProject().get().getEntities()
					.stream().map(Entity::getName)
					.map(CompletionProposal::new)
					.collect(Collectors.toList());
			// @formatter:on
		}
		return new ArrayList<>();
	}

}
