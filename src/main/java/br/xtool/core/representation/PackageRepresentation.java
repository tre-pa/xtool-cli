package br.xtool.core.representation;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;

/**
 * Representa um pacote Java.
 * 
 * @author jcruz
 *
 */

@Getter
public class PackageRepresentation {

	private String name;

	private String dir;

	private PackageRepresentation() {
		super();
	}

	public static PackageRepresentation of(String packageName) {
		PackageRepresentation package1 = new PackageRepresentation();
		package1.name = packageName;
		package1.dir = packageName.replaceAll("\\.", "/");
		return package1;
	}

	public static PackageRepresentation of(String... packageElements) {
		PackageRepresentation package1 = new PackageRepresentation();
		package1.name = StringUtils.join(packageElements, ".");
		package1.dir = package1.name.replaceAll("\\.", "/");
		return package1;
	}
}
