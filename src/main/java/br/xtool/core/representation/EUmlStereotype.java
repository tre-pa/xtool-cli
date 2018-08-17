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
		INDEXED("Indexed"),
		VIEW("View"),
		READ_ONLY("ReadOnly");
		// @formatter:on
		String type;
	}

	StereotypeType getStereotypeType();
}
