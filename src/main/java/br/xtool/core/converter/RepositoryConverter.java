package br.xtool.core.converter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.xtool.core.PathService;
import br.xtool.core.model.Entity;
import br.xtool.core.model.Repository;

@Component
public class RepositoryConverter implements Converter<String, Repository> {

	@Autowired
	private PathService pathService;

	@Override
	public Repository convert(String source) {
		if (pathService.getSpringBootProject().isPresent() && StringUtils.isNotEmpty(source)) {
			// @formatter:off
			return pathService.getSpringBootProject().get().getRepositories()
				.stream()
				.filter(e -> e.getName().equals(source))
				.findFirst()
				.orElseThrow(() -> new RuntimeException("Erro ao converer repositório"));
			// @formatter:on
		}
		return null;
	}

}
