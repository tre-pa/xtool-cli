package br.xtool.core.representation;

import java.util.Optional;

/**
 * Representação de do arquivo application.properties do projeto Spring Boot.
 * 
 * @author jcruz
 *
 */
public interface ESBootAppProperties {

	Optional<String> get(String key);

	void set(String key, String value);

	boolean hasProperty(String key);

	void save();
}
