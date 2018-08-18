package br.xtool.core.template;

import br.xtool.core.representation.EJavaClass;
import br.xtool.core.representation.EUmlRelationship;
import br.xtool.core.template.base.JavaTemplate;

public class JpaEntityTemplates {

	/**
	 * Adicionar o método auxiliar addX(X x);
	 * 
	 * @param javaClass
	 * @param relationship
	 */
	public static <T extends EUmlRelationship> void genAddListMethodRelationship(EJavaClass javaClass, T relationship) {
		String methodName = String.format("add%s", relationship.getTargetClass().getName());
		if (!javaClass.getRoasterJavaClass().hasMethodSignature(methodName, relationship.getTargetClass().getName())) {
			// @formatter:off
			CharSequence methodBody = JavaTemplate
					.from("this.{{source_role}}.add({{target_class_instance_name}});"
						+ "{{target_class_instance_name}}.set{{source_class_name}}(this);")
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

	/**
	 * 
	 * @param javaClass
	 * @param relationship
	 */
	public static <T extends EUmlRelationship> void genRemoveListMethodRelationship(EJavaClass javaClass, T relationship) {
		String methodName = String.format("remove%s", relationship.getTargetClass().getName());
		if (!javaClass.getRoasterJavaClass().hasMethodSignature(methodName, relationship.getTargetClass().getName())) {
			// @formatter:off
			CharSequence methodBody = JavaTemplate
					.from("this.{{source_role}}.remove({{target_class_instance_name}});"
						+ "{{target_class_instance_name}}.set{{source_class_name}}(this);")
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
