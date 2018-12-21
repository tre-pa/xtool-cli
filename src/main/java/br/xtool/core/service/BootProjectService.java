package br.xtool.core.service;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Set;
import java.util.function.Consumer;

import br.xtool.core.representation.EBootProject;
import br.xtool.core.representation.EBootRest;
import br.xtool.core.representation.EBootService;
import br.xtool.core.representation.EJavaClass;
import br.xtool.core.representation.EJavaEnum;
import br.xtool.core.representation.EJavaType;
import br.xtool.core.representation.EJpaEntity;
import br.xtool.core.representation.EJpaProjection;
import br.xtool.core.representation.EJpaRepository;
import br.xtool.core.representation.EJpaSpecification;
import br.xtool.core.visitor.Visitor;

/**
 * Serviços para projetos Spring Boot.
 * 
 * @author jcruz
 *
 */
public interface BootProjectService {

	/**
	 * Cria um diretório no projeto.
	 * 
	 * @param bootProject
	 * @param path
	 */
	void createDirectory(EBootProject bootProject, Path path);

	/**
	 * 
	 * @param javaType
	 */
	void save(EJavaType<?> javaType);

	/**
	 * 
	 * @param javaTypes
	 */
	void save(EJavaType<?>... javaTypes);

	/**
	 * Converte as classs UML para as classes Java correspondentes.
	 * 
	 * @param bootProject
	 * @param umlClass
	 * @return
	 */
	Collection<EJavaClass> umlClassesToJavaClasses(EBootProject bootProject, Set<Visitor> vistors);

	/**
	 * Converte os enums UML para as classes Java correspondentes.
	 * 
	 * @param bootProject
	 * @param vistors
	 * @return
	 */
	Collection<EJavaEnum> umlEnumsToJavaEnums(EBootProject bootProject);

	/**
	 * Cria a interface de repositório no projeto.
	 * 
	 * @param entity
	 * @return
	 */
	EJpaRepository createRepository(EBootProject bootProject, EJpaEntity entity);

	/**
	 * 
	 * @param bootProject
	 * @param entity
	 * @param consumer
	 */
	void createRepository(EBootProject bootProject, EJpaEntity entity, Consumer<EJpaRepository> consumer);

	/**
	 * Cria a interface de repositório no projeto.
	 * 
	 * @param bootProject
	 * @param entity
	 * @return
	 */
	EJpaProjection createProjection(EBootProject bootProject, EJpaEntity entity);

	/**
	 * 
	 * @param bootProject
	 * @param entity
	 * @param consumer
	 */
	void createProjection(EBootProject bootProject, EJpaEntity entity, Consumer<EJpaProjection> consumer);

	/**
	 * 
	 * @param bootProject
	 * @param entity
	 * @return
	 */
	EJpaSpecification createSpecification(EBootProject bootProject, EJpaEntity entity);

	/**
	 * 
	 * @param bootProject
	 * @param entity
	 * @param consumer
	 */
	void createSpecification(EBootProject bootProject, EJpaEntity entity, Consumer<EJpaSpecification> consumer);

	/**
	 * Cria uma classe de serviço no projeto.
	 * 
	 * @param bootProject
	 * @param jpaRepository
	 * @return
	 */
	EBootService createService(EBootProject bootProject, EJpaRepository jpaRepository);

	/**
	 * 
	 * @param bootProject
	 * @param jpaRepository
	 * @param consumer
	 */
	void createService(EBootProject bootProject, EJpaRepository jpaRepository, Consumer<EBootService> consumer);

	/**
	 * Cria classe rest no projeto.
	 * 
	 * @param bootProject
	 * @param jpaRepository
	 * @return
	 */
	EBootRest createRest(EBootProject bootProject, EJpaRepository jpaRepository);

	/**
	 * 
	 * @param bootProject
	 * @param jpaRepository
	 * @param consumer
	 */
	void createRest(EBootProject bootProject, EJpaRepository jpaRepository, Consumer<EBootRest> consumer);

}
