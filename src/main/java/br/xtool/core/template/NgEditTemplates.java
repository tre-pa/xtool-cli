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
			addStringFieldDecl(sb, attr);
		}
		return sb.toString();
	}

	private void addStringFieldDecl(StringBuilder sb, EntityAttributeRepresentation attr) {
		if (attr.isStringField()) {
			// @formatter:off
			sb.append(TemplateBuilder.builder()
					.tpl("<dxi-item dataField=\"{{attr.name}}\"",1)
					.tpl("  [editorOptions]=\"{ hint: 'Digite o {{attr.label}}' }\">",3)
					.tpl("  <dxo-label text=\"{{attr.label}}\"></dxo-label>",3)
					.tpl(	addRequiredValidation(attr),5)
					.tpl("</dxi-item>",3)
					.put("attr", attr)
					.build());
			// @formatter:on
		}
	}

	private String addRequiredValidation(EntityAttributeRepresentation attr) {
		if (attr.isRequired()) {
			return "<dxi-validation-rule type=\"required\"></dxi-validation-rule>";
		}
		return "";
	}
}
