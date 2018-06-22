package br.xtool.core.model;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;

/**
 * Representa um pacote Java.
 * 
 * @author jcruz
 *
 */

@Getter
public class Package {

	private String name;

	private String dir;

	private Package() {
		super();
	}

	public static Package of(String packageName) {
		Package package1 = new Package();
		package1.name = packageName;
		package1.dir = packageName.replaceAll("\\.", "/");
		return package1;
	}

	public static Package of(String... packageElements) {
		Package package1 = new Package();
		package1.name = StringUtils.join(packageElements, ".");
		package1.dir = package1.name.replaceAll("\\.", "/");
		return package1;
	}
}
