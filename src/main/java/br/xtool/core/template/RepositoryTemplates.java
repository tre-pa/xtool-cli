package br.xtool.core.template;

import org.jboss.forge.roaster.model.source.JavaInterfaceSource;
import org.jboss.forge.roaster.model.source.MethodSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.xtool.core.helper.RoasterHelper;
import br.xtool.core.implementation.representation.RepositoryRepresentationImpl;
import br.xtool.core.representation.springboot.EntityRepresentation;
import br.xtool.core.representation.springboot.RepositoryRepresentation;
import br.xtool.core.representation.springboot.SpringBootProjectRepresentation;

public class RepositoryTemplates {
	
	public static RepositoryRepresentation newRepositoryRepresentation(SpringBootProjectRepresentation springBootProject, EntityRepresentation entity) {
		String repositoryName = entity.getName().concat("Repository");
		RepositoryRepresentation repository = new RepositoryRepresentationImpl(springBootProject, RoasterHelper.createJavaInterface(repositoryName));
		repository.getRoasterInterface().setPackage(springBootProject.getRootPackage().getName().concat(".repository"));
		repository.getRoasterInterface().addImport(JpaRepository.class);
		repository.getRoasterInterface().addImport(JpaSpecificationExecutor.class);
		repository.getRoasterInterface().addImport(entity.getQualifiedName());
		repository.getRoasterInterface()
				.addInterface(JpaRepository.class.getSimpleName().concat("<").concat(entity.getName()).concat(",").concat("Long").concat(">"));
		repository.getRoasterInterface().addInterface(JpaSpecificationExecutor.class.getSimpleName().concat("<").concat(entity.getName()).concat(">"));
		repository.getRoasterInterface().addAnnotation(Repository.class);
		
		//Suporte a classe Qy
		repository.getRoasterInterface().addImport(springBootProject.getRootPackage().getName().concat(".groovy.qy.jpa.QyRepository"));
		repository.getRoasterInterface().addInterface("QyRepository<".concat(entity.getName()).concat(">"));
		
		return repository;
	}

	public static void genFindAllEntities(RepositoryRepresentation repository) {
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
