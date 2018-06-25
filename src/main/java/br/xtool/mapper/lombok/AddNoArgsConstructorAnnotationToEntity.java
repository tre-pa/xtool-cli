package br.xtool.mapper.lombok;

import br.xtool.core.model.Entity;
import br.xtool.mapper.core.LombokMapper;

/**
 * Adicionar a annotation @NoArgsConstructor a entidade JPA.
 * 
 * @author jcruz
 *
 */
public class AddNoArgsConstructorAnnotationToEntity implements LombokMapper {

	@Override
	public void apply(Entity t) {
		addAnnotation(t, "NoArgsConstructor", "lombok.NoArgsConstructor");
	}

	private void addAnnotation(Entity entity, String annotationName, String importName) {
		if (!entity.hasAnnotation(annotationName)) {
			entity.addImport(importName);
			entity.addAnnotation(annotation -> {
				annotation.setName(annotationName);
			});
		}
	}

}
