package br.xtool.core.representation.converter;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;

import org.jboss.forge.roaster.model.source.JavaClassSource;

import br.xtool.core.representation.EBootProject;
import br.xtool.core.representation.EJavaClass;
import br.xtool.core.representation.EUmlClass;
import br.xtool.core.representation.impl.EJavaClassImpl;
import br.xtool.core.util.RoasterUtil;
import br.xtool.core.visitor.Visitor;
import lombok.AllArgsConstructor;

/**
 * Converte uma classe UML em um EJavaClass.
 * 
 * @author jcruz
 *
 */
@AllArgsConstructor
public class EUmlClassConverter implements BiFunction<EBootProject, EUmlClass, EJavaClass> {

	private Set<? extends Visitor> visitors = new HashSet<>();

	@Override
	public EJavaClass apply(EBootProject bootProject, EUmlClass umlClass) {
		// @formatter:off
		EJavaClass javaClass = bootProject.getRoasterJavaUnits().stream()
				.filter(javaUnit -> javaUnit.getGoverningType().isClass())
				.filter(javaUnit -> javaUnit.getGoverningType().getName().equals(umlClass.getName()))
				.map(javaUnit -> javaUnit.<JavaClassSource>getGoverningType())
				.map(javaClassSource -> new EJavaClassImpl(bootProject, javaClassSource))
				.findFirst()
				.orElseGet(() -> new EJavaClassImpl(bootProject,RoasterUtil.createJavaClassSource(umlClass.getUmlPackage().getName(),umlClass.getName())));
		// @formatter:on
		this.visitors.forEach(visitor -> visitor.visit(javaClass));
		return javaClass;
	}

}
