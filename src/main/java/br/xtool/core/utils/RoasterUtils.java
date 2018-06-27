package br.xtool.core.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Optional;

import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.JavaUnit;

public class RoasterUtils {

	public static Optional<JavaUnit> getJavaUnit(File javaFile) {
		try {
			JavaUnit javaUnit = Roaster.parseUnit(new FileInputStream(javaFile));
			return Optional.of(javaUnit);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}

}
