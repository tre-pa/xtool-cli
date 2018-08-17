package br.xtool.core.representation.converter;

import java.util.function.BiFunction;

import org.jboss.forge.roaster.model.source.JavaEnumSource;

import br.xtool.core.representation.EBootProject;
import br.xtool.core.representation.EJavaEnum;
import br.xtool.core.representation.EUmlEnum;
import br.xtool.core.representation.impl.EJavaEnumImpl;
import br.xtool.core.util.RoasterUtil;
import lombok.AllArgsConstructor;

/**
 * Converter um enum UML do diagrama de classe em um EJavaEnum.
 * 
 * @author jcruz
 *
 */
@AllArgsConstructor
public class EUmlEnumConverter implements BiFunction<EBootProject, EUmlEnum, EJavaEnum> {

	@Override
	public EJavaEnum apply(EBootProject bootProject, EUmlEnum umlEnum) {
		// @formatter:off
		EJavaEnum javaEnum = bootProject.getRoasterJavaUnits().stream()
				.filter(javaUnit -> javaUnit.getGoverningType().isEnum())
				.filter(javaUnit -> javaUnit.getGoverningType().getName().equals(umlEnum.getName()))
				.map(javaUnit -> javaUnit.<JavaEnumSource>getGoverningType())
				.map(javaEnumSource -> new EJavaEnumImpl(bootProject, javaEnumSource))
				.findFirst()
				.orElseGet(() -> new EJavaEnumImpl(bootProject,RoasterUtil.createJavaEnumSource(umlEnum.getUmlPackage().getName(),umlEnum.getName())));
		umlEnum.getValues().stream()
			.filter(value -> !javaEnum.getConstants().contains(value))
			.forEach(value -> javaEnum.getRoasterEnum().addEnumConstant(value));
		// @formatter:on
		return javaEnum;
	}

}
