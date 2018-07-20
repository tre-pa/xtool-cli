package br.xtool.core.representation;

import lombok.AllArgsConstructor;
import lombok.Getter;

public interface EUmlStereotype {

	@AllArgsConstructor
	@Getter
	enum StereotypeType {
		// @formatter:off
		AUDITABLE("Auditable"),
		CACHEABLE("Cacheable"),
		INDEXED("Indexable");
		// @formatter:on
		String type;
	}

	StereotypeType getStereotypeType();
}
