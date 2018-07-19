package br.xtool.core.representation.impl;

import br.xtool.core.representation.EUmlMultiplicity;

public class EUmlMultiplicityImpl implements EUmlMultiplicity {

	private String multiplicity;

	public EUmlMultiplicityImpl(String multiplicity) {
		super();
		this.multiplicity = multiplicity;
	}

	@Override
	public String getMin() {
		return null;
	}

	@Override
	public String getMax() {
		return null;
	}

	@Override
	public boolean isMinEqualsMax() {
		return this.getMin().equals(this.getMax());
	}

}
