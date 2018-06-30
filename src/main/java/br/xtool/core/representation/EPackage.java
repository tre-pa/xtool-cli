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
public class EPackage {

	private String name;

	private String dir;

	private EPackage() {
		super();
	}

	public static EPackage of(String packageName) {
		EPackage package1 = new EPackage();
		package1.name = packageName;
		package1.dir = packageName.replaceAll("\\.", "/");
		return package1;
	}

	public static EPackage of(String... packageElements) {
		EPackage package1 = new EPackage();
		package1.name = StringUtils.join(packageElements, ".");
		package1.dir = package1.name.replaceAll("\\.", "/");
		return package1;
	}
}
