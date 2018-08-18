package br.xtool.core.template;

import javax.persistence.EntityNotFoundException;

import org.hibernate.annotations.Immutable;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.xtool.core.representation.EBootRest;
import br.xtool.core.representation.EJavaMethod;
import br.xtool.core.representation.EJpaRepository;
import br.xtool.core.template.base.JavaTemplate;

public class RestTemplates {

	/**
	 * 
	 * Gera o método insert da classe Rest.
	 * 
	 * @param rest
	 * @param repository
	 */
	public static void genInsertMethod(EBootRest rest, EJpaRepository repository) {
		if (!repository.getTargetEntity().hasAnnotation(Immutable.class)) {
			if (!rest.getRoasterJavaClass().hasMethodSignature("insert", repository.getTargetEntity().getName())) {
				rest.getRoasterJavaClass().addImport(repository.getTargetEntity().getQualifiedName());

				EJavaMethod<JavaClassSource> method = rest.addMethod("insert");
				method.getRoasterMethod().setPublic();
				method.getRoasterMethod().addAnnotation(PostMapping.class);
				method.getRoasterMethod().addAnnotation(ResponseStatus.class).setEnumValue(HttpStatus.CREATED);
				method.getRoasterMethod().setReturnType(repository.getTargetEntity().getName());
				// @formatter:off
				method.getRoasterMethod()
					.addParameter(
							repository.getTargetEntity().getName(), 
							repository.getTargetEntity().getInstanceName())
					.addAnnotation(RequestBody.class);
				// @formatter:on

			// @formatter:off
			method.getRoasterMethod().setBody(
					JavaTemplate.from("return {{repository_instance_name}}.save({{target_instance_name}});")
						.put("repository_instance_name", repository.getInstanceName())
						.put("target_instance_name", repository.getTargetEntity().getInstanceName())
					.build());
			// @formatter:on
			}
		}
	}

	/**
	 * 
	 * Gera o método update da classe Rest.
	 * 
	 * @param rest
	 * @param repository
	 */
	public static void genUpdateMethod(EBootRest rest, EJpaRepository repository) {
		if (!repository.getTargetEntity().hasAnnotation(Immutable.class)) {
			if (!rest.getRoasterJavaClass().hasMethodSignature("update", Long.class.getSimpleName(), repository.getTargetEntity().getName())) {
				rest.getRoasterJavaClass().addImport(repository.getTargetEntity().getQualifiedName());

				EJavaMethod<JavaClassSource> method = rest.addMethod("update");
				method.getRoasterMethod().setPublic();
				method.getRoasterMethod().addAnnotation(PutMapping.class).setStringValue("path", "/{id}");
				method.getRoasterMethod().setReturnType(repository.getTargetEntity().getName());
				// @formatter:off
				method.getRoasterMethod()
					.addParameter(Long.class, "id")
					.addAnnotation(PathVariable.class);
				method.getRoasterMethod()
					.addParameter(
							repository.getTargetEntity().getName(), 
							repository.getTargetEntity().getInstanceName())
					.addAnnotation(RequestBody.class);
				// @formatter:on

			// @formatter:off
			rest.getRoasterJavaClass().addImport(EntityNotFoundException.class);
			method.getRoasterMethod().setBody(
					JavaTemplate.from(""
							+ "	if ({{repository_instance_name}}.exists(id)) {"
							+ " 	  return {{repository_instance_name}}.save({{target_instance_name}});"
							+ " }"
							+ " throw new EntityNotFoundException(\"Entidade {{target_name}} não encontrada.\");"
							+ "")
						.put("repository_instance_name", repository.getInstanceName())
						.put("target_instance_name", repository.getTargetEntity().getInstanceName())
						.put("target_name", repository.getTargetEntity().getName())
					.build());
			// @formatter:on
			}
		}
	}
}
