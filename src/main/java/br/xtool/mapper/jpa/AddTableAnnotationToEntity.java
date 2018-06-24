package br.xtool.mapper.jpa;

import org.springframework.stereotype.Component;

import br.xtool.core.model.Entity;
import br.xtool.mapper.core.JpaMapper;

@Component
public class AddTableAnnotationToEntity implements JpaMapper {

	@Override
	public void apply(Entity entity) {
		if (!entity.hasAnnotation("Table")) {

		}
	}

}
