package br.xtool.core.representation;

import java.nio.file.Path;

public interface ENgEnum extends Comparable<ENgEnum>{

	Path getPath();

	String getName();

	String getFileName();
}
