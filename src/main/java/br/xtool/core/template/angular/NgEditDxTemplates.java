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
 * @author jcruz 
 *
 */
@Component
public class NgEditDxTemplates {

	/**
	 * Itera sobre todos os atributos da entidade para a geração do fragmento de código HTML de edição de acordo com o tipo de dado do atributo.
	 * 
	 * @param entity Entidade JPA.
	 * @return Fragmento do componente de edição.
	 */
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

	/**
	 * Gera o fragmento de código HTML para atributos do tipo Boolean.
	 * 
	 * @param sb   Instância de StringBuilder
	 * @param attr Atributo da entidade JPA.
	 */
	public void addHtmlBooleanFieldDecl(StringBuilder sb, EntityAttributeRepresentation attr) {
		if (attr.isBooleanField()) {
			// @formatter:off
			sb.append(TemplateBuilder.builder()
					.tpl("<!-- {{attr.label}} -->",1)
					.tpl("<dxi-item dataField=\"{{attr.name}}\"",3)
					.tpl("  editorType=\"dxCheckBox\"",3)
					.tpl("  [editorOptions]=\"{ text: '{{attr.label}}' }\">",3)
					.tpl("  <dxo-label text=\" \"></dxo-label>",3)
					.tpl("</dxi-item>",3)
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
	public void addHtmlNumberFieldDecl(StringBuilder sb, EntityAttributeRepresentation attr) {
		if (attr.isNumberField()) {
			// @formatter:off
			sb.append(TemplateBuilder.builder()
					.tpl("<!-- {{attr.label}} -->",1)
					.tpl("<dxi-item dataField=\"{{attr.name}}\"",3)
					.tpl("  editorType=\"dxNumberBox\"",3)
					.tpl("  [editorOptions]=\"{ hint: 'Digite o {{attr.label}}' }\">",3)
					.tpl("  <dxo-label text=\"{{attr.label}}\"></dxo-label>",3)
					.tpl(	addHtmlRequiredValidation(attr),4)
					.tpl("</dxi-item>",3)
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
	public void addHtmlStringFieldDecl(StringBuilder sb, EntityAttributeRepresentation attr) {
		if (attr.isStringField()) {
			// @formatter:off
			sb.append(TemplateBuilder.builder()
					.tpl("<!-- {{attr.label}} -->",1)
					.tpl("<dxi-item dataField=\"{{attr.name}}\"",3)
					.tpl("  [editorOptions]=\"{",3)
					.tpl( 	  addHtmlStringMask(attr))
					.tpl(" 	  hint: 'Digite o {{attr.label}}'", 4)
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

	/**
	 * Gera o fragmento de código HTML para atributos do tipo Temporal (Date, LocalDate, LocalDateTime ou java.sql.Date)
	 * 
	 * @param sb   Instância de StringBuilder
	 * @param attr Atributo da entidade JPA.
	 */
	public void addHtmlTemporalFieldDecl(StringBuilder sb, EntityAttributeRepresentation attr) {
		if (attr.isTemporalField()) {
			// @formatter:off
			sb.append(TemplateBuilder.builder()
					.tpl("<!-- {{attr.label}} -->",1)
					.tpl("<dxi-item dataField=\"{{attr.name}}\"",3)
					.tpl("	editorType=\"dxDateBox\"",3)
					.tpl("  [editorOptions]=\"{",3)
					.tpl("	  type: 'datetime',",4)
					.tpl("	  width: '100%',",4)
					.tpl("	  useMaskBehavior: true,",4)
					.tpl("	  openOnFieldClick: true,",4)
					.tpl(" 	  hint: 'Selecione a {{attr.label}}'", 4)
					.tpl("  }\">",3)
					.tpl("  <dxo-label text=\"{{attr.label}}\"></dxo-label>",3)
					.tpl(	addHtmlRequiredValidation(attr),4)
					.tpl("</dxi-item>",3)
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
	public String addHtmlStringMask(EntityAttributeRepresentation attr) {
		if (attr.getMask().isPresent()) {
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

	/**
	 * Verifica se o atributo é marcado como requerido e retorna o fragmento de código HTML de validação do tipo 'required'.
	 * 
	 * @param attr Atributo da entidade JPA.
	 * @return Framento de código HTML de validação do tipo 'required' ou String vazia.
	 */
	public String addHtmlRequiredValidation(EntityAttributeRepresentation attr) {
		return attr.isRequired() ? "<dxi-validation-rule type=\"required\"></dxi-validation-rule>" : "";
	}

	/**
	 * Retorna o fragmento de código HTML de validação de tamanho para atributo do tipo String.
	 * 
	 * @param attr Atributo de entidade JPA.
	 * @return Fragmento de código HTML de validação de tamanho.
	 */
	public String addHtmlStringLengthValidation(EntityAttributeRepresentation attr) {
		// @formatter:off
		return TemplateBuilder.builder()
				.tpl("<dxi-validation-rule type=\"stringLength\" max=\"{{ max }}\"></dxi-validation-rule>")
				.put("max", attr.getColumnLength())
				.build();
		// @formatter:on
	}
}
