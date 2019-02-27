package br.xtool.core.template;

import org.springframework.stereotype.Component;

import br.xtool.core.TemplateBuilder;
import br.xtool.core.representation.springboot.EntityAttributeRepresentation;
import br.xtool.core.representation.springboot.EntityRepresentation;

@Component
public class NgEditTemplates {

	public String createHtmlAttributesDecl(EntityRepresentation entity) {
		StringBuilder sb = new StringBuilder();
		for (EntityAttributeRepresentation attr : entity.getAttributes()) {
			if (attr.isStringField()) {
				// @formatter:off
				sb.append(TemplateBuilder.builder()
						.tpl("<dxi-item dataField=\"{{attr.name}}\"",1)
						.tpl("  [editorOptions]=\"{ hint: 'Digite o {{attr.label}}' }\">",3)
						.tpl("  <dxo-label text=\"{{attr.label}}\"></dxo-label>",3)
						.tpl("</dxi-item>",3)
						.put("attr", attr)
						.build());
				// @formatter:on
			}
		}
		return sb.toString();
	}
}
