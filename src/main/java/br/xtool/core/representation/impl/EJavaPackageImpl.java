package br.xtool.core.representation.impl;

import org.apache.commons.lang3.StringUtils;

import br.xtool.core.representation.EJavaPackage;

/**
 * Representa um pacote Java.
 * 
 * @author jcruz
 *
 */

public class EJavaPackageImpl implements EJavaPackage {

	private String name;

	private String dir;

	private EJavaPackageImpl() {
		super();
	}

	public static EJavaPackage of(String packageName) {
		EJavaPackageImpl package1 = new EJavaPackageImpl();
		package1.name = packageName;
		package1.dir = packageName.replaceAll("\\.", "/");
		return package1;
	}

	public static EJavaPackage of(String... packageElements) {
		EJavaPackageImpl package1 = new EJavaPackageImpl();
		package1.name = StringUtils.join(packageElements, ".");
		package1.dir = package1.name.replaceAll("\\.", "/");
		return package1;
	}

	@Override
	public int compareTo(EJavaPackage o) {
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