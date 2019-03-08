package br.xtool.core.template;

import org.springframework.stereotype.Component;

import br.xtool.core.TemplateBuilder;
import br.xtool.core.representation.springboot.EntityAttributeRepresentation;
import br.xtool.core.representation.springboot.EntityRepresentation;

@Component
public class NgListTemplates {

	public String createHtmlAttributesDecl(EntityRepresentation entity) {
		StringBuilder sb = new StringBuilder();
		for (EntityAttributeRepresentation attr : entity.getAttributes()) {
			addHtmlStringFieldDecl(sb, attr);
			addHtmlTemporalFieldDecl(sb, attr);
		}
		return sb.toString();
	}

	private void addHtmlStringFieldDecl(StringBuilder sb, EntityAttributeRepresentation attr) {
		if (attr.isStringField()) {
			// @formatter:off
			sb.append(TemplateBuilder.builder()
					.tpl("<!-- {{attr.label}} !-->",1)
					.tpl("<dxi-column",3)
					.tpl("  dataField=\"{{attr.name}}\"",3)
					.tpl("  caption=\"{{attr.label}}\"",3)
					.tpl("  [allowFiltering]=\"false\">",3)
					.tpl("</dxi-column>",3)
					.put("attr", attr)
					.build());
			// @formatter:on
		}
	}

	private void addHtmlTemporalFieldDecl(StringBuilder sb, EntityAttributeRepresentation attr) {
		if (attr.isTemporalField()) {
			// @formatter:off
			sb.append(TemplateBuilder.builder()
					.tpl("<!-- {{attr.label}} !-->",1)
					.tpl("<dxi-column dataField=\"{{ attr.name }}\"",3)
					.tpl("  caption=\"{{ attr.label }}\"",3)
					.tpl("  dataType=\"date\"",3)
					.tpl("  format=\"dd/MM/yyyy\"",3)
					.tpl("  [allowHeaderFiltering]=\"false\">",3)
					.tpl("</dxi-column>",3)
					.put("attr", attr)
					.build());
			// @formatter:on
		}
	}
}
