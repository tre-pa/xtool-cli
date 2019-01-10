package br.xtool.core.representation;

import java.nio.file.Path;
import java.util.Map;

/**
 * Representação do package.json de um projeto Angular.
 * 
 * @author jcruz
 *
 */
public interface NgPackageRepresentation {

	Path getPath();

	String getName();

	String getVersion();

	Map<String, String> getDependencies();

	Map<String, String> getDevDependencies();

}