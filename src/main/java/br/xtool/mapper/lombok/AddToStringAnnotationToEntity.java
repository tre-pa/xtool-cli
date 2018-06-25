package br.xtool.mapper.lombok;

import org.springframework.stereotype.Component;

import br.xtool.core.model.Attribute;
import br.xtool.core.model.Entity;
import br.xtool.mapper.core.LombokMapper;

@Component
public class AddToStringAnnotationToEntity implements LombokMapper {

	@Override
	public void apply(Entity t) {
		if (!t.hasAnnotation("ToString")) {
			t.addImport("lombok.ToString");
			t.addAnnotation(annotation -> {
				annotation.setName("ToString").setStringArrayValue("of", this.getAttributeOfEqualsAndHashCode(t));
			});
		}
	}

	public String[] getAttributeOfEqualsAndHashCode(Entity entity) {
		// @formatter:off
		return entity.getAttributes().stream()
				.filter(attr -> !attr.isAssociation())
				.filter(attr -> !attr.isStatic())
				.filter(attr -> !attr.isJpaTransient())
				.filter(attr -> !attr.isJpaLob())
				.map(Attribute::getName)
				.toArray(String[]::new);
		// @formatter:on
	}

}
