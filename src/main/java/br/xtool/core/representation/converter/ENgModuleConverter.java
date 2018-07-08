package br.xtool.core.representation.converter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.xtool.core.WorkContext;
import br.xtool.core.representation.ENgModule;

@Component
public class ENgModuleConverter implements Converter<String, ENgModule> {

	@Autowired
	private WorkContext workContext;

	@Override
	public ENgModule convert(String source) {
		if (workContext.getAngularProject().isPresent() && StringUtils.isNotEmpty(source)) {
			// @formatter:off
			return workContext.getAngularProject().get().getNgModules()
				.stream()
				.filter(e -> e.getName().equals(source))
				.findFirst()
				.orElseThrow(() -> new RuntimeException("Erro ao converer entidade"));
			// @formatter:on
		}
		return null;
	}

}
