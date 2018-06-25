package br.xtool.mapper.lombok;

import org.springframework.stereotype.Component;

import br.xtool.core.model.Entity;
import br.xtool.mapper.core.LombokMapper;

/**
 * Adicionar as annotation @Getter e @Setter na entidade JPA.
 * 
 * @author jcruz
 *
 */
@Component
public class AddLombokAccessorToEntity implements LombokMapper {

	@Override
	public void apply(Entity t) {
		addAnnotation(t, "Getter", "lombok.Getter");
		addAnnotation(t, "Setter", "lombok.Setter");
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
