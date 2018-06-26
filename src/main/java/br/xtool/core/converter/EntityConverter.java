package br.xtool.core.converter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.xtool.core.PathService;
import br.xtool.core.representation.EntityRepresentation;

@Component
public class EntityConverter implements Converter<String, EntityRepresentation> {

	@Autowired
	private PathService pathService;

	@Override
	public EntityRepresentation convert(String source) {
		if (pathService.getSpringBootProject().isPresent() && StringUtils.isNotEmpty(source)) {
			// @formatter:off
			return pathService.getSpringBootProject().get().getEntities()
				.stream()
				.filter(e -> e.getName().equals(source))
				.findFirst()
				.orElseThrow(() -> new RuntimeException("Erro ao converer entidade"));
			// @formatter:on
		}
		return null;
	}

}
