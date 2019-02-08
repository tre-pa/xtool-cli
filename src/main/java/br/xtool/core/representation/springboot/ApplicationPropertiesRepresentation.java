package br.xtool.core.representation.springboot;

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

	@Deprecated
	ApplicationPropertiesRepresentation set(String key, String value);

	@Deprecated
	ApplicationPropertiesRepresentation set(String key, String value, Object... params);

	@Deprecated
	ApplicationPropertiesRepresentation comment(String key, String value);

	boolean hasProperty(String key);

	void save();
}
