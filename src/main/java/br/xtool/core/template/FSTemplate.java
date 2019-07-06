package br.xtool.core.template;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import br.xtool.core.FS;

public abstract class FSTemplate {

	private Map<String, Object> vars = new HashMap<>();

	private Path source;

	private Path destination;

	protected abstract void configure();

	protected final void put(String key, Object value) {
		Assert.hasText(key, "A chave é obrigatória.");
		Assert.notNull(value, "O valor da chave não pode ser nulo.");
		vars.put(key, value);
	}

	protected final void source(String... paths) {
		Assert.notEmpty(paths, "O caminho do template não pode ser vazio.");
		source = Paths.get(StringUtils.join(paths, "/"));
	}

	protected final void destination(String... paths) {
		Assert.notEmpty(paths, "O caminho de destino não pode ser vazio.");
		destination = Paths.get(StringUtils.join(paths, "/"));
	}

	public void merge(FS fs) {
		configure();
		fs.copy(source, vars, destination);
	}

}
