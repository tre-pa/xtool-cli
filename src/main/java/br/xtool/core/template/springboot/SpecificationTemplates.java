package br.xtool.core.template.springboot;

import java.util.List;
import java.util.stream.Collectors;

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
		specification.getRoasterJavaClass().addMethod("public void configure() {}");
		MethodSource<JavaClassSource> configureMethod = specification.getRoasterJavaClass().getMethod("configure");
		configureMethod.addAnnotation(Override.class);
		List<List<String>> body = entity.getJavaFields().stream().filter(field -> field.isRelationshipField())
				.map(field -> {
					return this.findEntity("Local", springBootProject).getJavaFields().stream()
							.filter(f -> !f.isRelationshipField()).map(f -> {
								if (f.isLocalDate()) {
									specification.getRoasterJavaClass().addImport(f.getType());
								}
								if (f.isLocalDateTime()) {
									specification.getRoasterJavaClass().addImport(f.getType());
								}
								if (f.isCollectionField()) {
									specification.getRoasterJavaClass().addImport(f.getType());
								}
								return "map(\"" + field.getName() + "." + f.getName() + "\", " + f.getType().getName()
										+ ".class, root -> root.get(\"" + field.getName() + "\").get(\"" + f.getName()
										+ "\"));\n";
							}).collect(Collectors.toList());
				}).collect(Collectors.toList());
		List<String> roots = body.stream().map(n -> getListWithoutComman(n) + "\n").collect(Collectors.toList());
		configureMethod.setBody(getListWithoutComman(roots));
		return specification;
	}

	private String getListWithoutComman(List<String> list) {
		StringBuilder builder = new StringBuilder();
		for (String value : list) {
			builder.append(value);
		}
		return builder.toString();

	}

	private EntityRepresentation findEntity(String name, SpringBootProjectRepresentation springBootProject) {
		return springBootProject.getEntities().stream().filter(entity -> entity.getName().equalsIgnoreCase(name))
				.findAny().get();
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
