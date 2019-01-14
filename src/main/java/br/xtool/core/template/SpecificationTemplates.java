package br.xtool.core.template;

import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;
import org.springframework.data.jpa.domain.Specification;

import br.xtool.core.JavaTemplate;
import br.xtool.core.representation.impl.EJpaSpecificationImpl;
import br.xtool.core.representation.springboot.EntityRepresentation;
import br.xtool.core.representation.springboot.SpecificationRepresentation;
import br.xtool.core.representation.springboot.SpringBootProjectRepresentation;
import br.xtool.core.util.RoasterUtil;

public class SpecificationTemplates {
	
	/**
	 * 
	 * @param springBootProject
	 * @param entity
	 * @return
	 */
	public static SpecificationRepresentation newSpecificationRepresentation(SpringBootProjectRepresentation springBootProject, EntityRepresentation entity) {
		String specificationName = entity.getName().concat("Specification");
		SpecificationRepresentation specification = new EJpaSpecificationImpl(springBootProject, RoasterUtil.createJavaClassSource(specificationName));
		specification.getRoasterJavaClass().setPackage(springBootProject.getRootPackage().getName().concat(".repository").concat(".specification"));
		specification.getRoasterJavaClass().addImport(entity.getQualifiedName());
		specification.getRoasterJavaClass().addImport(springBootProject.getRootPackage().getName().concat(".groovy.qy.QySpecification"));
		specification.getRoasterJavaClass().setSuperType("QySpecification<".concat(entity.getName()).concat(">"));
		return specification;
	}

	/**
	 * Gera o método filter
	 * 
	 * @param specification
	 * @param attribute
	 */
	public static void genFilterSpecfication(SpecificationRepresentation specification) {
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
