package br.xtool.core.template;

import java.util.Collection;

import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;
import org.springframework.data.jpa.domain.Specification;

import br.xtool.core.representation.EJpaAttribute;
import br.xtool.core.representation.EJpaSpecification;
import br.xtool.core.template.base.JavaTemplate;

public class JpaSpecificationTemplates {

	public static void genDefaultSpecifications(EJpaSpecification specification, Collection<EJpaAttribute> attributes) {
		for (EJpaAttribute attribute : attributes) {
			if (attribute.getType().isType(String.class)) {
				genEqualAttributeSpecfications(specification, attribute);
			} else if (attribute.getType().isType(Long.class)) {
				genEqualAttributeSpecfications(specification, attribute);
			}
		}
	}

	private static void genEqualAttributeSpecfications(EJpaSpecification specification, EJpaAttribute attribute) {
		if (!specification.getRoasterJavaClass().hasMethodSignature(String.format("%sEqual", attribute.getName()), attribute.getType().getName())) {
			MethodSource<JavaClassSource> method = specification.getRoasterJavaClass().addMethod();
			specification.getRoasterJavaClass().addImport(Specification.class);
			specification.getRoasterJavaClass().addImport(specification.getTargetEntity().getQualifiedName());
			method.setPublic().setStatic(true);
			method.setName(String.format("%sEqual", attribute.getName()));
			method.setReturnType(String.format("Specification<%s>", specification.getTargetEntity().getName()));
			method.addParameter(attribute.getType().getName(), attribute.getName());
			// @formatter:off
			method.setBody(JavaTemplate.from(""
					+ " return (root, criteriaQuery, criteriaBuilder) -> " + 
					"                criteriaBuilder.equal(root.get(\"{{attribute_name}}\"), {{attribute_name}});")
					.put("attribute_name", attribute.getName())
					.build());
			// @formatter:on
		}
	}
}
