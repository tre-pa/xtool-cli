package br.xtool.core.representation;

import java.util.Optional;

public interface EApplicationProperties {

	Optional<String> get(String key);

	void set(String key, String value);

	boolean hasProperty(String key);

	void save();
}
