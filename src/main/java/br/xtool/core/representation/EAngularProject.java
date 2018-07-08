package br.xtool.core.representation;

import java.util.SortedSet;
import java.util.TreeSet;

import lombok.Getter;

@Getter
public class EAngularProject extends EProject {

	private SortedSet<ENgClass> ngClasses = new TreeSet<>();

	public EAngularProject(String path, SortedSet<ENgClass> ngClasses) {
		super(path);
		this.ngClasses = ngClasses;
	}

}
