package br.xtool.core.representation.impl;

import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

import br.xtool.core.representation.EUmlClass;
import br.xtool.core.representation.EUmlMultiplicity;

public class EUmlMultiplicityImpl implements EUmlMultiplicity {

	private String sourceQualifier;

	private EUmlClass sourceClass;

	private EUmlClass targetClass;

	public EUmlMultiplicityImpl(EUmlClass sourceClass, EUmlClass targetClass, String sourceQualifier) {
		super();
		this.sourceQualifier = sourceQualifier;
		this.sourceClass = sourceClass;
		this.targetClass = targetClass;
	}

	@Override
	public MultiplicityType getMutiplicityType() {
		String invalidMultiplicity = "Multiplicidade '%s' inválida entre %s e %s. Os tipos válidos são: %s";
		String blankMultiplicity = "É necessário definir a multiplicidade entre %s e %s";
		String multiplicities = StringUtils.join(MultiplicityType.values(), ", ");
		if (StringUtils.isBlank(this.sourceQualifier)) throw new IllegalArgumentException(String.format(blankMultiplicity, this.sourceClass.getName(), this.targetClass.getName()));
		// @formatter:off
		return Stream.of(MultiplicityType.values())
				.filter(multiplicityType -> multiplicityType.getPattern().equals(this.sourceQualifier))
				.findAny()
				.orElseThrow(() -> new IllegalArgumentException(String.format(invalidMultiplicity, this.sourceQualifier, this.sourceClass.getName(), this.targetClass.getName(), multiplicities)));
		// @formatter:on
	}

}
