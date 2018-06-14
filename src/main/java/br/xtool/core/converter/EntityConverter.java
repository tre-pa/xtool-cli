package br.xtool.core.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.xtool.core.PathContext;
import br.xtool.core.model.Entity;

@Component
public class EntityConverter implements Converter<String, Entity> {

	@Autowired
	private PathContext pathCtx;

	@Override
	public Entity convert(String source) {
		if (pathCtx.getSpringBootProject().isPresent()) {
			// @formatter:off
			return pathCtx.getSpringBootProject().get().getEntities()
				.stream()
				.filter(e -> e.getName().equals(source))
				.findFirst()
				.orElseThrow(() -> new RuntimeException("Erro ao converer entidade"));
			// @formatter:on
		}
		return null;
	}

}
