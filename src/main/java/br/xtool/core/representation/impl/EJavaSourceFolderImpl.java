package br.xtool.core.representation.impl;

import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import br.xtool.core.representation.EDirectory;
import br.xtool.core.representation.EJavaPackage;
import br.xtool.core.representation.EJavaSourceFolder;

public class EJavaSourceFolderImpl implements EJavaSourceFolder {

	private EDirectory directory;

	private EJavaPackage groupId;

	public EJavaSourceFolderImpl(EJavaPackage groupId, String path) {
		super();
		this.directory = EDirectoryImpl.of(path);
		this.groupId = groupId;
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EJavaSourceFolder#getPath()
	 */
	@Override
	public String getPath() {
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
				.map(dir -> StringUtils.substring(dir.getPath() , StringUtils.indexOf(dir.getPath(), this.groupId.getDir())))
				.map(str -> StringUtils.split(str, "/"))
				.map(EJavaPackageImpl::of)
				.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

}
