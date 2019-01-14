package br.xtool.core.representation.angular;

import java.nio.file.Path;

public interface NgEnumRepresentation extends Comparable<NgEnumRepresentation>{

	Path getPath();

	String getName();

	String getFileName();
}
