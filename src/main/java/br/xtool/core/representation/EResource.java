package br.xtool.core.representation;

import java.nio.file.Path;

public interface EResource {

	Path getPath();

	byte[] read();

}
