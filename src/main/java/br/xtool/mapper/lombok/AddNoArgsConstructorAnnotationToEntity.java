package br.xtool.mapper.lombok;

import org.springframework.stereotype.Component;

import br.xtool.core.representation.EntityRepresentation;
import br.xtool.mapper.core.LombokMapper;

/**
 * Adicionar a annotation @NoArgsConstructor a entidade JPA.
 * 
 * @author jcruz
 *
 */
@Component
public class AddNoArgsConstructorAnnotationToEntity implements LombokMapper {

	@Override
	public void apply(EntityRepresentation t) {
		addAnnotation(t, "NoArgsConstructor", "lombok.NoArgsConstructor");
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
