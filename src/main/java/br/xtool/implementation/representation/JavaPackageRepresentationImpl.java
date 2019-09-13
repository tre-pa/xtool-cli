package br.xtool.implementation.representation;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.lang3.StringUtils;

import br.xtool.representation.springboot.JavaPackageRepresentation;

/**
 * Representa um pacote Java.
 * 
 * @author jcruz
 *
 */
public class JavaPackageRepresentationImpl implements JavaPackageRepresentation {

	private String name;

	private String dir;

	private JavaPackageRepresentationImpl() {
		super();
	}

	public static JavaPackageRepresentation of(String packageName) {
		JavaPackageRepresentationImpl package1 = new JavaPackageRepresentationImpl();
		package1.name = packageName;
		package1.dir = packageName.replaceAll("\\.", "/");
		return package1;
	}

	public static JavaPackageRepresentation of(String... packageElements) {
		JavaPackageRepresentationImpl package1 = new JavaPackageRepresentationImpl();
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
