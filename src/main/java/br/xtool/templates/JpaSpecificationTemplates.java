package br.xtool.templates;

import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;
import org.springframework.data.jpa.domain.Specification;

import br.xtool.core.representation.EJpaSpecification;
import br.xtool.core.template.JavaTemplate;

public class JpaSpecificationTemplates {

	/**
	 * Gera o método filter
	 * 
	 * @param specification
	 * @param attribute
	 */
	public static void genFilterSpecfication(EJpaSpecification specification) {
		if (!specification.getRoasterJavaClass().hasMethodSignature("filter")) {
			MethodSource<JavaClassSource> method = specification.getRoasterJavaClass().addMethod();
			specification.getRoasterJavaClass().addImport(Specification.class);
			specification.getRoasterJavaClass().addImport(specification.getTargetEntity().getQualifiedName());
			specification.getRoasterJavaClass().addImport(specification.getProject().getRootPackage().getName().concat(".groovy.qy.filter.QyFilter"));
			method.setPublic().setStatic(true);
			method.setName("filter");
			method.setReturnType(String.format("Specification<%s>", specification.getTargetEntity().getName()));
			method.addParameter("QyFilter", "filter");
			// @formatter:off
			method.setBody(JavaTemplate.from(""
					+ " return (root, cq, cb) -> {" + 
					"                return filter.toPredicate({{target_name}}.class, root, cq, cb);"
					+ " };")
					.put("target_name", specification.getTargetEntity().getName())
					.build());
			// @formatter:on
		}
	}

}
