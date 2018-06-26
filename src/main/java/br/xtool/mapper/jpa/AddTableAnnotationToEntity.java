package br.xtool.mapper.jpa;

import org.springframework.stereotype.Component;

import br.xtool.core.NamePattern;
import br.xtool.core.representation.EntityRepresentation;
import br.xtool.mapper.core.JpaMapper;

/**
 * Adiciona a annotation @javax.persistence.Table na entidade.
 * 
 * @author jcruz
 *
 */
@Component
public class AddTableAnnotationToEntity implements JpaMapper {

	@Override
	public void apply(EntityRepresentation entity) {
		if (!entity.hasAnnotation("Table")) {
			entity.addImport("javax.persistence.Table");
			entity.addAnnotation(annotation -> {
				annotation.setName("Table").setStringValue("name", NamePattern.asDBTable(entity.getName()));
			});
		}
	}
}
