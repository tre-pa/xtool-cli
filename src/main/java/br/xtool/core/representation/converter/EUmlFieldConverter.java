package br.xtool.core.representation.converter;

import java.util.function.BiFunction;

import br.xtool.core.representation.EJavaClass;
import br.xtool.core.representation.EJavaField;
import br.xtool.core.representation.EUmlField;
import br.xtool.core.util.RoasterUtil;

public class EUmlFieldConverter implements BiFunction<EJavaClass, EUmlField, EJavaField> {

	@Override
	public EJavaField apply(EJavaClass javaClass, EUmlField umlField) {
		EJavaField javaField = javaClass.addField(umlField.getName());
		RoasterUtil.addImport(javaField.getRoasterField().getOrigin(), umlField.getType().getClassName());
		// @formatter:off
		javaField.getRoasterField()
			.setName(umlField.getName())
			.setPrivate()
			.setType(umlField.getType().getJavaName());
		// @formatter:on
		return javaField;
	}

}
