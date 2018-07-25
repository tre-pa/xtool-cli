package br.xtool.core.representation.impl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import br.xtool.core.representation.EDirectory;
import br.xtool.core.representation.EJavaPackage;
import br.xtool.core.representation.EJavaSourceFolder;
import lombok.SneakyThrows;

public class EJavaSourceFolderImpl implements EJavaSourceFolder {

	private EDirectory directory;

	public EJavaSourceFolderImpl(Path path) {
		super();
		this.directory = EDirectoryImpl.of(path);
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EJavaSourceFolder#getPath()
	 */
	@Override
	public Path getPath() {
		return this.directory.getPath();
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EJavaSourceFolder#getPackages()
	 */
	@Override
	@SneakyThrows
	public SortedSet<EJavaPackage> getPackages() {
		// @formatter:off
		return Files.walk(this.getPath())
				.filter(Files::isDirectory)
				.map(path -> this.getPath().relativize(path))
				.map(Path::toString)
				.filter(StringUtils::isNotBlank)
				.map(p -> StringUtils.split(p.toString(), "/"))
				.map(EJavaPackageImpl::of)
				.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

}
