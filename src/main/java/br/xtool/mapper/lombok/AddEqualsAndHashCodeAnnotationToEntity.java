package br.xtool.mapper.lombok;

import org.springframework.stereotype.Component;

import br.xtool.core.model.Attribute;
import br.xtool.core.model.Entity;
import br.xtool.mapper.core.LombokMapper;

@Component
public class AddEqualsAndHashCodeAnnotationToEntity implements LombokMapper {

	@Override
	public void apply(Entity t) {
		if (!t.hasAnnotation("EqualsAndHashCode")) {
			t.addImport("lombok.EqualsAndHashCode");
			t.addAnnotation(annotation -> {
				annotation.setName("EqualsAndHashCode").setStringArrayValue("of", this.getAttributeOfEqualsAndHashCode(t));
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
