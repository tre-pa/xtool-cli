package br.xtool.core.representation.impl;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

import br.xtool.core.representation.EUmlClass;
import br.xtool.core.representation.EUmlMultiplicity;

public class EUmlMultiplicityImpl implements EUmlMultiplicity {

	private String multiplicity;

	private EUmlClass sourceClass;

	private EUmlClass targetClass;

	public EUmlMultiplicityImpl(EUmlClass sourceClass, EUmlClass targetClass, String multiplicity) {
		super();
		this.multiplicity = multiplicity;
		this.sourceClass = sourceClass;
		this.targetClass = targetClass;
	}

	@Override
	public MultiplicityType getMutiplicityType() {
		String invalidMultiplicity = "Multiplicidade '%s' inválida entre %s e %s. Os tipos válidos são: %s";
		String blankMultiplicity = "É necessário definir a multiplicidade entre %s e %s";
		String multiplicities = StringUtils.join(MultiplicityType.values(), ", ");
		if (StringUtils.isBlank(this.multiplicity)) throw new IllegalArgumentException(String.format(blankMultiplicity, this.sourceClass.getName(), this.targetClass.getName()));
		// @formatter:off
		return Arrays.asList(MultiplicityType.values()).stream()
				.filter(multiplicityType -> multiplicityType.getPattern().equals(this.multiplicity))
				.findAny()
				.orElseThrow(() -> new IllegalArgumentException(String.format(invalidMultiplicity, this.multiplicity, this.sourceClass.getName(), this.targetClass.getName(), multiplicities)));
		// @formatter:on
	}

}
