package br.xtool.core.template;

import java.util.concurrent.TimeUnit;

import javax.persistence.EntityNotFoundException;

import org.hibernate.annotations.Immutable;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.JavaDocSource;
import org.jboss.forge.roaster.model.source.MethodSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.xtool.core.TemplateBuilder;
import br.xtool.core.helper.InflectorHelper;
import br.xtool.core.helper.RoasterHelper;
import br.xtool.core.implementation.representation.RestClassRepresentationImpl;
import br.xtool.core.representation.springboot.JavaMethodRepresentation;
import br.xtool.core.representation.springboot.RepositoryRepresentation;
import br.xtool.core.representation.springboot.RestClassRepresentation;
import br.xtool.core.representation.springboot.SpringBootProjectRepresentation;
import lombok.extern.slf4j.Slf4j;
import strman.Strman;

@Component
public class RestClassTemplates {

	public RestClassRepresentation newRestClassRepresentation(SpringBootProjectRepresentation bootProject, RepositoryRepresentation repository) {
		String restName = repository.getTargetEntity().getName().concat("Rest");
		RestClassRepresentation rest = new RestClassRepresentationImpl(bootProject, RoasterHelper.createJavaClassSource(restName));
		rest.getRoasterJavaClass().setPackage(bootProject.getRootPackage().getName().concat(".rest"));
		rest.getRoasterJavaClass().addAnnotation(Slf4j.class);
//		rest.getRoasterJavaClass().addImport(Autowired.class);
//		rest.getRoasterJavaClass().addImport(repository.getQualifiedName());
		rest.getRoasterJavaClass().addAnnotation(RestController.class);
		// @formatter:off
		rest.getRoasterJavaClass().addAnnotation(RequestMapping.class)
			.setStringValue(String.format("api/%s",InflectorHelper.getInstance().pluralize(Strman.toKebabCase(repository.getTargetEntity().getName()))));
		rest.getRoasterJavaClass().addAnnotation(SuppressWarnings.class).setStringValue("unused");
//		rest.getRoasterJavaClass().addField()
//			.setPrivate()
//			.setName(repository.getInstanceName())
//			.setType(repository)
//			.addAnnotation(Autowired.class);
		// @formatter:on

		// Suporte a classe JiiRest
		rest.getRoasterJavaClass().addImport(bootProject.getRootPackage().getName().concat(".groovy.jii.JiiRest"));
		rest.getRoasterJavaClass().addImport(repository.getTargetEntity().getQualifiedName());
		rest.getRoasterJavaClass().addImport(repository.getQualifiedName());
		rest.getRoasterJavaClass().addImport(repository.getTargetSpecification().getQualifiedName());
		// @formatter:off
		rest.getRoasterJavaClass().setSuperType(String.format("JiiRest<%s,Long,%s,%s>", 
				repository.getTargetEntity().getName(), 
				repository.getTargetSpecification().getName(),
				repository.getName()));
		// @formatter:on
		return rest;
	}

	/**
	 * 
	 * Gera o método insert da classe Rest.
	 * 
	 * @param rest
	 * @param repository
	 */
	public void genInsertMethod(RestClassRepresentation rest, RepositoryRepresentation repository) {
		if (!repository.getTargetEntity().hasAnnotation(Immutable.class)) {
			if (!rest.getRoasterJavaClass().hasMethodSignature("insert", repository.getTargetEntity().getName())) {
				rest.getRoasterJavaClass().addImport(repository.getTargetEntity().getQualifiedName());

				JavaMethodRepresentation<JavaClassSource> method = rest.addMethod("insert");
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

				// @formatter:off
				method.getRoasterMethod().setBody(
					TemplateBuilder.builder()
						.tpl("return {{repository_instance_name}}.save({{target_instance_name}});")
						.put("repository_instance_name", repository.getInstanceName())
						.put("target_instance_name", repository.getTargetEntity().getInstanceName())
					.build());
				
				/*
				 * Adição do JavaDoc
				 */
				JavaDocSource<MethodSource<JavaClassSource>> javaDoc = method.getRoasterMethod().getJavaDoc();
				javaDoc.setText(
						TemplateBuilder.builder()
						.tpl("Cria um(a) novo(a) {{target_name}}")
						.put("target_name", repository.getTargetEntity().getName())
						.build()); 
				javaDoc.addTagValue("@param", repository.getTargetEntity().getInstanceName());
				javaDoc.addTagValue("@return", "Entidade gerenciada.");
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
	public void genUpdateMethod(RestClassRepresentation rest, RepositoryRepresentation repository) {
		if (!repository.getTargetEntity().hasAnnotation(Immutable.class)) {
			if (!rest.getRoasterJavaClass().hasMethodSignature("update", Long.class.getSimpleName(), repository.getTargetEntity().getName())) {
				rest.getRoasterJavaClass().addImport(repository.getTargetEntity().getQualifiedName());

				JavaMethodRepresentation<JavaClassSource> method = rest.addMethod("update");
				method.getRoasterMethod().setPublic();
				method.getRoasterMethod().addAnnotation(PutMapping.class).setStringValue("path", "/{id}");
				method.getRoasterMethod().setReturnType(repository.getTargetEntity().getName());
				// @formatter:off
				method.getRoasterMethod()
					.addParameter(Long.class.getSimpleName(), "id")
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
					TemplateBuilder.builder()
						.tpl("	if (!{{repository_instance_name}}.existsById(id)) throw new EntityNotFoundException(\"Entidade {{target_name}} não encontrada.\");")
						.tpl(" return {{repository_instance_name}}.save({{target_instance_name}});")
						.put("repository_instance_name", repository.getInstanceName())
						.put("target_instance_name", repository.getTargetEntity().getInstanceName())
						.put("target_name", repository.getTargetEntity().getName())
					.build());
			
			/*
			 * Adição do JavaDoc
			 */
			JavaDocSource<MethodSource<JavaClassSource>> javaDoc = method.getRoasterMethod().getJavaDoc();
			javaDoc.setText(
					TemplateBuilder.builder()
					.tpl("Atualiza um(a) {{target_name}}")
					.put("target_name", repository.getTargetEntity().getName())
					.build()); 
			javaDoc.addTagValue("@param", repository.getTargetEntity().getInstanceName());
			javaDoc.addTagValue("@return", "Entidade gerenciada.");
			// @formatter:on
			}
		}
	}

	/**
	 * 
	 * Gera o método delete da classe Rest.
	 * 
	 * @param rest
	 * @param repository
	 */
	public void genDeleteMethod(RestClassRepresentation rest, RepositoryRepresentation repository) {
		if (!repository.getTargetEntity().hasAnnotation(Immutable.class)) {
			if (!rest.getRoasterJavaClass().hasMethodSignature("delete", Long.class.getSimpleName())) {
				rest.getRoasterJavaClass().addImport(repository.getTargetEntity().getQualifiedName());

				JavaMethodRepresentation<JavaClassSource> method = rest.addMethod("delete");
				method.getRoasterMethod().setPublic();
				// @formatter:off
				method.getRoasterMethod().addAnnotation(DeleteMapping.class)
					.setStringValue("path", "/{id}");
				method.getRoasterMethod().addAnnotation(ResponseStatus.class)
					.setEnumValue(HttpStatus.NO_CONTENT);
				method.getRoasterMethod().setReturnTypeVoid();
				method.getRoasterMethod()
					.addParameter(Long.class.getSimpleName(), "id")
					.addAnnotation(PathVariable.class);

			// @formatter:off
			rest.getRoasterJavaClass().addImport(EntityNotFoundException.class);
			method.getRoasterMethod().setBody(
					TemplateBuilder.builder()
						.tpl("	if (!{{repository_instance_name}}.existsById(id)) throw new EntityNotFoundException(\"Entidade {{target_name}} não encontrada.\");")
						.tpl(" {{repository_instance_name}}.deleteById(id);")
						.put("repository_instance_name", repository.getInstanceName())
						.put("target_instance_name", repository.getTargetEntity().getInstanceName())
						.put("target_name", repository.getTargetEntity().getName())
					.build());
			/*
			 * Adição do JavaDoc
			 */
			JavaDocSource<MethodSource<JavaClassSource>> javaDoc = method.getRoasterMethod().getJavaDoc();
			javaDoc.setText(
					TemplateBuilder.builder()
					.tpl("Deleta um(a) {{target_name}}")
					.put("target_name", repository.getTargetEntity().getName())
					.build()); 
			javaDoc.addTagValue("@param",  "id");
			// @formatter:on
			}
		}
	}

	/**
	 * 
	 * @param rest
	 * @param repository
	 */
	public void genFindById(RestClassRepresentation rest, RepositoryRepresentation repository) {
		if (!rest.getRoasterJavaClass().hasMethodSignature("findById", Long.class.getSimpleName())) {
			rest.getRoasterJavaClass().addImport(repository.getTargetEntity().getQualifiedName());
			rest.getRoasterJavaClass().addImport(EntityNotFoundException.class);

			JavaMethodRepresentation<JavaClassSource> method = rest.addMethod("findById");
			method.getRoasterMethod().setPublic();
			// @formatter:off
			method.getRoasterMethod()
				.addAnnotation(GetMapping.class)
					.setStringValue("path", "/{id}");
			method.getRoasterMethod()
				.setReturnType(repository.getTargetEntity().getName());
			method.getRoasterMethod()
				.addParameter(Long.class.getSimpleName(), "id")
				.addAnnotation(PathVariable.class);
			method.getRoasterMethod().setBody(
					TemplateBuilder.builder()
						.tpl(" return {{repository_instance_name}}.findById(id).orElseThrow(() -> new EntityNotFoundException(\"Registro de Material não encontrado.\"));")
						.put("repository_instance_name", repository.getInstanceName())
						.put("target_instance_name", repository.getTargetEntity().getInstanceName())
						.put("target_name", repository.getTargetEntity().getName())
					.build());
			/*
			 * Adição do JavaDoc
			 */
			JavaDocSource<MethodSource<JavaClassSource>> javaDoc = method.getRoasterMethod().getJavaDoc();
			javaDoc.setText(
					TemplateBuilder.builder()
						.tpl("Retorna um(a) {{target_name}}")
						.put("target_name", repository.getTargetEntity().getName())
						.build()); 
			javaDoc.addTagValue("@param", "id");
			javaDoc.addTagValue("@return", "Entidade gerenciada.");
			// @formatter:on
		}
	}

	public void genFindAll(RestClassRepresentation rest, RepositoryRepresentation repository) {
		if (!rest.getRoasterJavaClass().hasMethodSignature("findAll", Pageable.class)) {
			rest.getRoasterJavaClass().addImport(repository.getTargetEntity().getQualifiedName());
			rest.getRoasterJavaClass().addImport(Pageable.class);
			rest.getRoasterJavaClass().addImport(ResponseEntity.class);
			rest.getRoasterJavaClass().addImport(CacheControl.class);
			rest.getRoasterJavaClass().addImport(TimeUnit.class);
			rest.getRoasterJavaClass().addImport(Page.class);

			JavaMethodRepresentation<JavaClassSource> method = rest.addMethod("findAll");
			method.getRoasterMethod().setPublic();
			// @formatter:off
			method.getRoasterMethod()
				.addAnnotation(GetMapping.class);
			method.getRoasterMethod()
				.setReturnType(String.format("ResponseEntity<Page<%s>>", repository.getTargetEntity().getName()));
			method.getRoasterMethod()
				.addParameter(Pageable.class.getSimpleName(), "pageable");
			method.getRoasterMethod().setBody(
					TemplateBuilder.builder()
						.tpl("	// @formatter:off\n")
						.tpl("	return ResponseEntity\n")
						.tpl("			.ok()\n")
						.tpl( "			.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))\n")
						.tpl("			.body({{repository_instance_name}}.findAll(pageable));\n")
						.tpl("	// @formatter:on ")
						.put("repository_instance_name", repository.getInstanceName())
						.put("target_instance_name", repository.getTargetEntity().getInstanceName())
						.put("target_name", repository.getTargetEntity().getName())
					.build());
			/*
			 * Adição do JavaDoc
			 */
			JavaDocSource<MethodSource<JavaClassSource>> javaDoc = method.getRoasterMethod().getJavaDoc();
			javaDoc.setText(
					TemplateBuilder.builder()
					.tpl("Retorna uma lista paginada de {{target_name}}")
					.put("target_name", repository.getTargetEntity().getName())
					.build()); 
			javaDoc.addTagValue("@return", "Lista de entidades gerenciadas.");
			// @formatter:on
		}
	}

	public void genFilter(RestClassRepresentation rest, RepositoryRepresentation repository) {
		if (!rest.getRoasterJavaClass().hasMethodSignature("filter", "Pageable", "JiiFilter")) {
			rest.getRoasterJavaClass().addImport(repository.getTargetEntity().getQualifiedName());
			rest.getRoasterJavaClass().addImport(Pageable.class);
			rest.getRoasterJavaClass().addImport(ResponseEntity.class);
			rest.getRoasterJavaClass().addImport(Page.class);
			rest.getRoasterJavaClass().addImport(repository.getTargetSpecification().getQualifiedName());
			rest.getRoasterJavaClass().addImport(repository.getProject().getRootPackage().getName().concat(".groovy.jii.filter.JiiFilter"));

			JavaMethodRepresentation<JavaClassSource> method = rest.addMethod("filter");
			method.getRoasterMethod().setPublic();
			// @formatter:off
			method.getRoasterMethod()
				.addAnnotation(PostMapping.class).setStringValue("path", "/_filter");
			method.getRoasterMethod()
				.setReturnType(String.format("ResponseEntity<Page<%s>>", repository.getTargetEntity().getName()));
			method.getRoasterMethod()
				.addParameter(Pageable.class.getSimpleName(), "pageable");
			method.getRoasterMethod()
				.addParameter("JiiFilter", "filter")
				.addAnnotation(RequestBody.class);
			method.getRoasterMethod().setBody(
					TemplateBuilder.builder()
						.tpl("	// @formatter:off")
						.tpl("	return ResponseEntity")
						.tpl("			.ok()\n")
						.tpl("			.body({{repository_instance_name}}.findAll({{specification_name}}.filter(filter), pageable));")
						.tpl("	// @formatter:on ")
						.put("repository_instance_name", repository.getInstanceName())
						.put("target_instance_name", repository.getTargetEntity().getInstanceName())
						.put("specification_name", repository.getTargetSpecification().getName())
						.put("target_name", repository.getTargetEntity().getName())
					.build());
			/*
			 * Adição do JavaDoc
			 */
			JavaDocSource<MethodSource<JavaClassSource>> javaDoc = method.getRoasterMethod().getJavaDoc();
			javaDoc.setText(
					TemplateBuilder.builder()
						.tpl("Retorna uma lista paginada filtrada de {{target_name}}")
						.put("target_name", repository.getTargetEntity().getName())
						.build()); 
			javaDoc.addTagValue("@return", "Lista de entidades gerenciadas.");
			// @formatter:on
		}
	}

	public void genCount(RestClassRepresentation rest, RepositoryRepresentation repository) {
		if (!rest.getRoasterJavaClass().hasMethodSignature("count")) {
			JavaMethodRepresentation<JavaClassSource> method = rest.addMethod("count");
			method.getRoasterMethod().setPublic();
			// @formatter:off
			method.getRoasterMethod()
				.addAnnotation(GetMapping.class)
				.setStringValue("path", "/_count");
			method.getRoasterMethod().setReturnType(long.class);
			method.getRoasterMethod().setBody(
					TemplateBuilder.builder()
						.tpl("	return {{repository_instance_name}}.count(); ")
						.put("repository_instance_name", repository.getInstanceName())
					.build());
			// @formatter:on
		}
	}

}
