package br.xtool.core.representation.impl;

import java.nio.file.Path;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import br.xtool.core.representation.EDirectory;
import br.xtool.core.representation.EJavaPackage;
import br.xtool.core.representation.EJavaSourceFolder;

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
	public SortedSet<EJavaPackage> getPackages() {
		// @formatter:off
		return this.directory.getAllDirectories().stream()
				.map(dir -> this.getPath().relativize(dir.getPath()))
				.map(Path::toString)
				.filter(StringUtils::isNotBlank)
				.map(p -> StringUtils.split(p.toString(), "/"))
				.map(EJavaPackageImpl::of)
				.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

}
