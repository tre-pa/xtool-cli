package br.xtool.core.representation;

import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.io.FilenameUtils;

import lombok.Getter;

@Getter
public class EAngularProject extends EProject {

	private SortedSet<ENgClass> ngClasses = new TreeSet<>();

	@Getter(lazy = true)
	private final ENgPackage ngPackage = buildNgPackage();

	public EAngularProject(String path, SortedSet<ENgClass> ngClasses) {
		super(path);
		this.ngClasses = ngClasses;
	}

	private ENgPackage buildNgPackage() {
		return ENgPackage.of(FilenameUtils.concat(this.getPath(), "package.json")).orElse(null);
	}

}
