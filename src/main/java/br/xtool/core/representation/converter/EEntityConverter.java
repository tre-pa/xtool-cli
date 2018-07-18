package br.xtool.core.representation.converter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.xtool.core.WorkContext;
import br.xtool.core.representation.EEntity;

@Component
public class EEntityConverter implements Converter<String, EEntity> {

	@Autowired
	private WorkContext workContext;

	@Override
	public EEntity convert(String source) {
		if (this.workContext.getSpringBootProject().isPresent() && StringUtils.isNotEmpty(source)) {
			// @formatter:off
			return this.workContext.getSpringBootProject().get().getEntities()
				.stream()
				.filter(e -> e.getName().equals(source))
				.findFirst()
				.orElseThrow(() -> new RuntimeException("Erro ao converer entidade"));
			// @formatter:on
		}
		return null;
	}

}
