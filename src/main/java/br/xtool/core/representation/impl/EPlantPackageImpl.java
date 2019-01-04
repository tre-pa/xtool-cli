package br.xtool.core.representation.impl;

import br.xtool.core.representation.PlantPackageRepresentation;
import net.sourceforge.plantuml.cucadiagram.IGroup;

public class EPlantPackageImpl implements PlantPackageRepresentation {

	private IGroup group;

	public EPlantPackageImpl(IGroup group) {
		super();
		this.group = group;
	}

	@Override
	public String getName() {
		return this.group.getCode().getFullName();
	}

}
