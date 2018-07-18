package br.xtool.core.representation.angular;

import java.util.Map;

/**
 * Representação do package.json de um projeto Angular.
 * 
 * @author jcruz
 *
 */
public interface ENgPackage {

	String getPath();

	String getName();

	String getVersion();

	Map<String, String> getDependencies();

	Map<String, String> getDevDependencies();

}
