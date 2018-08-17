package br.xtool.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.jboss.forge.roaster.ParserException;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.JavaUnit;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.JavaEnumSource;
import org.jboss.forge.roaster.model.source.JavaInterfaceSource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RoasterUtil {

	public static Optional<JavaUnit> loadJavaUnit(File javaFile) {
		try {
			log.info("Realizando parse do arquivo {}", javaFile.getAbsolutePath());
			JavaUnit javaUnit = Roaster.parseUnit(new FileInputStream(javaFile));
			return Optional.of(javaUnit);
		} catch (FileNotFoundException | ParserException e) {
			log.error(e.getMessage());
		}
		return Optional.empty();
	}

	public static JavaEnumSource createJavaEnumSource(String packageName, String name) {
		JavaEnumSource javaEnumSource = Roaster.create(JavaEnumSource.class);
		javaEnumSource.setPackage(packageName);
		javaEnumSource.setName(name);
		return javaEnumSource;
	}

	public static JavaEnumSource createJavaEnumSource(String name) {
		JavaEnumSource javaEnumSource = Roaster.create(JavaEnumSource.class);
		javaEnumSource.setName(name);
		return javaEnumSource;
	}

	public static JavaClassSource createJavaClassSource(String name) {
		JavaClassSource javaClassSource = Roaster.create(JavaClassSource.class);
		javaClassSource.setName(name);
		return javaClassSource;
	}

	public static JavaClassSource createJavaClassSource(String packageName, String name) {
		JavaClassSource javaClassSource = Roaster.create(JavaClassSource.class);
		javaClassSource.setPackage(packageName);
		javaClassSource.setName(name);
		return javaClassSource;
	}

	public static JavaInterfaceSource createJavaInterface(String name) {
		JavaInterfaceSource javaInterfaceSource = Roaster.create(JavaInterfaceSource.class);
		javaInterfaceSource.setName(name);
		return javaInterfaceSource;
	}

	public static JavaInterfaceSource createJavaInterface(String packageName, String name) {
		JavaInterfaceSource javaInterfaceSource = Roaster.create(JavaInterfaceSource.class);
		javaInterfaceSource.setPackage(packageName);
		javaInterfaceSource.setName(name);
		return javaInterfaceSource;
	}

	public static void addImport(JavaClassSource javaClassSource, String className) {
		if (StringUtils.isNotBlank(className)) {
			if (!javaClassSource.hasImport(className)) {
				javaClassSource.addImport(className);
			}
		}
	}

}
