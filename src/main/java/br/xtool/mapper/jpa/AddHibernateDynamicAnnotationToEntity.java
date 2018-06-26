package br.xtool.mapper.jpa;

import org.springframework.stereotype.Component;

import br.xtool.core.representation.EntityRepresentation;
import br.xtool.mapper.core.JpaMapper;

@Component
public class AddHibernateDynamicAnnotationToEntity implements JpaMapper {

	@Override
	public void apply(EntityRepresentation entity) {
		addAnnotation(entity, "DynamicUpdate", "org.hibernate.annotations.DynamicUpdate");
		addAnnotation(entity, "DynamicInsert", "org.hibernate.annotations.DynamicInsert");
	}

	private void addAnnotation(EntityRepresentation entity, String annotationName, String importName) {
		if (!entity.hasAnnotation(annotationName)) {
			entity.addImport(importName);
			entity.addAnnotation(annotation -> {
				annotation.setName(annotationName);
			});
		}
	}

}
