package br.xtool.core.representation.plantuml;

import lombok.AllArgsConstructor;
import lombok.Getter;

public interface PlantStereotypeRepresentation {

	@AllArgsConstructor
	@Getter
	enum StereotypeType {
		AUDITABLE("Auditable"), CACHEABLE("Cacheable"), INDEXED("Indexed"), VIEW("View"), READ_ONLY("ReadOnly"), VERSIONABLE("Versionable");
		String type;
	}

	StereotypeType getStereotypeType();
}
