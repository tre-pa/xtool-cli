package br.xtool.service;

import java.nio.file.Path;
import java.util.Set;
import java.util.function.Consumer;

import br.xtool.core.representation.EBootProject;
import br.xtool.core.representation.EBootProject.BootProjectSupport;
import br.xtool.core.representation.EBootRest;
import br.xtool.core.representation.EBootService;
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
	 * Adicionar um suporte ao projeto.
	 * 
	 * @param bootProject
	 * @param supportClass
	 */
	<T extends BootProjectSupport> void addSupport(EBootProject bootProject, Class<T> supportClass);

	/**
	 * Verifica se o projeto possui suporte.
	 * 
	 * @param bootProject
	 * @param supportClass
	 * @return
	 */
	<T extends BootProjectSupport> boolean hasSupport(EBootProject bootProject, Class<T> supportClass);

	//	/**
	//	 * 
	//	 * @param sourceFolder
	//	 * @param javaClass
	//	 */
	//	void save(EJavaSourceFolder sourceFolder, EJavaClass javaClass);
	//
	//	/**
	//	 * 
	//	 * @param javaClass
	//	 */
	//	void save(EJavaClass javaClass);
	//
	//	/**
	//	 * 
	//	 * @param sourceFolder
	//	 * @param javaInterface
	//	 */
	//	void save(EJavaSourceFolder sourceFolder, EJavaInterface javaInterface);
	//
	//	/**
	//	 * 
	//	 * @param javaInterface
	//	 */
	//	void save(EJavaInterface javaInterface);
	void save(EJavaType<?> javaType);

	/**
	 * 
	 * @param javaTypes
	 */
	void save(EJavaType<?>... javaTypes);

	/**
	 * Converte o diagrama de classe UML para as classes correspondentes.
	 * 
	 * @param bootProject
	 * @param umlClass
	 * @return
	 */
	void convertUmlClassDiagramToJavaClasses(EBootProject bootProject, Set<Visitor> vistors);

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
