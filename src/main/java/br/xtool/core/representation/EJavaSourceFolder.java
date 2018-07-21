package br.xtool.core.representation;

import java.util.SortedSet;

public interface EJavaSourceFolder {

	String getPath();

	SortedSet<EJavaPackage> getPackages();

}
