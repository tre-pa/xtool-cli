package br.xtool.core.representation.impl;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import br.xtool.core.representation.EPlantClass;
import br.xtool.core.representation.EPlantStereotype;

public class EUmlStereotypeImpl implements EPlantStereotype {

	private EPlantClass umlClass;

	private String stereotype;

	public EUmlStereotypeImpl(EPlantClass umlClass, String stereotype) {
		super();
		this.umlClass = umlClass;
		this.stereotype = stereotype;
	}

	@Override
	public StereotypeType getStereotypeType() {
		String invalidStereotype = "Stereotype '%s' inválido para a classe '%s'. Os stereotypes válidos são: %s";
		// @formatter:off
		String stereotypes = Stream.of(StereotypeType.values())
				.map(StereotypeType::getType)
				.collect(Collectors.joining(","));
		return Stream.of(StereotypeType.values())
					.filter(s -> s.getType().equals(this.stereotype))
					.findAny()
					.orElseThrow(()-> new IllegalArgumentException(String.format(invalidStereotype, this.stereotype, this.umlClass.getName(), stereotypes)));
		// @formatter:on
	}

}
