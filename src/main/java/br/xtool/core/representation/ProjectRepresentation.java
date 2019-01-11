package br.xtool.core.representation;

import java.nio.file.Path;
import java.util.Collection;

import br.xtool.core.representation.angular.NgProjectRepresentation;
import br.xtool.core.representation.impl.EBootProjectImpl;
import br.xtool.core.representation.impl.ENgProjectImpl;
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
	enum Type {
		SPRINGBOOT("springboot", SpringBootProjectRepresentation.class), ANGULAR("angular", NgProjectRepresentation.class), NONE("none", NoneProjectRepresentation.class);
		private String name;
		private Class<?> projectClass;
	}

	/**
	 * Enum com as versões do projeto.
	 * 
	 * @author jcruz
	 *
	 */
	@AllArgsConstructor
	@Getter
	enum Version {
		NONE(""), V1("v1"), V2("v2"), V3("v3"), V4("v4"), V5("v5"), V6("v6"), V7("v7"), V8("v8"), V9("v9"), V10("v10");
		private String name;
	}

	Path getPath();

	/**
	 * Retorna o nome do projeto.
	 * 
	 * @return
	 */
	String getName();

	//	String getMainDir();

	/**
	 * 
	 * @return
	 */
	String getFrameworkVersion();

	Version getProjectVersion();

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
	 * 
	 * @param projectClass
	 * @param path
	 * @return
	 */
	static <T extends ProjectRepresentation> ProjectRepresentation factory(Class<T> projectClass, Path path) {
		if (SpringBootProjectRepresentation.class.isAssignableFrom(projectClass)) {
			return new EBootProjectImpl(path);
		} else if (NgProjectRepresentation.class.isAssignableFrom(projectClass)) {
			return new ENgProjectImpl(path);
		}
		throw new IllegalArgumentException(String.format("Factory de projeto não encontrada para %s", projectClass.getName()));
	}

	/**
	 * 
	 * @author jcruz
	 *
	 * @param <T>
	 */
	interface Support<T extends ProjectRepresentation> {
		/**
		 * 
		 * @param project
		 * @param version
		 */
		void apply(T project);

		boolean has(T project);
	}

}
