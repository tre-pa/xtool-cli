package br.xtool.core.converter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.xtool.core.WorkContext;
import br.xtool.core.representation.EJavaEntity;

@Component
public class EJavaEntityConverter implements Converter<String, EJavaEntity> {

	@Autowired
	private WorkContext workContext;

	@Override
	public EJavaEntity convert(String source) {
		if (StringUtils.isNotEmpty(source)) {
			// @formatter:off
			return this.workContext.getSpringBootProject().getEntities()
				.stream()
				.filter(e -> e.getName().equals(source))
				.findFirst()
				.orElseThrow(() -> new RuntimeException("Erro ao converer entidade"));
			// @formatter:on
		}
		return null;
	}

}
