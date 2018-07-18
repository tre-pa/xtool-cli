package br.xtool.core.representation.converter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.xtool.core.WorkContext;
import br.xtool.core.representation.ENgPage;

@Component
public class ENgPageConverter implements Converter<String, ENgPage> {

	@Autowired
	private WorkContext workContext;

	@Override
	public ENgPage convert(String source) {
		if (workContext.getAngularProject().isPresent() && StringUtils.isNotEmpty(source)) {
			// @formatter:off
			return workContext.getAngularProject().get().getNgPages()
				.stream()
				.filter(e -> e.getName().equals(source))
				.findFirst()
				.orElseThrow(() -> new RuntimeException("Erro ao converer entidade"));
			// @formatter:on
		}
		return null;
	}

}
