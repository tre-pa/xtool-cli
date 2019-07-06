package br.xtool.core.implementation.representation;

import java.nio.file.Files;
import java.nio.file.Path;

import br.xtool.core.representation.angular.NgBaseTypeRepresentation;
import lombok.SneakyThrows;

public class NgTypeRepresentationImpl implements NgBaseTypeRepresentation {

	private Path path;

	public NgTypeRepresentationImpl(Path path) {
		super();
		this.path = path;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.angular.NgTypeRepresentation#getPath()
	 */
	@Override
	public Path getPath() {
		return this.path;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.angular.NgTypeRepresentation#getTsFileName()
	 */
	@Override
	public String getTsFileName() {
		return this.path.getFileName().toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.angular.NgTypeRepresentation#getTsFileContent()
	 */
	@Override
	@SneakyThrows
	public String getTsFileContent() {
		return new String(Files.readAllBytes(this.path));
	}

}
