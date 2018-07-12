package br.xtool.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Optional;

import org.jboss.forge.roaster.ParserException;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.JavaUnit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RoasterUtil {

	public static Optional<JavaUnit> createJavaUnit(File javaFile) {
		try {
			log.info("Realizando parse do arquivo {}", javaFile.getAbsolutePath());
			JavaUnit javaUnit = Roaster.parseUnit(new FileInputStream(javaFile));
			return Optional.of(javaUnit);
		} catch (FileNotFoundException | ParserException e) {
			log.error(e.getMessage());
		}
		return Optional.empty();
	}
}
