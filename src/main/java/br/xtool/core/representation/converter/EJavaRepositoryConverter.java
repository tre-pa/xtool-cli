package br.xtool.core.representation.converter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.xtool.core.WorkContext;
import br.xtool.core.representation.EJavaRepository;

@Component
public class EJavaRepositoryConverter implements Converter<String, EJavaRepository> {

	@Autowired
	private WorkContext workContext;

	@Override
	public EJavaRepository convert(String source) {
		if (workContext.getSpringBootProject().isPresent() && StringUtils.isNotEmpty(source)) {
			// @formatter:off
			return workContext.getSpringBootProject().get().getRepositories()
				.stream()
				.filter(e -> e.getName().equals(source))
				.findFirst()
				.orElseThrow(() -> new RuntimeException("Erro ao converer repositório"));
			// @formatter:on
		}
		return null;
	}

}
