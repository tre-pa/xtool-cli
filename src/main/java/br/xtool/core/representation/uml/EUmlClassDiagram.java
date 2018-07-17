package br.xtool.core.representation.uml;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

public class EUmlClassDiagram {

	public static Optional<EUmlClassDiagram> of(String path) {
		if (Files.exists(Paths.get(path))) {

		}
		return Optional.empty();
	}

}
