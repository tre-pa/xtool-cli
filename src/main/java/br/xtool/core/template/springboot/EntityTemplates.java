package br.xtool.core.template.springboot;

import org.hibernate.annotations.Immutable;
import org.springframework.stereotype.Component;

import br.xtool.core.helper.TemplateBuilder;
import br.xtool.core.representation.plantuml.PlantRelationshipRepresentation;
import br.xtool.core.representation.springboot.JavaClassRepresentation;

@Component
public class EntityTemplates {

	/**
	 * Adicionar o método auxiliar addX(X x);
	 * 
	 * @param javaClass
	 * @param relationship
	 */
	public <T extends PlantRelationshipRepresentation> void genAddListMethodRelationship(JavaClassRepresentation javaClass, T relationship) {
		if (!javaClass.hasAnnotation(Immutable.class)) {
			String methodName = String.format("add%s", relationship.getTargetClass().getName());
			if (!javaClass.getRoasterJavaClass().hasMethodSignature(methodName, relationship.getTargetClass().getName())) {
			// @formatter:off
			CharSequence methodBody = TemplateBuilder.builder()
					.tpl("this.{{source_role}}.add({{target_class_instance_name}});")
					.tpl("{{target_class_instance_name}}.set{{source_class_name}}(this);")
					.put("source_role", relationship.getSourceRole())
					.put("target_class_instance_name", relationship.getTargetClass().getInstanceName())
					.put("source_class_name", relationship.getSourceClass().getName())
					.build();
			// @formatter:on

			// @formatter:off
			javaClass.getRoasterJavaClass().addMethod()
				.setName(methodName)
				.setReturnTypeVoid()
				.setBody(methodBody.toString())
				.addParameter(relationship.getTargetClass().getName(), relationship.getTargetClass().getInstanceName());
			// @formatter:on
			}
		}
	}

	/**
	 * 
	 * @param javaClass
	 * @param relationship
	 */
	public <T extends PlantRelationshipRepresentation> void genRemoveListMethodRelationship(JavaClassRepresentation javaClass, T relationship) {
		if (!javaClass.hasAnnotation(Immutable.class)) {
			String methodName = String.format("remove%s", relationship.getTargetClass().getName());
			if (!javaClass.getRoasterJavaClass().hasMethodSignature(methodName, relationship.getTargetClass().getName())) {
			// @formatter:off
			CharSequence methodBody = TemplateBuilder.builder()
					.tpl("this.{{source_role}}.remove({{target_class_instance_name}});")
					.tpl("{{target_class_instance_name}}.set{{source_class_name}}(this);")
					.put("source_role", relationship.getSourceRole())
					.put("target_class_instance_name", relationship.getTargetClass().getInstanceName())
					.put("source_class_name", relationship.getSourceClass().getName())
					.build();
			// @formatter:on

			// @formatter:off
			javaClass.getRoasterJavaClass().addMethod()
				.setName(methodName)
				.setReturnTypeVoid()
				.setBody(methodBody.toString())
				.addParameter(relationship.getTargetClass().getName(), relationship.getTargetClass().getInstanceName());
			// @formatter:on
			}
		}
	}

}
