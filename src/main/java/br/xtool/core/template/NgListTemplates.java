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
			addHtmlBooleanFieldDecl(sb, attr);
			addHtmlNumberFieldDecl(sb, attr);
			addHtmlStringFieldDecl(sb, attr);
			addHtmlTemporalFieldDecl(sb, attr);
		}
		return sb.toString();
	}

	public String createHtmlTotalItemsSummaryDecl(EntityRepresentation entity) {
		StringBuilder sb = new StringBuilder();
		for (EntityAttributeRepresentation attr : entity.getAttributes()) {
			addHtmlTemporalTotalItemSummaryDecl(sb, attr);
		}
		return sb.toString();
	}

	public String createHeaderFiltersDecl(EntityRepresentation entity) {
		StringBuilder sb = new StringBuilder();
		for (EntityAttributeRepresentation attr : entity.getAttributes()) {
			addBooleanHeaderFilterDecl(sb, attr);
		}
		return sb.toString();
	}

	public String createHeaderFiltersImpl(EntityRepresentation entity) {
		StringBuilder sb = new StringBuilder();
		for (EntityAttributeRepresentation attr : entity.getAttributes()) {
			addBooleanHeaderFilterImpl(sb, attr);
		}
		return sb.toString();
	}

	private void addHtmlBooleanFieldDecl(StringBuilder sb, EntityAttributeRepresentation attr) {
		if (attr.isBooleanField()) {
			// @formatter:off
			sb.append(TemplateBuilder.builder()
					.tpl("<!-- {{attr.label}} -->",1)
					.tpl("<dxi-column dataField=\"{{attr.name}}\"",3)
					.tpl("  caption=\"{{attr.label}}\"",3)
					.tpl("  alignment=\"left\"",3)
					.tpl("  trueText=\"Sim\"",3)
					.tpl("  falseText=\"Não\"",3)
					.tpl("  [showEditorAlways]=\"false\"",3)
					.tpl("  [allowFiltering]=\"false\"",3)
					.tpl("  [allowHeaderFiltering]=\"true\"",3)
					.tpl("  [headerFilter]=\"{ dataSource: {{attr.name}}HeaderFilter }\">",3)
					.tpl("</dxi-column>",3)
					.put("attr", attr)
					.build());
			// @formatter:on
		}
	}

	private void addHtmlNumberFieldDecl(StringBuilder sb, EntityAttributeRepresentation attr) {
		if (attr.isNumberField()) {
			// @formatter:off
			sb.append(TemplateBuilder.builder()
					.tpl("<!-- {{attr.label}} -->",1)
					.tpl("<dxi-column dataField=\"{{attr.name}}\"",3)
					.tpl("  caption=\"{{attr.label}}\"",3)
					.tpl("  alignment=\"left\"",3)
					.tpl("  [allowHeaderFiltering]=\"false\">",3)
					.tpl("</dxi-column>",3)
					.put("attr", attr)
					.build());
			// @formatter:on
		}
	}

	private void addHtmlStringFieldDecl(StringBuilder sb, EntityAttributeRepresentation attr) {
		if (attr.isStringField()) {
			// @formatter:off
			sb.append(TemplateBuilder.builder()
					.tpl("<!-- {{attr.label}} -->",1)
					.tpl("<dxi-column dataField=\"{{attr.name}}\"",3)
					.tpl("  caption=\"{{attr.label}}\"",3)
					.tpl("  [allowFiltering]=\"false\"",3)
					.tpl("  [allowSearch]=\"true\">",3)
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
					.tpl("<!-- {{attr.label}} -->",1)
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

	private void addHtmlTemporalTotalItemSummaryDecl(StringBuilder sb, EntityAttributeRepresentation attr) {
		if (attr.isTemporalField()) {
			// @formatter:off
			sb.append(TemplateBuilder.builder()
					.tpl("<dxi-total-item column=\"{{attr.name}}\"",3)
					.tpl("  summaryType=\"min\"></dxi-total-item>",3)
					.tpl("<dxi-total-item column=\"{{attr.name}}\"",3)
					.tpl("  summaryType=\"max\"></dxi-total-item>",3)
					.put("attr", attr)
					.build());
			// @formatter:on
		}
	}

	private void addBooleanHeaderFilterDecl(StringBuilder sb, EntityAttributeRepresentation attr) {
		if (attr.isBooleanField()) {
			// @formatter:off
			sb.append(TemplateBuilder.builder()
					.tpl("{{attr.name}}HeaderFilter: any;",1)
					.put("attr", attr)
					.build());
			// @formatter:on
		}
	}

	private void addBooleanHeaderFilterImpl(StringBuilder sb, EntityAttributeRepresentation attr) {
		if (attr.isBooleanField()) {
			// @formatter:off
			sb.append(TemplateBuilder.builder()
					.tpl("this.{{attr.name}}HeaderFilter = [{",1)
					.tpl("  text: 'Sim',",1)
					.tpl("  value: ['{{attr.name}}', '=', true]",1)
					.tpl("}, {",1)
					.tpl("  text: 'Não',",1)
					.tpl("  value: ['{{attr.name}}', '=', false]",1)
					.tpl("}];",1)
					.put("attr", attr)
					.build());
			// @formatter:on
		}
	}

}
