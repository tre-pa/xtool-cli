package br.xtool.core.converter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.xtool.core.WorkContext;
import br.xtool.core.representation.EntityRepresentation;

@Component
public class EntityRepresentationConverter implements Converter<String, EntityRepresentation> {

	@Autowired
	private WorkContext workContext;

	@Override
	public EntityRepresentation convert(String source) {
		if (workContext.getProject().isPresent() && StringUtils.isNotEmpty(source)) {
			// @formatter:off
			return workContext.getProject().get().getEntities()
				.stream()
				.filter(e -> e.getName().equals(source))
				.findFirst()
				.orElseThrow(() -> new RuntimeException("Erro ao converer entidade"));
			// @formatter:on
		}
		return null;
	}

}
