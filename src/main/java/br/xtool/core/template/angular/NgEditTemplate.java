package br.xtool.core.template.angular;

import org.springframework.stereotype.Component;

import br.xtool.core.helper.TemplateBuilder;
import br.xtool.core.representation.springboot.EntityAttributeRepresentation;
import br.xtool.core.representation.springboot.EntityRepresentation;

/**
 * Classe com os fragmentos de template do componente de edição do Angular. A classe é injetada no arquivo de template
 * src/main/resources/templates/angular/v7/edit/${entity.tsFileName}-edit/${entity.tsFileName}-edit.component.html.vm
 * 
 * 
 * @author mathews 
 */
@Component
public class NgEditTemplate {

	/**
	 * Itera sobre todos os atributos da entidade para a geração do fragmento de código HTML de edição de acordo com o tipo de dado do atributo.
	 * 
	 * @param entity Entidade JPA.
	 * @return Fragmento do componente de edição.
	 */
	public String createHtmlAttributesDecl(EntityRepresentation entity) {
		StringBuilder sb = new StringBuilder();
		for (EntityAttributeRepresentation attr : entity.getAttributes()) {
			addHtmlBooleanFieldDecl(sb, entity, attr);
			addHtmlNumberFieldDecl(sb, entity, attr);
			addHtmlStringFieldDecl(sb, entity, attr);
			addHtmlTemporalFieldDecl(sb, entity, attr);
		}
		return sb.toString();
	}

	/**
	 * Gera o fragmento de código HTML para atributos do tipo Boolean.
	 * 
	 * @param sb   Instância de StringBuilder
	 * @param attr Atributo da entidade JPA.
	 */
	public void addHtmlBooleanFieldDecl(StringBuilder sb, EntityRepresentation entity, EntityAttributeRepresentation attr) {
		if (attr.isBooleanField()) {
			// @formatter:off
			sb.append(TemplateBuilder.builder()
					.tpl("<!-- {{ attr.label }} -->", 1)
					.tpl("<div class=\"adx-form-field responsive-small-field\">", 1)
					.tpl("  <div class=\"adx-form-field-editor\">", 1)
					.tpl("    <dx-check-box [(ngModel)]=\"{{ entity.instanceName }}.{{ attr.name }}\"", 1)
					.tpl("      [(value)]=\"{{ entity.instanceName }}.{{ attr.name }}\"", 1)
					.tpl("      name=\"{{ attr.name }}\"", 1)
					.tpl("      text=\"{{ attr.label }}\">", 1)
					.tpl("    </dx-check-box>", 1)
					.tpl("  </div>", 1)
					.tpl("</div>", 1)
					.put("entity", entity)
					.put("attr", attr)
					.build());
			// @formatter:on
		}
	}

	/**
	 * Gera o fragmento de código HTML para atributos do tipo Number (Integer, Long, BigInteger...).
	 * 
	 * @param sb   Instância de StringBuilder
	 * @param attr Atributo da entidade JPA.
	 */
	public void addHtmlNumberFieldDecl(StringBuilder sb, EntityRepresentation entity, EntityAttributeRepresentation attr) {
		if (attr.isNumberField() && !attr.isId()) {
			// @formatter:off
			sb.append(TemplateBuilder.builder()
					.tpl("<!-- {{ attr.label }} -->",1)
					.tpl("<div class=\"adx-form-field responsive-small-field\">", 1)
					.tpl(  addHtmlFormFieldLabel(attr), 1)
					.tpl("  <div class=\"adx-form-field-editor\">", 1)
					.tpl("    <dx-number-box [(ngModel)]=\"{{ entity.instanceName }}.{{ attr.name }}\"", 1)
					.tpl("      [(value)]=\"{{ entity.instanceName }}.{{ attr.name }}\"", 1)
					.tpl("      name=\"{{ attr.name }}\">", 1)
					.tpl("      <dx-validator>", 1)
					.tpl(	      addHtmlRequiredValidationRule(attr),4)
					.tpl("      </dx-validator>", 1)
					.tpl("    </dx-number-box", 1)
					.tpl("  </div", 1)
					.tpl("</div", 1)
					.put("attr", attr)
					.build());
			// @formatter:on
		}
	}

	/**
	 * Gera o fragmento de código HTML para atributos do tipo String.
	 * 
	 * @param sb   Instância de StringBuilder
	 * @param attr Atributo da entidade JPA.
	 */
	public void addHtmlStringFieldDecl(StringBuilder sb, EntityRepresentation entity, EntityAttributeRepresentation attr) {
		if (attr.isStringField()) {
			// @formatter:off
			sb.append(TemplateBuilder.builder()
					.tpl("<!-- {{ attr.label }} -->", 1)
					.tpl(addHtmlStringResponsiveFormField(attr), 1)
					.tpl(  addHtmlFormFieldLabel(attr), 1)
					.tpl("  <div class=\"adx-form-field-editor\">", 1)
					.tpl("    <" + addHtmlStringTagName(attr) + " [(ngModel)]=\"{{ entity.instanceName }}.{{ attr.name }}\"", 1)
					.tpl("      [(value)]=\"{{ entity.instanceName }}.{{ attr.name }}\"", 1)
					.tpl(       addHtmlStringMask(attr), 1)
					.tpl("      name=\"{{ attr.name }}\">", 1)
					.tpl("      <dx-validator>", 1)
					.tpl(         addHtmlRequiredValidationRule(attr), 1)
					.tpl(         addHtmlStringLengthValidation(attr), 1)
					.tpl("      </dx-validator>", 1)
					.tpl("    </" + addHtmlStringTagName(attr) + ">", 1)
					.tpl("  </div>", 1)
					.tpl("</div>", 1)
					.put("attr", attr)
					.build());
			// @formatter:on
		}
	}

	/**
	 * Gera o fragmento de código HTML para atributos do tipo Temporal (Date, LocalDate, LocalDateTime ou java.sql.Date)
	 * 
	 * @param sb   Instância de StringBuilder
	 * @param attr Atributo da entidade JPA.
	 */
	public void addHtmlTemporalFieldDecl(StringBuilder sb, EntityRepresentation entity, EntityAttributeRepresentation attr) {
		if (attr.isTemporalField()) {
			// @formatter:off
			sb.append(TemplateBuilder.builder()
					.tpl("<!-- {{ attr.label }} -->",1)
					.tpl("<div class=\"adx-form-field responsive-small-field\">", 1)
					.tpl(  addHtmlFormFieldLabel(attr), 1)
					.tpl("  <div class=\"adx-form-field-editor\">", 1)
					.tpl("    <dx-date-box [(ngModel)]=\"{{ entity.instanceName }}.{{ attr.name }}\"", 1)
					.tpl("      [(value)]=\"{{ entity.instanceName }}.{{ attr.name }}\"", 1)
					.tpl("      [openOnFieldClick]=\"true\"", 1)
					.tpl("      [useMaskBehavior]=\"true\"", 1)
					.tpl("      name=\"{{ attr.name }}\"", 1)
					.tpl("      type=\"'date\">", 1)
					.tpl("      <dx-validator>", 1)
					.tpl(	      addHtmlRequiredValidationRule(attr),4)
					.tpl("      </dx-validator>", 1)
					.tpl("    </dx-date-box", 1)
					.tpl("  </div", 1)
					.tpl("</div", 1)
					.put("attr", attr)
					.build());
			// @formatter:on
		}
	}

	/**
	 * Verifica se o atributo JPA está marcado com máscara e gera o framento de código HTML refletindo a máscara.
	 * 
	 * @param attr
	 * @return Framento com código HTML de máscara ou String vazia.
	 */
	private String addHtmlStringMask(EntityAttributeRepresentation attr) {
		if (attr.getMask().isPresent()) {
			// @formatter:off
			return TemplateBuilder.builder()
					.tpl("      mask=\"{{ mask }}\"", 1)
					.tpl("      showMaskMode=\"onFocus\"", 1)
					.tpl("      [useMaskedValue]=\"true\"", 1)
					.put("mask", attr.getMask().get())
					.build();
			// @formatter:on
		}
		return "";
	}

	/**
	 * Verifica o comprimento do atributo JPA gera o framento de código HTML referente ao nome da Tag ideal para o atributo.
	 * 
	 * @param attr
	 * @return Framento com código HTML com o nome da Tag usada para editar uma String.
	 */
	private String addHtmlStringTagName(EntityAttributeRepresentation attr) {
		return attr.getColumnLength() > 255 ? "dx-text-area" : "dx-text-box";
	}

	/**
	 * Verifica se o atributo é marcado como requerido e retorna o fragmento de código HTML de validação do tipo 'required'.
	 * 
	 * @param attr Atributo da entidade JPA.
	 * @return Framento de código HTML de validação do tipo 'required' ou String vazia.
	 */
	private String addHtmlRequiredValidationRule(EntityAttributeRepresentation attr) {
		return attr.isRequired() ? "<dxi-validation-rule type=\"required\"></dxi-validation-rule>" : "";
	}

	/**
	 * Retorna o fragmento de código HTML que define o Label de um Form Field, já com a marcação de 'required' caso necessária.
	 * 
	 * @param attr Atributo da entidade JPA.
	 * @return Fragmento de código HTML que define o Label de um Form Field.
	 */
	private String addHtmlFormFieldLabel(EntityAttributeRepresentation attr) {
		return attr.isRequired() ? "  <div class=\"adx-form-field-label\"\n    title=\"{{ attr.label }} é obrigatório\">{{ attr.label }} *</div>" : "  <div class=\"adx-form-field-label\">{{ attr.label }}</div>";
	}

	/**
	 * Retorna o fragmento de código HTML que define um Form Field responsivo.
	 * 
	 * @param attr Atributo da entidade JPA.
	 * @return Fragmento de código HTML que define um Form Field responsivo.
	 */
	private String addHtmlStringResponsiveFormField(EntityAttributeRepresentation attr) {
		if (attr.getColumnLength() <= 50) return "<div class=\"adx-form-field responsive-small-field\">";
		if (attr.getColumnLength() <= 100) return "<div class=\"adx-form-field responsive-medium-field\">";
		return "<div class=\"adx-form-field responsive-large-field\">";
	}

	/**
	 * Retorna o fragmento de código HTML de validação de tamanho para atributo do tipo String.
	 * 
	 * @param attr Atributo de entidade JPA.
	 * @return Fragmento de código HTML de validação de tamanho.
	 */
	private String addHtmlStringLengthValidation(EntityAttributeRepresentation attr) {
		// @formatter:off
		return TemplateBuilder.builder()
				.tpl("<dxi-validation-rule type=\"stringLength\"\n max=\"{{ max }}\"></dxi-validation-rule>")
				.put("max", attr.getColumnLength())
				.build();
		// @formatter:on
	}

}