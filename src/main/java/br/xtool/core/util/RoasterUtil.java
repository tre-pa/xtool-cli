package br.xtool.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.annotation.Annotation;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.jboss.forge.roaster.ParserException;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.JavaUnit;
import org.jboss.forge.roaster.model.source.AnnotationSource;
import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;

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

	public static JavaClassSource createJavaClassSource(String name) {
		JavaClassSource javaClassSource = Roaster.create(JavaClassSource.class);
		javaClassSource.setName(name);
		return javaClassSource;
	}

	public static void addImport(JavaClassSource javaClassSource, String className) {
		if (StringUtils.isNotBlank(className)) {
			if (!javaClassSource.hasImport(className)) {
				javaClassSource.addImport(className);
			}
		}
	}

	/**
	 * 
	 * @param javaClassSource
	 * @param type
	 * @return
	 */
	public static AnnotationSource<JavaClassSource> findAnnotationOrCreate(JavaClassSource javaClassSource, Class<? extends Annotation> type) {
		return javaClassSource.hasAnnotation(type) ? javaClassSource.getAnnotation(type) : javaClassSource.addAnnotation(type);
	}

	/**
	 * 
	 * @param fieldSource
	 * @param type
	 * @return
	 */
	public static AnnotationSource<JavaClassSource> findAnnotationOrCreate(FieldSource<JavaClassSource> fieldSource, Class<? extends Annotation> type) {
		return fieldSource.hasAnnotation(type) ? fieldSource.getAnnotation(type) : fieldSource.addAnnotation(type);
	}

	/**
	 * Busca pelo FieldSource no objeto JavaClassSource correpondente. Caso nao
	 * encontre retorna uma instância de FieldSource.
	 * 
	 * @param javaClassSource
	 * @param name
	 * @return
	 */
	public static FieldSource<JavaClassSource> findFieldSourceOrCreate(JavaClassSource javaClassSource, String name) {
		return javaClassSource.hasField(name) ? javaClassSource.getField(name) : javaClassSource.addField();
	}
}
