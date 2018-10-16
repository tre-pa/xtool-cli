package br.xtool.core.representation;

import lombok.AllArgsConstructor;
import lombok.Getter;

public interface EPlantStereotype {

	@AllArgsConstructor
	@Getter
	enum StereotypeType {
		// @formatter:off
		AUDITABLE("Auditable"),
		CACHEABLE("Cacheable"),
		INDEXED("Indexed"),
		VIEW("View"),
		READ_ONLY("ReadOnly"),
		VERSIONABLE("Versionable");
		// @formatter:on
		String type;
	}

	StereotypeType getStereotypeType();
}
