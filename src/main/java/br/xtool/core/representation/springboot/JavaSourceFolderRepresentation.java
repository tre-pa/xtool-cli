package br.xtool.core.representation.springboot;

import java.util.SortedSet;

import br.xtool.core.representation.SourceFolderRepresentation;

/**
 * Representação de um source folder Java.
 * 
 * @author jcruz
 *
 */
public interface JavaSourceFolderRepresentation extends SourceFolderRepresentation {

	/**
	 * Retorna a lista de pacotes do source folder java.
	 * 
	 * @return
	 */
	SortedSet<JavaPackageRepresentation> getPackages();

	/**
	 * Retorna o projeto Spring Boot associado.
	 * 
	 * @return
	 */
	SpringBootProjectRepresentation getBootProject();

}
