package br.xtool.core.representation;

import java.io.File;
import java.util.List;

import br.xtool.core.representation.EProject.ProjectType;

/**
 * Representação de um diretório do sistema de arquivos.
 * 
 * @author jcruz
 *
 */
public interface EDirectory {

	String getPath();

	ProjectType getProjectType();

	String getBaseName();

	List<File> getAllFiles();

}
