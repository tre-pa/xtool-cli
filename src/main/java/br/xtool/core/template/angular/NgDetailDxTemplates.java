package br.xtool.core.template.angular;

import org.springframework.stereotype.Component;

import br.xtool.core.helper.TemplateBuilder;
import br.xtool.core.representation.springboot.EntityAttributeRepresentation;
import br.xtool.core.representation.springboot.EntityRepresentation;

/**
 * Template DevExtreme do componente de detail de projetos Angular.
 * 
 * @author jcruz
 *
 */
@Component
public class NgDetailDxTemplates {

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
					.tpl("<p><b>{{attr.label}}: </b>{% verbatim %}{{{% endverbatim %} {{entity.instanceName}}?.{{attr.name}} {% verbatim %}}}{% endverbatim %}</p>",2)
					.put("attr", attr)
					.put("entity", attr.getEntity())
					.build());
			// @formatter:on
		}
	}

	private void addHtmlTemporalFieldDecl(StringBuilder sb, EntityAttributeRepresentation attr) {
		if (attr.isTemporalField()) {
			// @formatter:off
			sb.append(TemplateBuilder.builder()
					.tpl("<!-- {{attr.label}} !-->",1)
					.tpl("<p><b>{{attr.label}}: </b>{% verbatim %}{{{% endverbatim %} {{entity.instanceName}}?.{{attr.name}} {% verbatim %}}}{% endverbatim %}</p>",2)
					.put("attr", attr)
					.put("entity", attr.getEntity())
					.build());
			// @formatter:on
		}
	}

}
