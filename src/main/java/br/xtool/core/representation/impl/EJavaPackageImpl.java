package br.xtool.core.representation.impl;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.lang3.StringUtils;

import br.xtool.core.representation.springboot.JavaPackageRepresentation;

/**
 * Representa um pacote Java.
 * 
 * @author jcruz
 *
 */
public class EJavaPackageImpl implements JavaPackageRepresentation {

	private String name;

	private String dir;

	private EJavaPackageImpl() {
		super();
	}

	public static JavaPackageRepresentation of(String packageName) {
		EJavaPackageImpl package1 = new EJavaPackageImpl();
		package1.name = packageName;
		package1.dir = packageName.replaceAll("\\.", "/");
		return package1;
	}

	public static JavaPackageRepresentation of(String... packageElements) {
		EJavaPackageImpl package1 = new EJavaPackageImpl();
		package1.name = StringUtils.join(packageElements, ".");
		package1.dir = package1.name.replaceAll("\\.", "/");
		return package1;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getDir() {
		return this.dir;
	}

	@Override
	public Path getPath() {
		return Paths.get(this.dir);
	}

	@Override
	public int compareTo(JavaPackageRepresentation o) {
		return this.getName().compareTo(o.getName());
	}

}
