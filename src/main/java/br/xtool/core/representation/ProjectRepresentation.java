package br.xtool.core.representation;

import java.nio.file.Path;
import java.util.Collection;

import br.xtool.core.implementation.representation.NgProjectRepresentationImpl;
import br.xtool.core.implementation.representation.SpringBootNgProjectRepresentationImpl;
import br.xtool.core.implementation.representation.SpringBootProjectRepresentationImpl;
import br.xtool.core.representation.angular.NgProjectRepresentation;
import br.xtool.core.representation.springboot.SpringBootNgProjectRepresentation;
import br.xtool.core.representation.springboot.SpringBootProjectRepresentation;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Interface genérica para projetos.
 * 
 * @author jcruz
 *
 */
public interface ProjectRepresentation extends Comparable<ProjectRepresentation> {

	/**
	 * Enum com os tipos de projeto
	 * 
	 * @author jcruz
	 *
	 */
	@AllArgsConstructor
	@Getter
	// @formatter:off
	@Deprecated
	enum Type {
		SPRINGBOOT("springboot", SpringBootProjectRepresentation.class), 
		ANGULAR("angular", NgProjectRepresentation.class), 
		SPRINGBOOTNG("springbootng", SpringBootNgProjectRepresentation.class),
		NONE("none", NoneProjectRepresentation.class);
		private String name;
		private Class<?> projectClass;
	}
	// @formatter:on

	/**
	 * Enum com as versões do projeto.
	 * 
	 * @author jcruz
	 *
	 */
	@AllArgsConstructor
	@Getter
	// @formatter:off
	@Deprecated
	enum Version {
		NONE(""), V1("v1"), V2("v2"), V3("v3"), V4("v4"), V5("v5"), V6("v6"), V7("v7"), V8("v8"), V9("v9"), V10("v10"),
		V2_V7("v2_v7");
		private String name;
	}
	// @formatter:on

	Path getPath();

	/**
	 * Retorna o nome do projeto.
	 * 
	 * @return
	 */
	String getName();

	/**
	 * Retorna a versão do Framework
	 * 
	 * @return
	 */
	@Deprecated
	String getFrameworkVersion();

	/**
	 * Retorna a versão do projeto.
	 * 
	 * @return
	 */
	Version getProjectVersion();

	/**
	 * Efetua uma limpeza no cache do projeto.
	 */
	void refresh();

	/**
	 * Retorna o tipo de projeto atual.
	 * 
	 * @return
	 */
	Type getProjectType();

	/**
	 * Lista todos os arquivo do projeto recursivamente.
	 * 
	 * @return
	 */
	Collection<Path> listAllFiles();

	/**
	 * Lista todos os diretórios recursivamente.
	 * 
	 * @return
	 */
	Collection<Path> listAllDirectories();

	/**
	 * Retorna se o projeto é multi-módulo.
	 * 
	 * @return
	 */
	@Deprecated
	boolean isMultiModule();

	/**
	 * 
	 * @param projectClass
	 * @param path
	 * @return
	 */
	@Deprecated
	static <T extends ProjectRepresentation> ProjectRepresentation factory(Class<T> projectClass, Path path) {
		if (SpringBootProjectRepresentation.class.isAssignableFrom(projectClass)) {
			return new SpringBootProjectRepresentationImpl(path);
		} else if (NgProjectRepresentation.class.isAssignableFrom(projectClass)) {
			return new NgProjectRepresentationImpl(path);
		} else if (SpringBootNgProjectRepresentation.class.isAssignableFrom(projectClass)) {
			return new SpringBootNgProjectRepresentationImpl(path);
		}
		throw new IllegalArgumentException(String.format("Factory de projeto não encontrada para %s", projectClass.getName()));
	}

}
