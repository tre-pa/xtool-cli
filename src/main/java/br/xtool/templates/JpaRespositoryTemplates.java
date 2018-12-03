package br.xtool.templates;

import org.jboss.forge.roaster.model.source.JavaInterfaceSource;
import org.jboss.forge.roaster.model.source.MethodSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import br.xtool.core.representation.EJpaRepository;

public class JpaRespositoryTemplates {

	public static void genFindAllEntities(EJpaRepository repository) {
		if (!repository.getRoasterInterface().hasMethodSignature("findAllEntities", Pageable.class.getSimpleName())) {
			repository.getRoasterInterface().addImport(Query.class);
			repository.getRoasterInterface().addImport(repository.getTargetProjection().getQualifiedName());
			repository.getRoasterInterface().addImport(Page.class);
			repository.getRoasterInterface().addImport(Pageable.class);
			// @formatter:off
			MethodSource<JavaInterfaceSource> method = repository.getRoasterInterface().addMethod();
			method.setPublic()
				.setName("findAllEntities")
				.setReturnType(String.format("Page<%s>", repository.getTargetProjection().getName()))
				.addAnnotation(Query.class.getSimpleName())
				.setStringValue(String.format("select e from %s e", repository.getTargetEntity().getName()));
			// @formatter:on
			method.addParameter(Pageable.class.getSimpleName(), "pageable");

		}
	}
}
