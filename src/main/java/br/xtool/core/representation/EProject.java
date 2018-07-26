package br.xtool.core.representation;

import java.nio.file.Path;
import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Interface genérica para projetos.
 * 
 * @author jcruz
 *
 */
public interface EProject extends Comparable<EProject> {

	/**
	 * Enum com os tipos de projeto
	 * 
	 * @author jcruz
	 *
	 */
	enum Type {
		SPRINGBOOT_PROJECT, ANGULAR_PROJECT, NONE;
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
		V1("v1"), V2("v2"), V3("v3"), V4("v4"), V5("v5"), V6("v6"), V7("v7"), V8("v8"), V9("v9"), V10("v10");
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

}
