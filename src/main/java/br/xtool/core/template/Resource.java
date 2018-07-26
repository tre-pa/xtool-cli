package br.xtool.core.template;

import java.nio.file.Path;

public interface Resource {

	Path getPath();

	byte[] read();

}
