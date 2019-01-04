package br.xtool.core.representation;

import java.util.Optional;

/**
 * Representação de do arquivo application.properties do projeto Spring Boot.
 * 
 * @author jcruz
 *
 */
public interface ApplicationPropertiesRepresentation {

	SpringBootProjectRepresentation getProject();

	Optional<String> get(String key);

	ApplicationPropertiesRepresentation set(String key, String value);

	ApplicationPropertiesRepresentation set(String key, String value, Object... params);

	ApplicationPropertiesRepresentation comment(String key, String value);

	boolean hasProperty(String key);

	void save();
}
