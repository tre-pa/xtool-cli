package br.xtool.mapper.lombok;

import org.springframework.stereotype.Component;

import br.xtool.core.representation.EntityRepresentation;
import br.xtool.mapper.core.LombokMapper;

/**
 * Adiciona as annotation @Getter e @Setter na entidade JPA.
 * 
 * @author jcruz
 *
 */
@Component
public class AddAccessorAnnotationToEntity implements LombokMapper {

	@Override
	public void apply(EntityRepresentation t) {
		addAnnotation(t, "Getter", "lombok.Getter");
		addAnnotation(t, "Setter", "lombok.Setter");
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
