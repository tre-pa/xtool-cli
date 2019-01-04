package br.xtool.core.implementation;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Set;
import java.util.function.Consumer;

import br.xtool.core.representation.EntityRepresentation;
import br.xtool.core.representation.JavaClassRepresentation;
import br.xtool.core.representation.JavaEnumRepresentation;
import br.xtool.core.representation.JavaTypeRepresentation;
import br.xtool.core.representation.JpaProjectionRepresentation;
import br.xtool.core.representation.SpringBootProjectRepresentation;
import br.xtool.core.visitor.Visitor;

/**
 * Serviços para projetos Spring Boot.
 * 
 * @author jcruz
 *
 */
@Deprecated
public interface SpringBootProject {

	/**
	 * Cria um diretório no projeto.
	 * 
	 * @param bootProject
	 * @param path
	 */
	void createDirectory(SpringBootProjectRepresentation bootProject, Path path);

	/**
	 * Adicionar um suporte ao projeto.
	 * 
	 * @param bootProject
	 * @param supportClass
	 */
//	@Deprecated
//	<T extends BootProjectSupport> void addSupport(SpringBootProjectRepresentation bootProject, Class<T> supportClass);

	/**
	 * Verifica se o projeto possui suporte.
	 * 
	 * @param bootProject
	 * @param supportClass
	 * @return
	 */
//	@Deprecated
//	<T extends BootProjectSupport> boolean hasSupport(SpringBootProjectRepresentation bootProject, Class<T> supportClass);

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
	@Deprecated
	void save(JavaTypeRepresentation<?> javaType);

	/**
	 * 
	 * @param javaTypes
	 */
	@Deprecated
	void save(JavaTypeRepresentation<?>... javaTypes);

	/**
	 * Converte as classs UML para as classes Java correspondentes.
	 * 
	 * @param bootProject
	 * @param umlClass
	 * @return
	 */
	Collection<JavaClassRepresentation> umlClassesToJavaClasses(SpringBootProjectRepresentation bootProject, Set<Visitor> vistors);

	/**
	 * Converte os enums UML para as classes Java correspondentes.
	 * 
	 * @param bootProject
	 * @param vistors
	 * @return
	 */
	Collection<JavaEnumRepresentation> umlEnumsToJavaEnums(SpringBootProjectRepresentation bootProject);

	/**
	 * Cria a interface de repositório no projeto.
	 * 
	 * @param entity
	 * @return
	 */
//	RepositoryRepresentation createRepository(SpringBootProjectRepresentation bootProject, EntityRepresentation entity);

	/**
	 * 
	 * @param bootProject
	 * @param entity
	 * @param consumer
	 */
//	void createRepository(SpringBootProjectRepresentation bootProject, EntityRepresentation entity, Consumer<RepositoryRepresentation> consumer);

	/**
	 * Cria a interface de repositório no projeto.
	 * 
	 * @param bootProject
	 * @param entity
	 * @return
	 */
	JpaProjectionRepresentation createProjection(SpringBootProjectRepresentation bootProject, EntityRepresentation entity);

	/**
	 * 
	 * @param bootProject
	 * @param entity
	 * @param consumer
	 */
	void createProjection(SpringBootProjectRepresentation bootProject, EntityRepresentation entity, Consumer<JpaProjectionRepresentation> consumer);

	/**
	 * 
	 * @param bootProject
	 * @param entity
	 * @return
	 */
//	SpecificationRepresentation createSpecification(SpringBootProjectRepresentation bootProject, EntityRepresentation entity);

	/**
	 * 
	 * @param bootProject
	 * @param entity
	 * @param consumer
	 */
//	void createSpecification(SpringBootProjectRepresentation bootProject, EntityRepresentation entity, Consumer<SpecificationRepresentation> consumer);

	/**
	 * Cria uma classe de serviço no projeto.
	 * 
	 * @param bootProject
	 * @param jpaRepository
	 * @return
	 */
//	ServiceClassRepresentation createService(SpringBootProjectRepresentation bootProject, RepositoryRepresentation jpaRepository);

	/**
	 * 
	 * @param bootProject
	 * @param jpaRepository
	 * @param consumer
	 */
//	void createService(SpringBootProjectRepresentation bootProject, RepositoryRepresentation jpaRepository, Consumer<ServiceClassRepresentation> consumer);

	/**
	 * Cria classe rest no projeto.
	 * 
	 * @param bootProject
	 * @param jpaRepository
	 * @return
	 */
//	RestClassRepresentation createRest(SpringBootProjectRepresentation bootProject, RepositoryRepresentation jpaRepository);

	/**
	 * 
	 * @param bootProject
	 * @param jpaRepository
	 * @param consumer
	 */
//	void createRest(SpringBootProjectRepresentation bootProject, RepositoryRepresentation jpaRepository, Consumer<RestClassRepresentation> consumer);

}
