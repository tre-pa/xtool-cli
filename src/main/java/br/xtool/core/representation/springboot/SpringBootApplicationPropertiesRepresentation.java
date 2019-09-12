package br.xtool.core.representation.springboot;

import java.util.Optional;

/**
 * Representação de do arquivo application.properties do projeto Spring Boot.
 * 
 * @author jcruz
 *
 */
public interface SpringBootApplicationPropertiesRepresentation {

	SpringBootProjectRepresentation getProject();

	Optional<String> get(String key);

	@Deprecated
	SpringBootApplicationPropertiesRepresentation set(String key, String value);

	@Deprecated
	SpringBootApplicationPropertiesRepresentation set(String key, String value, Object... params);

	@Deprecated
	SpringBootApplicationPropertiesRepresentation comment(String key, String value);

	boolean hasProperty(String key);

	void save();
}
