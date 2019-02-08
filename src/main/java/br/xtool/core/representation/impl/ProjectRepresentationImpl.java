package br.xtool.core.representation.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import br.xtool.core.representation.ProjectRepresentation;

public abstract class ProjectRepresentationImpl implements ProjectRepresentation {

	private Path path;

	public ProjectRepresentationImpl(Path path) {
		super();
		this.path = path;
	}

	/**
	 * Retorna o nome do projeto.
	 * 
	 * @return
	 */
	@Override
	public String getName() {
		return this.getPath().getFileName().toString();
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EProject#listAllFiles()
	 */
	@Override
	public Collection<Path> listAllFiles() {
		try (Stream<Path> pathStrem = Files.walk(this.path)) {
			// @formatter:off
			return pathStrem
				.filter(Files::isRegularFile)
				.filter(p -> !this.path.relativize(p).startsWith("target"))
				.filter(p -> !this.path.relativize(p).startsWith(".git"))
				.filter(p -> !this.path.relativize(p).startsWith(".settings"))
				.filter(p -> !this.path.relativize(p).startsWith("dist"))
				.filter(p -> !this.path.relativize(p).startsWith("node_modules"))
				.filter(p -> !this.path.relativize(p).startsWith(".project"))
				.filter(p -> !this.path.relativize(p).startsWith(".classpath"))
				.filter(p -> !this.path.relativize(p).startsWith(".gitignore"))
				.filter(p -> !this.path.relativize(p).startsWith(".factorypath"))
				.filter(p -> !this.path.relativize(p).startsWith(".springBeans"))
				.filter(p -> !this.path.relativize(p).endsWith(".gitkeep"))
				.collect(Collectors.toList());
			// @formatter:on
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EProject#listAllDirectories()
	 */
	@Override
	public Collection<Path> listAllDirectories() {
		try (Stream<Path> pathStrem = Files.walk(this.path)) {
			// @formatter:off
			return pathStrem
				.filter(Files::isDirectory)
				.filter(p -> !this.path.relativize(p).startsWith("target"))
				.filter(p -> !this.path.relativize(p).startsWith(".git"))
				.filter(p -> !this.path.relativize(p).startsWith(".settings"))
				.filter(p -> !this.path.relativize(p).startsWith(".apt_generated"))
				.filter(p -> !this.path.relativize(p).startsWith("dist"))
				.filter(p -> !this.path.relativize(p).startsWith("node_modules"))
				.collect(Collectors.toList());
			// @formatter:on
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EProject#getPath()
	 */
	@Override
	public Path getPath() {
		return this.path;
	}

	/**
	 * 
	 */
	@Override
	public int compareTo(ProjectRepresentation o) {
		return this.getName().compareTo(o.getName());
	}
}
