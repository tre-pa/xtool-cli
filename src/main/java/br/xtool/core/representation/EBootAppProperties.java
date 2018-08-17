package br.xtool.core.representation;

import java.util.Optional;

/**
 * Representação de do arquivo application.properties do projeto Spring Boot.
 * 
 * @author jcruz
 *
 */
public interface EBootAppProperties {

	EBootProject getProject();

	Optional<String> get(String key);

	EBootAppProperties set(String key, String value);

	EBootAppProperties set(String key, String value, Object... params);

	EBootAppProperties comment(String key, String value);

	boolean hasProperty(String key);

	void save();
}
