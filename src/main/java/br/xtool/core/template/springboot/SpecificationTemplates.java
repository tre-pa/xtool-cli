package br.xtool.core.template.springboot;

import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import br.xtool.core.helper.RoasterHelper;
import br.xtool.core.helper.TemplateBuilder;
import br.xtool.core.implementation.representation.SpecificationRepresentationImpl;
import br.xtool.core.representation.springboot.EntityRepresentation;
import br.xtool.core.representation.springboot.SpecificationRepresentation;
import br.xtool.core.representation.springboot.SpringBootProjectRepresentation;

@Component
public class SpecificationTemplates {

	/**
	 * 
	 * @param springBootProject
	 * @param entity
	 * @return
	 */
	public SpecificationRepresentation newSpecificationRepresentation(SpringBootProjectRepresentation springBootProject, EntityRepresentation entity) {
		String specificationName = entity.getName().concat("Specification");
		SpecificationRepresentation specification = new SpecificationRepresentationImpl(springBootProject, RoasterHelper.createJavaClassSource(specificationName));
		specification.getRoasterJavaClass().setPackage(springBootProject.getRootPackage().getName().concat(".repository").concat(".specification"));
		specification.getRoasterJavaClass().addImport(entity.getQualifiedName());
		specification.getRoasterJavaClass().addImport("br.jus.tre_pa.jfilter.jpa.AbstractSpecification");
		specification.getRoasterJavaClass().setSuperType("AbstractSpecification<".concat(entity.getName()).concat(">"));
		specification.getRoasterJavaClass().addAnnotation(Component.class);
		return specification;
	}

	/**
	 * Gera o método filter
	 * 
	 * @param specification
	 * @param attribute
	 */
	public void genFilterSpecfication(SpecificationRepresentation specification) {
		if (!specification.getRoasterJavaClass().hasMethodSignature("filter")) {
			MethodSource<JavaClassSource> method = specification.getRoasterJavaClass().addMethod();
			specification.getRoasterJavaClass().addImport(Specification.class);
			specification.getRoasterJavaClass().addImport(specification.getTargetEntity().getQualifiedName());
			specification.getRoasterJavaClass().addImport(specification.getProject().getRootPackage().getName().concat(".groovy.jii.filter.JiiFilter"));
			method.setPublic().setStatic(true);
			method.setName("filter");
			method.setReturnType(String.format("Specification<%s>", specification.getTargetEntity().getName()));
			method.addParameter("JiiFilter", "filter");
			// @formatter:off
			method.setBody(
					TemplateBuilder.builder()
						.tpl("return (root, cq, cb) -> {")
						.tpl("		return filter.toPredicate({{target_name}}.class, root, cq, cb);")
						.tpl("};")
					.put("target_name", specification.getTargetEntity().getName())
					.build());
			// @formatter:on
		}
	}

}
