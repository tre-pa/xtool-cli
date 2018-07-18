package br.xtool.core.representation.impl;

import org.apache.commons.lang3.StringUtils;

import br.xtool.core.representation.EPackage;

/**
 * Representa um pacote Java.
 * 
 * @author jcruz
 *
 */

public class EPackageImpl implements EPackage {

	private String name;

	private String dir;

	private EPackageImpl() {
		super();
	}

	public static EPackage of(String packageName) {
		EPackageImpl package1 = new EPackageImpl();
		package1.name = packageName;
		package1.dir = packageName.replaceAll("\\.", "/");
		return package1;
	}

	public static EPackage of(String... packageElements) {
		EPackageImpl package1 = new EPackageImpl();
		package1.name = StringUtils.join(packageElements, ".");
		package1.dir = package1.name.replaceAll("\\.", "/");
		return package1;
	}

	@Override
	public int compareTo(EPackage o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getDir() {
		return this.dir;
	}
}
