package br.xtool.core.representation.converter;

import java.util.function.BiFunction;

import org.jboss.forge.roaster.model.source.JavaClassSource;

import br.xtool.core.representation.EBootProject;
import br.xtool.core.representation.EJavaClass;
import br.xtool.core.representation.EUmlClass;
import br.xtool.core.representation.impl.EJavaClassImpl;
import br.xtool.core.util.RoasterUtil;

/**
 * Converte uma classe UML em um EJavaClass.
 * 
 * @author jcruz
 *
 */
public class EUmlClassConverter implements BiFunction<EBootProject, EUmlClass, EJavaClass> {

	@Override
	public EJavaClass apply(EBootProject bootProject, EUmlClass umlClass) {
		// @formatter:off
		return bootProject.getRoasterJavaUnits().stream()
				.filter(javaUnit -> javaUnit.getGoverningType().isClass())
				.filter(javaUnit -> javaUnit.getGoverningType().getName().equals(umlClass.getName()))
				.map(javaUnit -> javaUnit.<JavaClassSource>getGoverningType())
				.map(javaClassSource -> new EJavaClassImpl(bootProject, javaClassSource))
				.findFirst()
				.orElseGet(() -> new EJavaClassImpl(bootProject,RoasterUtil.createJavaClassSource(umlClass.getPackage().getName(),umlClass.getName())));
		// @formatter:on
	}

}
