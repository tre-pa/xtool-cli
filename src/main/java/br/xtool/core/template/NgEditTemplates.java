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
			addHtmlStringFieldDecl(sb, attr);
		}
		return sb.toString();
	}

	private void addHtmlStringFieldDecl(StringBuilder sb, EntityAttributeRepresentation attr) {
		if (attr.isStringField()) {
			// @formatter:off
			sb.append(TemplateBuilder.builder()
					.tpl("<!-- {{attr.label}} !-->",1)
					.tpl("<dxi-item dataField=\"{{attr.name}}\"",3)
					.tpl("  [editorOptions]=\"{  ",3)
					.tpl( 	  addHtmlStringMask(attr))
					.tpl(" 	  hint: 'Digite o {{attr.label}}' ", 4)
					.tpl("  }\">",3)
					.tpl("  <dxo-label text=\"{{attr.label}}\"></dxo-label>",3)
					.tpl(	addHtmlRequiredValidation(attr),4)
					.tpl(	addHtmlStringLengthValidation(attr),4)
					.tpl("</dxi-item>",3)
					.put("attr", attr)
					.build());
			// @formatter:on
		}
	}
	
	private String addHtmlStringMask(EntityAttributeRepresentation attr)  {
		if(attr.getMask().isPresent()) {
			// @formatter:off
			return TemplateBuilder.builder()
					.tpl("mask: '{{ mask }}',",6)
					.tpl("showMaskMode: 'onFocus',",6)
					.tpl("useMaskedValue: true,",6)
					.put("mask", attr.getMask().get())
					.build();
			// @formatter:on
		}
		return "";
	}

	private String addHtmlRequiredValidation(EntityAttributeRepresentation attr) {
		return attr.isRequired() ? "<dxi-validation-rule type=\"required\"></dxi-validation-rule>" : "";
	}

	private String addHtmlStringLengthValidation(EntityAttributeRepresentation attr) {
		// @formatter:off
		return TemplateBuilder.builder()
				.tpl("<dxi-validation-rule type=\"stringLength\" max=\"{{ max }}\"></dxi-validation-rule>")
				.put("max", attr.getColumnLength())
				.build();
		// @formatter:on
	}
}
